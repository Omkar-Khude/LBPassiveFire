package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Entity.Door;

@Repository
public interface DoorRepository extends JpaRepository<Door, Integer> {
	Door findById(int id);

}
