package com.parking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.parking.dto.ParkingSlotRequest;
import com.parking.entiry.ParkingSlot;
import com.parking.entiry.SlotStatus;
import com.parking.repository.ParkingSlotRepository;

@Service

public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;

    public ParkingSlotService(
            ParkingSlotRepository parkingSlotRepository) {

        this.parkingSlotRepository =
                parkingSlotRepository;
    }

    public ParkingSlot addSlot(ParkingSlotRequest request) {

        ParkingSlot slot = new ParkingSlot();

        slot.setSlotNumber(request.getSlotNumber());
        slot.setVehicleType(request.getVehicleType());
        slot.setStatus(SlotStatus.AVAILABLE);

        return parkingSlotRepository.save(slot);
    }

    public List<ParkingSlot> getAllSlots() {

        return parkingSlotRepository.findAll(); 
    }

    public List<ParkingSlot> getAvailableSlots() {

        return parkingSlotRepository.findByStatus(
                SlotStatus.AVAILABLE);
    }
}
