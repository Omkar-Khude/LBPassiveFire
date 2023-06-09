package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Entity.Remediation;

@Repository
public interface RemediationRepository extends JpaRepository<Remediation, Integer> {

	Remediation findById(int id);

}
