package de.io.pharmacy_management_system.service;

import java.util.List;

import de.io.pharmacy_management_system.exceptions.DoctorAlreadyExistWithIdTException;
import de.io.pharmacy_management_system.exceptions.DoctorIdMissingException;
import de.io.pharmacy_management_system.exceptions.DoctorNotFoundException;
import de.io.pharmacy_management_system.model.Doctor;
import de.io.pharmacy_management_system.model.Patient;

public interface DoctorService {
	
	
	public void addPatienttoDoctor(Patient patient) throws DoctorNotFoundException ; // l1 

	public String deleteDoctor(String doctorIdT) throws DoctorNotFoundException;//l1

	public String deleteDoctor(int doctorId) throws DoctorNotFoundException;//l1

	public Doctor updateDoctor(Doctor doctor) throws DoctorNotFoundException, DoctorIdMissingException, DoctorAlreadyExistWithIdTException;//l1

	public Doctor updateDoctor(String docotorIdT, Doctor doctor) throws DoctorNotFoundException, DoctorAlreadyExistWithIdTException;//l1

	public Doctor updateDoctor(int docotorId, Doctor doctor) throws DoctorNotFoundException, DoctorAlreadyExistWithIdTException;//l1

	public Doctor addDoctor(Doctor doctor) throws DoctorAlreadyExistWithIdTException;//l1

	public Doctor findDoctorById(String doctorIdT) throws DoctorNotFoundException;// l1

	public Doctor findDoctorById(int id) throws DoctorNotFoundException;// l1

	List<Doctor> addDoctorList(List<Doctor> doctorsList) throws DoctorAlreadyExistWithIdTException;

}
