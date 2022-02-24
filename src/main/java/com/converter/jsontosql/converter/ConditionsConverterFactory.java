package com.converter.jsontosql.converter;

import com.converter.jsontosql.Enums.ConditionType;
import com.converter.jsontosql.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ConditionsConverterFactory {

	private final Map<String, ConditionsConverter> conditionsConverters;

	public ConditionsConverter getConditionType(ConditionType conditionType) throws Exception {
		ConditionsConverter downloadReportFormatter = conditionsConverters.get(conditionType.name());
		if (downloadReportFormatter == null) {
			throw new Exception(Constants.INVALID_CONDITION_TYPE);
		}
		return downloadReportFormatter;
	}

}
