package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Entity.ComponentManufacturers;


@Repository
public interface ComponentManufacturersRepository extends JpaRepository<ComponentManufacturers, Integer> {

}
