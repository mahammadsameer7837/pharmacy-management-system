package de.io.pharmacy_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.io.pharmacy_management_system.exceptions.DoctorNotFoundException;
import de.io.pharmacy_management_system.exceptions.MedicineNotFoundException;
import de.io.pharmacy_management_system.exceptions.NoMedicinesAssignedException;
import de.io.pharmacy_management_system.exceptions.PatientNotFoundException;
import de.io.pharmacy_management_system.exceptions.PatientsLimitReachedException;
import de.io.pharmacy_management_system.model.BillCart;
import de.io.pharmacy_management_system.model.Patient;
import de.io.pharmacy_management_system.serviceImpl.PatientServicesImpl;

@RestController
@RequestMapping("/patient-services")
public class PatientController {
	
	@Autowired
	PatientServicesImpl patientServices;
	
	//Requirement-1: To add new patient into hospital
	@PostMapping("/add-patient")
	public Patient addPatient(@RequestBody Patient patient) throws PatientsLimitReachedException, MedicineNotFoundException, DoctorNotFoundException {
		System.out.println("Here-1");
		return patientServices.addPatient(patient);
	}
	//Requirement-3: Get Patient details
	@GetMapping("/get-patient/{id}")
	public Patient getPatient(@PathVariable int id) throws PatientNotFoundException {
		return patientServices.getPatient(id);
	}
	//Requirement-3: update patient details
	@PutMapping("/update-patient/{id}")
	public Patient updatePatient(@PathVariable int id, @RequestBody Patient patient) throws PatientNotFoundException, DoctorNotFoundException, MedicineNotFoundException {
		return patientServices.updatePatient(id, patient);
		
	}
	//Requirement-3: delete patient details
	@DeleteMapping("/delete-patient/{id}")
	public String deletePatient(@PathVariable int id) throws PatientNotFoundException {
		return patientServices.deletePatient(id) ;
		
	}
	//Requirement- 7: Produce bill of all the medicications assigned
	@GetMapping("/produce-bill/{id}")
	public BillCart produceBill(@PathVariable int id) throws PatientNotFoundException, NoMedicinesAssignedException {
		return patientServices.produceBill(id);
		
	}
	
	

}
