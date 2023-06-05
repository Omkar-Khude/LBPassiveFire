package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.Entity.Door;
import com.example.demo.repository.DoorRepository;


@Service
public class DoorService {
	
	private final DoorRepository doorRepository;

    public DoorService(DoorRepository doorRepository) {
        this.doorRepository = doorRepository;
    }

    public Door saveDoor(Door door) {
        return doorRepository.save(door);
    }

}
