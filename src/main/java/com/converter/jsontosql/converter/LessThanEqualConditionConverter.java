package com.converter.jsontosql.converter;

import com.converter.jsontosql.model.QueryParameters;
import com.converter.jsontosql.service.impl.ConverterHelperService;
import com.converter.jsontosql.util.Constants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service(Constants.LESS_THAN_EQUAL_CONDITION)
public class LessThanEqualConditionConverter implements ConditionsConverter {

    private final ConverterHelperService converterHelperService;

    @Autowired
    public LessThanEqualConditionConverter(ConverterHelperService converterHelperService){
        this.converterHelperService = converterHelperService;
    }

    @Override
    public StringBuilder convert(QueryParameters queryParameters, StringBuilder resultQueryStringBuilder, String tableName,
                                 AtomicBoolean joinPresent, AtomicBoolean wherePresent, String Operator) {
        return converterHelperService.getStringBuilder(queryParameters, resultQueryStringBuilder, tableName, joinPresent, wherePresent, Operator);
    }
}
