package de.io.pharmacy_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.io.pharmacy_management_system.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>{
	
	

}
