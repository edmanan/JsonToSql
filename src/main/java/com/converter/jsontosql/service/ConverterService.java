package com.converter.jsontosql.service;

import com.converter.jsontosql.model.ConvertRequest;

public interface ConverterService {
	String convertJsonToSqlQuery(ConvertRequest convertRequest) throws Exception;
}
