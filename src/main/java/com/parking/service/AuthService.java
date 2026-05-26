package com.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parking.dto.AuthResponse;
import com.parking.dto.LoginRequest;
import com.parking.dto.RegisterRequest;
import com.parking.entiry.Role;
import com.parking.entiry.User;
import com.parking.repository.UserRepository;
import com.parking.security.JwtUtil;

@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	@Autowired
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}
	
	
	public AuthResponse register(RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse("Email already exists", null);
            		
        }
		 User newUser= new User();
		 newUser.setName(request.getName());
		 newUser.setEmail(request.getEmail());
		 
		 String encryptedPass= passwordEncoder.encode(request.getPassword());
		 newUser.setPassword(encryptedPass);
		 
		 newUser.setRole(Role.USER);
		 userRepository.save(newUser);
		 String token = jwtUtil.generateToken(newUser.getEmail());
		 
		 return new AuthResponse(
	                "User Registered Successfully",
	                token
	        );
	}
	
	public AuthResponse login(LoginRequest request) {
		User user= userRepository.findByEmail(request.getEmail())
				.orElse(null);
		if(user==null) {
			return new AuthResponse("User Not Found", null);
		}
		
		boolean passwordMatch=passwordEncoder.matches(request.getPassword(), user.getPassword());
		
		if(!passwordMatch) {
			return new AuthResponse("Invalid Password", null);
		}
		
		String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                "Login Successful",
                token
        );
	}
}
