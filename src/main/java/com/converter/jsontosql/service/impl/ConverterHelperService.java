package com.converter.jsontosql.service.impl;

import com.converter.jsontosql.model.Joins;
import com.converter.jsontosql.model.QueryParameters;
import com.converter.jsontosql.util.Constants;
import com.converter.jsontosql.util.StaticMapInitializer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ConverterHelperService {

    private static final Logger log = LoggerFactory.getLogger(ConverterHelperService.class);

    public void addJoinClauseToQuery(StringBuilder resultQueryStringBuilder, Joins joins, String joinType) {
        resultQueryStringBuilder.append(Constants
                        .EMPTY_STRING).append(StaticMapInitializer.joinsMap.get(joinType)).append(Constants.EMPTY_STRING).append(joins.getSecondaryTableName()).append(Constants.EMPTY_STRING).append(Constants.ON)
                .append(Constants.EMPTY_STRING).append(joins.getPrimaryTableName()).append(Constants.PERIOD).append(joins.getPrimaryField()).append(Constants.EQUAL_OPERATOR)
                .append(joins.getSecondaryTableName()).append(Constants.PERIOD).append(joins.getSecondaryField());
        log.info("Present Query with join: {}", resultQueryStringBuilder);
    }

    public void addTableNameIfJoinExists(StringBuilder resultQueryStringBuilder, String tableName, AtomicBoolean joinPresent) {
        if (joinPresent.get()) {
            resultQueryStringBuilder.append(Constants.EMPTY_STRING).append(tableName).append(Constants.PERIOD);
            log.info("Present Query with table name: {}", resultQueryStringBuilder);
        }
    }

    public void addWhereCondition(QueryParameters queryParameters, StringBuilder resultQueryStringBuilder,
                                  AtomicBoolean wherePresent) {
        if (wherePresent.get()) {
            switch (queryParameters.getQueryFilter().toUpperCase()) {
                case Constants.OR_TEXT:
                    resultQueryStringBuilder.append(Constants.EMPTY_STRING).append(Constants.OR_TEXT).append(Constants.EMPTY_STRING);
                    break;
                case Constants.AND_TEXT:
                    resultQueryStringBuilder.append(Constants.EMPTY_STRING).append(Constants.AND_TEXT).append(Constants.EMPTY_STRING);
            }
        } else {
            resultQueryStringBuilder.append(Constants.WHERE_TEXT);
            wherePresent.set(true);
        }
        log.info("Present Query with where: {}", resultQueryStringBuilder);
    }

    public StringBuilder getStringBuilder(QueryParameters queryParameters, StringBuilder resultQueryStringBuilder, String tableName, AtomicBoolean joinPresent, AtomicBoolean wherePresent, String Operator) {
        addWhereCondition(queryParameters, resultQueryStringBuilder, wherePresent);
        addTableNameIfJoinExists(resultQueryStringBuilder, tableName, joinPresent);
        resultQueryStringBuilder.append(queryParameters.getQueryFieldName()).append(Constants.EMPTY_STRING).append(StaticMapInitializer.conditionToOperatorMap.get(Operator));
        if (StringUtils.isNumeric(queryParameters.getQueryFieldValue())) {
            resultQueryStringBuilder.append(queryParameters.getQueryFieldValue());
        } else {
            resultQueryStringBuilder.append(Constants.SINGLE_QUOTE).append(queryParameters.getQueryFieldValue()).append(Constants.SINGLE_QUOTE);
        }
        log.info("Present Query with {} : {}", Operator,resultQueryStringBuilder);
        return resultQueryStringBuilder;
    }
}
