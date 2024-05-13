package com.whatsapp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetail> UserExceptionHandler(UserException e,WebRequest req)
	{
		ErrorDetail err =  new ErrorDetail(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(MessageException.class)
	public ResponseEntity<ErrorDetail> messageExceptionHandler(MessageException e,WebRequest req)
	{
		ErrorDetail err =  new ErrorDetail(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetail> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e,WebRequest req)
	{
		String error = e.getBindingResult().getFieldError().getDefaultMessage();
		ErrorDetail err =  new ErrorDetail("validation error",error,LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
		
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetail> NoHandlerFoundExceptionHandler(UserException e,WebRequest req)
	{
		ErrorDetail err =  new ErrorDetail("EndPoint not found",req.getDescription(false),LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(ChatException.class)
	public ResponseEntity<ErrorDetail> ChatExceptionHandler(ChatException e,WebRequest req)
	{
		ErrorDetail err =  new ErrorDetail(e.getMessage(),req.getDescription(false),LocalDateTime.now());
		
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	
}
