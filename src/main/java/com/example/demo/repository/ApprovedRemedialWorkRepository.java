package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Entity.ApprovedRemedialWork;


@Repository
public interface ApprovedRemedialWorkRepository extends JpaRepository<ApprovedRemedialWork, Integer> {

}
