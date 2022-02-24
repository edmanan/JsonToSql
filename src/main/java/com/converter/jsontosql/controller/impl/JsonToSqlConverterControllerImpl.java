package com.converter.jsontosql.controller.impl;

import javax.validation.Valid;

import com.converter.jsontosql.constants.EndPointsRecognization;
import com.converter.jsontosql.controller.JsonToSqlConverterController;
import com.converter.jsontosql.service.impl.ConverterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.converter.jsontosql.model.ConvertRequest;

@RestController
@RequestMapping(EndPointsRecognization.API)
public class JsonToSqlConverterControllerImpl implements JsonToSqlConverterController {

	private static Logger log = LoggerFactory.getLogger(JsonToSqlConverterControllerImpl.class);

	private final ConverterServiceImpl sqlQueryServiceImpl;

	@Autowired
	public JsonToSqlConverterControllerImpl(ConverterServiceImpl sqlQueryServiceImpl) {
		this.sqlQueryServiceImpl = sqlQueryServiceImpl;
	}

	//api to convert json to SQL query
	@Override
	@PostMapping(value = EndPointsRecognization.CONVERT)
	public String convertJsonToSqlQuery(@RequestBody @Valid ConvertRequest request) throws Exception {
		log.info("Entered into JsonToSqlConverterControllerImpl with request : {}", request.toString());
		return sqlQueryServiceImpl.convertJsonToSqlQuery(request);
	}

}
