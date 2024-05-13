package com.whatsapp.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorDetail {
	
	private String error;
	private String message;
	private LocalDateTime timeStamp;
	
	public ErrorDetail()
	{
		
	}
	

}
