package com.subhadip.practice.reactive.fluxMonoTest;

public class CustomException extends Throwable{

	private String message;
	
	CustomException(Throwable e){
		this.message=e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
