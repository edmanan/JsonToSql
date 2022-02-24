package com.converter.jsontosql.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import com.converter.jsontosql.model.ConvertRequest;

public interface JsonToSqlConverterController {

	String convertJsonToSqlQuery(@RequestBody @Valid ConvertRequest request) throws Exception;

}
