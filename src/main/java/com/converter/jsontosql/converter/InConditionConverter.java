package com.converter.jsontosql.converter;

import com.converter.jsontosql.model.QueryParameters;
import com.converter.jsontosql.service.impl.ConverterHelperService;
import com.converter.jsontosql.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service(Constants.IN_CONDITION)
public class InConditionConverter implements ConditionsConverter {

    private final ConverterHelperService converterHelperService;

    private static final Logger log = LoggerFactory.getLogger(InConditionConverter.class);

    @Autowired
    public InConditionConverter(ConverterHelperService converterHelperService){
        this.converterHelperService = converterHelperService;
    }

    @Override
    public StringBuilder convert(QueryParameters queryParameters, StringBuilder resultQueryStringBuilder, String tableName,
                                 AtomicBoolean joinPresent, AtomicBoolean wherePresent, String Operator) {
        converterHelperService.addWhereCondition(queryParameters, resultQueryStringBuilder, wherePresent);
        converterHelperService.addTableNameIfJoinExists(resultQueryStringBuilder, tableName, joinPresent);
        if (StringUtils.isNumeric(queryParameters.getQueryFieldValue())) {
            resultQueryStringBuilder.append(queryParameters.getQueryFieldName()).append(Constants.EMPTY_STRING).append(Constants.IN_CONDITION.toUpperCase()).append(Constants.EMPTY_STRING).append(Constants.OPENING_BRACKET).append(queryParameters.getQueryFieldValue())
                    .append(Constants.CLOSING_BRACKET);
        } else {
            List<String> values = Arrays.asList(queryParameters.getQueryFieldValue().toUpperCase().split(Constants.COMMA));
            StringUtils.join(values, new StringBuilder(Constants.SINGLE_QUOTE).append(Constants.COMMA).append(Constants.SINGLE_QUOTE).toString());
            resultQueryStringBuilder.append(queryParameters.getQueryFieldName()).append(Constants.EMPTY_STRING).append(Constants.IN_CONDITION.toUpperCase()).append(Constants.EMPTY_STRING).append(Constants.OPENING_BRACKET).append(Constants.SINGLE_QUOTE).append(queryParameters.getQueryFieldValue())
                    .append(Constants.SINGLE_QUOTE).append(Constants.CLOSING_BRACKET);
        }
        log.info("Present Query with like : {}", resultQueryStringBuilder);
        return resultQueryStringBuilder;
    }

}


