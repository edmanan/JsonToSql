package com.converter.jsontosql.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryParameters implements Serializable {

	private String queryOperator;

	private String queryFilter;

	private String queryFieldName;

	private String queryFieldValue;

}
