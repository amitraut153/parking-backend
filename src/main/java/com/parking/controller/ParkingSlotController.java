package com.parking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.dto.ParkingSlotRequest;
import com.parking.entiry.ParkingSlot;
import com.parking.service.ParkingSlotService;

@RestController
@RequestMapping("/api/slots")
public class ParkingSlotController {
	
	private final ParkingSlotService parkingSlotService;

	public ParkingSlotController(ParkingSlotService parkingSlotService) {
		this.parkingSlotService = parkingSlotService;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ParkingSlot addSlot(@RequestBody ParkingSlotRequest request) {
		return parkingSlotService.addSlot(request);
	}
	
	@GetMapping("/all")
	public List<ParkingSlot> getAllSlots(){
		return parkingSlotService.getAllSlots();
	}
	
	@GetMapping("/available")
	public List<ParkingSlot> getAvailableSlots(){
		return parkingSlotService.getAvailableSlots();
	}
}
