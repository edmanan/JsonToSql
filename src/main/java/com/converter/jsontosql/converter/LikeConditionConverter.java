package com.converter.jsontosql.converter;

import com.converter.jsontosql.model.QueryParameters;
import com.converter.jsontosql.service.impl.ConverterHelperService;
import com.converter.jsontosql.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service(Constants.LIKE_CONDITION)
public class LikeConditionConverter implements ConditionsConverter {

    private final ConverterHelperService converterHelperService;

    private static final Logger log = LoggerFactory.getLogger(LikeConditionConverter.class);

    @Autowired
    public LikeConditionConverter(ConverterHelperService converterHelperService){
        this.converterHelperService = converterHelperService;
    }

    @Override
    public StringBuilder convert(QueryParameters queryParameters, StringBuilder resultQueryStringBuilder, String tableName,
                                 AtomicBoolean joinPresent, AtomicBoolean wherePresent, String Operator) {
        converterHelperService.addWhereCondition(queryParameters, resultQueryStringBuilder, wherePresent);
        converterHelperService.addTableNameIfJoinExists(resultQueryStringBuilder, tableName, joinPresent);
        resultQueryStringBuilder.append(queryParameters.getQueryFieldName()).append(Constants.LIKE_STAR_CLAUSE).append(queryParameters.getQueryFieldValue())
                .append(Constants.PERCENTAGE).append(Constants.SINGLE_QUOTE);
        log.info("Present Query with like : {}", resultQueryStringBuilder);
        return resultQueryStringBuilder;
    }
}


