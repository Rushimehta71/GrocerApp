package com.grocerybookingapp.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.grocerybookingapp.other.ApiResponseMessage;

//this annotation takes care in whole project if we get this exception then it will handle
@RestControllerAdvice
public class GlobalExceptionHandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		logger.info("resourceNotFoundExceptionHandler Global Exception handlr invoked");
		ApiResponseMessage apiResponse = ApiResponseMessage.builder().message(ex.getMessage())
				.status(HttpStatus.NOT_FOUND).success(true).build();
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException ex) {
		logger.info("methodArgumentNotValidExceptionHandler Global Exception handlr invoked");
		List<ObjectError> allError = ex.getAllErrors();
		Map<String, Object> responce = new HashMap<>();
		allError.stream().forEach(objectError -> {
			String messageString = objectError.getDefaultMessage();
			String fieldString = ((FieldError) objectError).getField();
			responce.put(fieldString, messageString);
		});
		return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadApiRequestException.class)
	public ResponseEntity<ApiResponseMessage> badApiRequestExceptionHandler(BadApiRequestException ex) {
		logger.info("BadApiRequest Global Exception handlr invoked");
		ApiResponseMessage apiResponse = ApiResponseMessage.builder().message(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST).success(false).build();
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ApiResponseMessage> warongAPIExceptionHandler(NoHandlerFoundException ex) {
		logger.info("BadApiRequest Global Exception handlr invoked");
		ApiResponseMessage apiResponse = ApiResponseMessage.builder()
				.message("{ Wrong EndPoint please check path }  " + ex.getMessage()).status(HttpStatus.NOT_FOUND)
				.success(false).build();
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponseMessage> accessDeniedRequestExceptionHandler(AccessDeniedException ex) {
		logger.info("BadApiRequest Global Exception handlr invoked");
		ApiResponseMessage apiResponse = ApiResponseMessage.builder().message(ex.getMessage())
				.status(HttpStatus.FORBIDDEN).success(false).build();
		return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
	}

}
