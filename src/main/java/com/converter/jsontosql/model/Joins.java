package com.converter.jsontosql.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Joins {

	private String joinName;

	private String primaryTableName;

	private String primaryField;

	private String secondaryTableName;

	private String secondaryField;
	
}
