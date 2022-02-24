package com.converter.jsontosql.service.impl;

import com.converter.jsontosql.Enums.ConditionType;
import com.converter.jsontosql.converter.ConditionsConverter;
import com.converter.jsontosql.converter.ConditionsConverterFactory;
import com.converter.jsontosql.model.ConvertRequest;
import com.converter.jsontosql.model.Joins;
import com.converter.jsontosql.model.QueryParameters;
import com.converter.jsontosql.model.Table;
import com.converter.jsontosql.service.ConverterService;
import com.converter.jsontosql.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ConverterServiceImpl implements ConverterService {

    private static final Logger log = LoggerFactory.getLogger(ConverterServiceImpl.class);

    private final ConverterHelperService sqlQueryHelperService;
    private final ConditionsConverterFactory conditionsConverterFactory;

    @Autowired
    public ConverterServiceImpl(ConverterHelperService sqlQueryHelperService, ConditionsConverterFactory conditionsConverterFactory){
        this.sqlQueryHelperService = sqlQueryHelperService;
        this.conditionsConverterFactory = conditionsConverterFactory;
    }

    @Override
    public String convertJsonToSqlQuery(ConvertRequest request) throws Exception {
        validateJsonRequest(request);
        AtomicBoolean joinPresent = new AtomicBoolean();
        AtomicBoolean wherePresent = new AtomicBoolean();
        StringBuilder resultQueryStringBuilder = new StringBuilder();
        int tableCount = 0;
        if ((request.getJoins() != null) && (request.getJoins().size() > 0)) {
            joinPresent.set(true);
        }
        for (Table table : request.getTables()) {
            String tableName = table.getTableName();
            if (tableCount == 0) {
                resultQueryStringBuilder.append(Constants.SELECT_CLAUSE).append(table.getTableName());
                log.info("Present Query after select clause: {}", resultQueryStringBuilder);
                tableCount++;
                if (joinPresent.get()) {
                    addJoinClauseToQuery(resultQueryStringBuilder, request.getJoins());
                    log.info("Present Query after join : {}", resultQueryStringBuilder);
                }
            }
            resultQueryStringBuilder = setQueryParameters(resultQueryStringBuilder, table, tableName, joinPresent, wherePresent);
        }
        resultQueryStringBuilder.append(Constants.SEMI_COLON).append(Constants.EMPTY_STRING);
        log.info("Final Query : {}", resultQueryStringBuilder);
        return resultQueryStringBuilder.toString();
    }

    private void validateJsonRequest(ConvertRequest request) throws Exception {
        log.info("Validating input request");
        String customErrorMessage = null;
        if (Objects.isNull(request)) {
            customErrorMessage = Constants.INCORRECT_FORMAT;
        }

        if (Objects.isNull(request.getTables())) {
            customErrorMessage = Constants.INCORRECT_FORMAT_TABLE;
        }

        if (Objects.nonNull(request.getTables()) && (request.getTables().size() == 0)) {
            customErrorMessage = Constants.INCORRECT_FORMAT_TABLE;
        }

        if (Objects.nonNull(request.getTables()) && (request.getTables().size() > 1)
                && ((request.getJoins() == null) || (request.getJoins().size() == 0))) {
            customErrorMessage = Constants.INCORRECT_FORMAT_JOIN;
        }
        if (customErrorMessage != null) {
            log.info("Validation failed with error : {}", customErrorMessage);
            throw new Exception(customErrorMessage);
        }
        log.info("Validation of input request is successful");
    }

    private StringBuilder setQueryParameters(StringBuilder resultQueryStringBuilder, Table table, String tableName, AtomicBoolean joinPresent,
                                            AtomicBoolean wherePresent) throws Exception {
        if ((table.getQueryParameters() != null) && table.getQueryParameters().size() > 0) {
            List<QueryParameters> queryParametersList = table.getQueryParameters();
            for (QueryParameters queryParameters : queryParametersList) {
                if (StringUtils.isNotEmpty(queryParameters.getQueryOperator())) {
                    resultQueryStringBuilder = processCondition(resultQueryStringBuilder, queryParameters, tableName, joinPresent, wherePresent);
                }
            }
        }
        return resultQueryStringBuilder;
    }

    private StringBuilder processCondition(StringBuilder resultQueryStringBuilder, QueryParameters queryParameters, String tableName,
                                           AtomicBoolean joinPresent, AtomicBoolean wherePresent) throws Exception {
        ConditionsConverter converter = conditionsConverterFactory.getConditionType(ConditionType.valueOf(queryParameters.getQueryOperator().toLowerCase()));
        log.info("Adding conditions to the Query");
        return converter.convert(queryParameters, resultQueryStringBuilder, tableName, joinPresent, wherePresent, queryParameters.getQueryOperator().toLowerCase());
    }

    private void addJoinClauseToQuery(StringBuilder resultQueryStringBuilder, List<Joins> joins) {
        for (Joins join : joins) {
            sqlQueryHelperService.addJoinClauseToQuery(resultQueryStringBuilder, join, join.getJoinName().toLowerCase());
        }
    }
}