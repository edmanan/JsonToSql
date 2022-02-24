package com.converter.jsontosql.converter;

import com.converter.jsontosql.model.QueryParameters;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public interface ConditionsConverter {
	StringBuilder convert(QueryParameters queryParameters, StringBuilder resultQueryStringBuilder, String tableName,
						  AtomicBoolean joinPresent, AtomicBoolean wherePresent, String Operator);
}
