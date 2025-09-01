package com.booktok.booktok.exception;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	
	private int status;
    private String timestamp;
    private String path;
    private String message;
    private List<ValidationError> errors;
}
