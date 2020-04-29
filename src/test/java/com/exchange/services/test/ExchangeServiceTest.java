package com.exchange.services.test;

import static com.exchange.services.constants.ControllerConstants.EXCHANGE_PATH_FOR_QUERY;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.websocket.ClientEndpoint;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import com.exchange.services.controller.ExchangeServiceController;
import com.exchange.services.repository.ConversionRepository;
import com.exchange.services.restapi.client.controller.ClientController;

@WebMvcTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ExchangeServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ConversionRepository conversionRepository;
	
	@MockBean
	ClientController clientController;

	@Test
	public void testConvert() throws Exception {

		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("sourceCurrency", "USD");
		requestParams.add("targetCurrency", "TRY");
		requestParams.add("sourceAmount", "1000");

		mockMvc.perform(
				MockMvcRequestBuilders.get(EXCHANGE_PATH_FOR_QUERY.concat(ExchangeServiceController.CONVERSION_PATH))
						.params(requestParams).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.sourceCurrency").exists());
	}

	@Test
	public void testConversionList() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("id", "1");

		mockMvc.perform(MockMvcRequestBuilders
				.get(EXCHANGE_PATH_FOR_QUERY.concat(ExchangeServiceController.LIST_CONVERSION_PATH))
				.params(requestParams).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists());
	}
}
