package com.spring.batch.csv.json.springbatchcsvtojson.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.spring.batch.csv.json.springbatchcsvtojson.model.Employee;

@Component
public class CsvToJsonItemProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee item) throws Exception {
		return item;
	}

}
