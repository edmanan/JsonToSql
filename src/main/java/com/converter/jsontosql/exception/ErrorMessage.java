package com.converter.jsontosql.exception;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

	private int errorCode;
	private Timestamp errorTimestamp;
	private String errorName;
	private String errorDescription;
}
