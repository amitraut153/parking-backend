package com.parking.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private static final String  SECRET="mysecretkeymysecretkeymysecretkeymysecretkey";
	
	private final SecretKey key=Keys.hmacShaKeyFor(SECRET.getBytes());
	
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()
						+1000*60*60))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String extractEmail(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean isTokenValid(String token, String email) {
		String extractedEmail=extractEmail(token);
		return extractedEmail.equals(email);
	}
}
