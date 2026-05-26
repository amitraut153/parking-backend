package com.parking.service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.parking.dto.BookingRequest;
import com.parking.entiry.Booking;
import com.parking.entiry.ParkingSlot;
import com.parking.entiry.SlotStatus;
import com.parking.entiry.User;
import com.parking.repository.BookingRepository;
import com.parking.repository.ParkingSlotRepository;
import com.parking.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final ParkingSlotRepository parkingSlotRepository;

	public Booking bookSlot(BookingRequest request, String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		ParkingSlot slot = parkingSlotRepository.findById(request.getSlotId())
				.orElseThrow(() -> new RuntimeException("Slot Not Found"));

		if (slot.getStatus() == SlotStatus.BOOKED) {
			throw new RuntimeException("Slot Already Booked");
		}
		slot.setStatus(SlotStatus.BOOKED);
		parkingSlotRepository.save(slot);

		Booking booking = new Booking();
		booking.setBookingTime(LocalDateTime.now());
		booking.setUser(user);
		booking.setParkingSlot(slot);

		return bookingRepository.save(booking);
	}

	public List<Booking> getMyBookings(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		return bookingRepository.findByUser(user);
	}

	public String cancelBooking(Long bookingId, String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking Not Found"));

		// SECURITY CHECK
		if (!booking.getUser().getId().equals(user.getId())) {

			throw new RuntimeException("You cannot cancel this booking");
		}

		ParkingSlot slot = booking.getParkingSlot();

		slot.setStatus(SlotStatus.AVAILABLE);

		parkingSlotRepository.save(slot);

		bookingRepository.delete(booking);

		return "Booking Cancelled Successfully";
	}
}
