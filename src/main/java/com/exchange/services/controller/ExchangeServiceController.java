package com.exchange.services.controller;

import static com.exchange.services.constants.ControllerConstants.EXCHANGE_PATH_FOR_MAPPING;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.exchange.services.exception.InvalidQueryException;
import com.exchange.services.model.Conversion;
import com.exchange.services.repository.ConversionRepository;
import com.exchange.services.restapi.client.controller.ClientController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(EXCHANGE_PATH_FOR_MAPPING)
@Api(value = "Exchange service controller")
public class ExchangeServiceController {

	private static Logger logger = LoggerFactory.getLogger(ExchangeServiceController.class);

	public final static String CONVERSION_PATH = "/convert";
	public final static String LIST_CONVERSION_PATH = "/listconversion";
	private final String DATE_PATTERN = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$";
	@Autowired
	ConversionRepository conversionRepository;

	@Autowired
	ClientController clientController;

	@GetMapping(path = LIST_CONVERSION_PATH)
	@ApiOperation(value = "Lists conversions depending on şnput parameters", response = List.class)
	public List<Conversion> listConversion(
			@ApiParam(value = "Filter by date.", format = "dd-MM-yyyy", required = false) @RequestParam(value = "date", required = false) String date,
			@ApiParam(value = "Filter by id.", required = false) @RequestParam(value = "id", required = false) Long id,
			@ApiParam(value = "Page value for pagination.", required = false, defaultValue = "0") @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@ApiParam(value = "Max record per pagen.", required = false, defaultValue = "10") @RequestParam(value = "size", required = false, defaultValue = "10") int size,
			UriComponentsBuilder uriBuilder, HttpServletResponse response) throws InvalidQueryException {

		if (id == null && date == null) {
			logger.error("At least one of id or date must be filled in.");
			throw new InvalidQueryException("At least one of id or date must be filled in.");
		}

		Conversion conversion = new Conversion();

		if (date != null) {
			if (date.matches(DATE_PATTERN)) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				try {
					conversion.setDate(formatter.parse(date));
				} catch (ParseException e) {
					logger.error("Error occured parsing date.", e);
					throw new InvalidQueryException("Error occured while parsing date.");
				}
			} else {
				logger.error("Date format is not correct.");
				throw new InvalidQueryException("Date format is not correct. Please adapt to 'dd-MM-yyyy' format. ");
			}
		}

		if (id != null) {
			conversion.setId(id);
		}

		Example<Conversion> example = Example.of(conversion);
		Page<Conversion> conversionList = conversionRepository.findAll(example, PageRequest.of(page, size));
		logger.debug("Fetching list is successfull.");

		if (conversionList == null)
			return new ArrayList<Conversion>();

		return conversionList.getContent();
	}

	@GetMapping(path = CONVERSION_PATH, params = { "sourceCurrency", "sourceAmount", "targetCurrency" })
	@ApiOperation(value = "Caclculates amount of target currency", response = Conversion.class)
	@Transactional(value = TxType.REQUIRED)
	public Conversion convert(
			@ApiParam(value = "Source Currency", required = true) @RequestParam(value = "sourceCurrency", required = true) String sourceCurrency,
			@ApiParam(value = "Source Amount", required = true) @RequestParam(value = "sourceAmount", required = true) Float sourceAmount,
			@ApiParam(value = "Target Currency", required = true) @RequestParam(value = "targetCurrency", required = true) String targetCurrency,
			UriComponentsBuilder uriBuilder, HttpServletResponse response)
			throws InvalidQueryException, ParseException {

		Float rate = clientController.callRestService(sourceCurrency, targetCurrency);
		logger.debug("Rest service call  is successfull");
		Conversion conversion = new Conversion(sourceCurrency, sourceAmount, targetCurrency);
		conversion.setTargetAmount(sourceAmount * rate);
		conversionRepository.save(conversion);
		logger.debug("Saccessfully ssaved to db {0} - {1} - {2}",sourceCurrency,sourceAmount, targetCurrency );
		return conversion;
	}

}
