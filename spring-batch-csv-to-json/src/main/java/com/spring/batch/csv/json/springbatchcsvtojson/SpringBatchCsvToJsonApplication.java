package com.spring.batch.csv.json.springbatchcsvtojson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchCsvToJsonApplication {

	public static void main(String[] args) {

		final Logger LOGGER = LoggerFactory.getLogger(SpringBatchCsvToJsonApplication.class);

		SpringApplication.run(SpringBatchCsvToJsonApplication.class, args);

		LOGGER.info("Application started!!!");

	}

}
