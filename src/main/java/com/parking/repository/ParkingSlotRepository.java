package com.parking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.entiry.ParkingSlot;
import com.parking.entiry.SlotStatus;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long>{
	
	List<ParkingSlot> findByStatus(SlotStatus status);
}
