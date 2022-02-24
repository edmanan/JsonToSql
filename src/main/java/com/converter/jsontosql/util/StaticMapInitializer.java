package com.converter.jsontosql.util;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class StaticMapInitializer {

    public static final Logger log = LoggerFactory.getLogger(StaticMapInitializer.class);

    public static final Map<String, String> conditionToOperatorMap = new HashMap<>();
    public static final Map<String, String> joinsMap = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            populateCountryIdMap(conditionToOperatorMap);
            populateJoinsMap(joinsMap);
        } catch (Exception e) {
            log.error(String.format("Error {%s} while populating static map ", e));
        }
    }

    private void populateCountryIdMap(Map<String, String> conditionToOperatorMap) {
        conditionToOperatorMap.put(Constants.EQUAL_CONDITION.toLowerCase(), Constants.EQUAL_OPERATOR);
        conditionToOperatorMap.put(Constants.GREATER_THAN_CONDITION.toLowerCase(), Constants.GREATER_THAN_OPERATOR);
        conditionToOperatorMap.put(Constants.LESS_THAN_CONDITION.toLowerCase(), Constants.LESS_THAN_OPERATOR);
        conditionToOperatorMap.put(Constants.GREATER_THAN_EQUAL_CONDITION.toLowerCase(), Constants.GREATER_THAN_EQUAL_OPERATOR);
        conditionToOperatorMap.put(Constants.LESS_THAN_EQUAL_CONDITION.toLowerCase(), Constants.LESS_THAN_EQUAL_OPERATOR);
        conditionToOperatorMap.put(Constants.NOT_EQUAL_CONDITION.toLowerCase(), Constants.NOT_EQUAL_OPERATOR);
    }

    private void populateJoinsMap(Map<String, String> joinsMap) {
        joinsMap.put(Constants.INNER_TEXT.toLowerCase(), Constants.INNER_JOIN);
        joinsMap.put(Constants.LEFT_TEXT.toLowerCase(), Constants.LEFT_JOIN);
        joinsMap.put(Constants.RIGHT_TEXT.toLowerCase(), Constants.RIGHT_JOIN);
        joinsMap.put(Constants.FULL_TEXT.toLowerCase(), Constants.FULL_OUTER_JOIN);
    }

}
