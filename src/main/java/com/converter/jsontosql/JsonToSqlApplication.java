package com.converter.jsontosql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class JsonToSqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonToSqlApplication.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
	}

}
