package de.io.pharmacy_management_system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

 
@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(value=MedicineAlreadyFoundException.class)
	public ResponseEntity<Object> exceptionOne(MedicineAlreadyFoundException exception){
		
		return new ResponseEntity<>("Medicine already found",HttpStatus.ALREADY_REPORTED);
		
	} 

	@ExceptionHandler(value=MedicineNotFoundException.class)
	public ResponseEntity<Object> exceptionTwo(MedicineNotFoundException exception){
		
		return new ResponseEntity<>("Medicine not found",HttpStatus.NOT_FOUND);
		
	} 
	
	@ExceptionHandler(value=DiseaseAllreadyFoundException.class)
	public ResponseEntity<Object> exceptionThree(DiseaseAllreadyFoundException exception){
		
		return new ResponseEntity<>("Disease already found",HttpStatus.ALREADY_REPORTED);
		
	} 
	
	@ExceptionHandler(value=DiseaseNotFoundException.class)
	public ResponseEntity<Object> exceptionFour(DiseaseNotFoundException exception){
		
		return new ResponseEntity<>("Disease not found",HttpStatus.NOT_FOUND);
		
	} 
	@ExceptionHandler(value=PatientsLimitReachedException.class)
	public ResponseEntity<Object> exceptionFive(PatientsLimitReachedException exception){
		
		return new ResponseEntity<>("Patients Limit Reached",HttpStatus.NOT_FOUND);
		
	} 
	
	@ExceptionHandler(value=DoctorAlreadyExistWithIdTException.class)
	public ResponseEntity<Object> exceptionSix(DoctorAlreadyExistWithIdTException exception){
		
		return new ResponseEntity<>("Docotor Already Exist With IdT given",HttpStatus.ALREADY_REPORTED);
		
	} 
	
	@ExceptionHandler(value=DoctorNotFoundException.class)
	public ResponseEntity<Object> exceptionSeven(DoctorNotFoundException exception){
		
		return new ResponseEntity<>("Docotor not found",HttpStatus.NOT_FOUND);
		
	} 
	

	@ExceptionHandler(value=PatientNotFoundException.class)
	public ResponseEntity<Object> exceptionEight(PatientNotFoundException exception){
		
		return new ResponseEntity<>("Patient not found",HttpStatus.NOT_FOUND);
		
	} 
	
	@ExceptionHandler(value=DoctorIdMissingException.class)
	public ResponseEntity<Object> exceptionNine(DoctorIdMissingException exception){
		
		return new ResponseEntity<>("Doctor ID OR Doctor IDT is Required",HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(value=EmptyMedicineSetException.class)
	public ResponseEntity<Object> exceptionTen(EmptyMedicineSetException exception){
		
		return new ResponseEntity<>("Medicine Set or Medicine is missing",HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(value=NoMedicinesAssignedException.class)
	public ResponseEntity<Object> exceptionEleven(NoMedicinesAssignedException exception){
		
		return new ResponseEntity<>("No medicines assigned so far",HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(value=MedicineNameTakenException.class)
	public ResponseEntity<Object> exceptionEleven(MedicineNameTakenException exception){
		
		return new ResponseEntity<>("Medicine with same name already exists",HttpStatus.NOT_FOUND);
		
	}
	
}
