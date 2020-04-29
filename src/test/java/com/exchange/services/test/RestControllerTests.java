package com.exchange.services.test;

import static com.exchange.services.constants.ControllerConstants.CLIENT_PATH_FOR_QUERY;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.exchange.services.restapi.client.controller.ClientController;

@WebMvcTest( ClientController.class )
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class RestControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testClient() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get(CLIENT_PATH_FOR_QUERY.concat(ClientController.RESTSERVICE_PATH), "USD", "TRY")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists());
	}
}
