package com.exchange.services.restapi.client.controller;

import static com.exchange.services.constants.ControllerConstants.CLIENT_PATH_FOR_MAPPING;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exchange.services.exception.InvalidQueryException;
import com.exchange.services.model.Conversion;
import com.exchange.services.restapi.client.model.Base;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(CLIENT_PATH_FOR_MAPPING)
@Api(value = "Restapi.io client controller ")
public class ClientController {
	
	private static Logger logger = LoggerFactory.getLogger(ClientController.class);
	 
	public static final String RESTSERVICE_PATH="/convert/{sourceCurrency}/{targetCurrency}";

	@GetMapping(RESTSERVICE_PATH)
	@ApiOperation(value = "Caclculates amount of target currency. It utilizes 'https://ratesapi.io/documentation' ", response = Conversion.class)
	public Float callRestService(
			@ApiParam(value = "Source Currency (Base)") @PathVariable(value = "sourceCurrency") String base,
			@ApiParam(value = "Target Currency (Symbol)") @PathVariable(value = "targetCurrency") String symbol)
			throws InvalidQueryException {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.add("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			ResponseEntity<Base> result = restTemplate.exchange(
					"https://api.ratesapi.io/api/latest?base=" + base + "&symbols=" + symbol, HttpMethod.GET, entity,
					Base.class);
			return result.getBody().getRates().get(symbol);
		} catch (Exception e) {
			logger.error("Remote query is not acceptable.",e);
			throw new InvalidQueryException("Remote query is not acceptable.");
		}

	}
}
