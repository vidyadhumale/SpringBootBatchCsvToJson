package com.spring.batch.csv.json.springbatchcsvtojson.config;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.SynchronizedItemStreamWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.spring.batch.csv.json.springbatchcsvtojson.batch.EmployeeFieldSetMapper;
import com.spring.batch.csv.json.springbatchcsvtojson.model.Employee;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {

	final Logger LOGGER = LoggerFactory.getLogger(SpringBatchConfiguration.class);

	@Bean
	public Job createJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemReader<Employee> itemReader, ItemProcessor<Employee, Employee> itemProcessor,
			ItemWriter<Employee> itemWriter) {

		LOGGER.info("Inside createJob: ");
		Step step = stepBuilderFactory.get("ETL-file-load").<Employee, Employee>chunk(2).reader(itemReader)
				.processor(itemProcessor).writer(itemWriter).taskExecutor(taskExcutor()).build();

		return jobBuilderFactory.get("ETL-load").incrementer(new RunIdIncrementer()).start(step).build();

	}

	@Bean
	public TaskExecutor taskExcutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(64);
		executor.setMaxPoolSize(64);
		executor.setQueueCapacity(64);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setThreadNamePrefix("MultiThreaded-");
		return executor;
	}

	@Bean
	public SynchronizedItemStreamReader<Employee> itemReader(@Value("${input.file}") Resource resource) {
		FlatFileItemReader<Employee> flatFileItemReader = new FlatFileItemReader<Employee>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		SynchronizedItemStreamReader<Employee> synchronizedItemStreamReader = new SynchronizedItemStreamReader<>();
		synchronizedItemStreamReader.setDelegate(flatFileItemReader);
		return synchronizedItemStreamReader;
	}

	@Bean
	public LineMapper<Employee> lineMapper() {

		DefaultLineMapper<Employee> defaultLineMapper = new DefaultLineMapper<Employee>();
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(false);

		delimitedLineTokenizer.setNames("first_name", "last_name", "company_name", "address", "city", "country",
				"state", "zip", "phone1", "phone2", "email", "web");

		EmployeeFieldSetMapper employeeFieldSetMapper = new EmployeeFieldSetMapper();

		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		defaultLineMapper.setFieldSetMapper(employeeFieldSetMapper);

		return defaultLineMapper;

	}

	@Bean
	public SynchronizedItemStreamWriter<Employee> jsonItemWriter(@Value("${output.file}") String output)throws IOException {

		JsonFileItemWriterBuilder<Employee> builder = new JsonFileItemWriterBuilder<>();
		JacksonJsonObjectMarshaller<Employee> marshaller = new JacksonJsonObjectMarshaller<>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
		marshaller.setObjectMapper(objectMapper);
		JsonFileItemWriter<Employee> jsonItemWriter = null;
		FileSystemResource fileSystemResource = new FileSystemResource(output);
		jsonItemWriter = builder.name("empItemWriter").jsonObjectMarshaller(marshaller)
				.resource(fileSystemResource).build();
		SynchronizedItemStreamWriter<Employee> synchronizedItemStreamWriter = new SynchronizedItemStreamWriter<Employee>();
		synchronizedItemStreamWriter.setDelegate(jsonItemWriter);
		return synchronizedItemStreamWriter;
	}
}
