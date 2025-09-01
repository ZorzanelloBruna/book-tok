package com.booktok.booktok.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationError {

	private String field;
    private Object rejectedValue;
    private String message;
}
