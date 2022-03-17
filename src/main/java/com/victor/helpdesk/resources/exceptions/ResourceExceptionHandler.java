package com.victor.helpdesk.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.victor.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.victor.helpdesk.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFoundException(ObjectNotFoundException e,
			HttpServletRequest request) {

		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Object not found", e.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandartError> dataIntegrityViolationException(DataIntegrityViolationException e,
			HttpServletRequest request) {

		StandartError error = new StandartError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Violação de dados", e.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandartError> methodArgumentNotValidException(MethodArgumentNotValidException e,
			HttpServletRequest request) {

		ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Validation error", "Erro na validação dos campos", request.getRequestURI());

		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			errors.addError(error.getField(), error.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
