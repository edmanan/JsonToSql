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

@Service(Constants.BETWEEN_CONDITION)
public class BetweenConditionConverter implements ConditionsConverter {

    private final ConverterHelperService converterHelperService;

    private static final Logger log = LoggerFactory.getLogger(BetweenConditionConverter.class);

    @Autowired
    public BetweenConditionConverter(ConverterHelperService converterHelperService){
        this.converterHelperService = converterHelperService;
    }

    @Override
    public StringBuilder convert(QueryParameters queryParameters, StringBuilder resultQueryStringBuilder, String tableName,
                                 AtomicBoolean joinPresent, AtomicBoolean wherePresent, String Operator) {
        converterHelperService.addWhereCondition(queryParameters, resultQueryStringBuilder, wherePresent);
        converterHelperService.addTableNameIfJoinExists(resultQueryStringBuilder, tableName, joinPresent);
        List<String> values = Arrays.asList(queryParameters.getQueryFieldValue().toUpperCase().split(","));
        resultQueryStringBuilder.append(queryParameters.getQueryFieldName()).append(Constants.EMPTY_STRING).append(Constants.BETWEEN_TEXT).append(Constants.EMPTY_STRING);
        if (StringUtils.isNumeric(StringUtils.remove(queryParameters.getQueryFieldValue(), ","))) {
            resultQueryStringBuilder.append(values.get(0)).append(Constants.EMPTY_STRING).append(Constants.AND_TEXT).append(Constants
                    .EMPTY_STRING).append(values.get(1));
        } else {
            resultQueryStringBuilder.append(Constants.SINGLE_QUOTE).append(values.get(0)).append(Constants.SINGLE_QUOTE).append(Constants.EMPTY_STRING).append(Constants.AND_TEXT).append(Constants.EMPTY_STRING).append(Constants.SINGLE_QUOTE).append(values.get(1))
                    .append(Constants.SINGLE_QUOTE);
        }
        log.info("Present Query with like : {}", resultQueryStringBuilder);
        return resultQueryStringBuilder;
    }
}


