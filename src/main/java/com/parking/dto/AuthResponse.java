package com.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthResponse {
	private String message;
	private String token;
	
	public AuthResponse(String message, String token) {
		this.message = message;
		this.token=token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token=token;
	}

	@Override
	public String toString() {
		return "AuthResponse [message=" + message + ", token=" + token + "]";
	}
	
}
