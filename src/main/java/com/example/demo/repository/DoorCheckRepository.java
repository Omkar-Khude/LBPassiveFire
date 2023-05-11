package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.DoorCheck;

@Repository
public interface DoorCheckRepository extends JpaRepository<DoorCheck, Integer> {

}
