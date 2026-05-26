package com.parking.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.dto.BookingRequest;
import com.parking.entiry.Booking;
import com.parking.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
	
	private final BookingService bookingService;
	
	@PostMapping("/book")
	public Booking bookSlot(@RequestBody BookingRequest request, Authentication authentication) {
		return bookingService.bookSlot(request, authentication.getName());
	}
	@GetMapping("/my")
	public List<Booking> getMyBookings(Authentication authentication){
			return bookingService.getMyBookings(authentication.getName());
	}
	
	@DeleteMapping("/cancel/{bookingId}")
	public String cancelBooking(
	        @PathVariable Long bookingId,
	        Authentication authentication) {

	    return bookingService.cancelBooking(
	            bookingId,
	            authentication.getName());
	}
}
