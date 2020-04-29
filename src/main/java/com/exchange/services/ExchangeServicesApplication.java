package com.exchange.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExchangeServicesApplication {

	private static Logger logger = LoggerFactory.getLogger(ExchangeServicesApplication.class);

	public static void main(String[] args) {
		logger.info("Starting Exchange Services...");
		SpringApplication.run(ExchangeServicesApplication.class, args);
	}
}
