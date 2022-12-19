package de.io.pharmacy_management_system.serviceImpl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.io.pharmacy_management_system.exceptions.DoctorNotFoundException;
import de.io.pharmacy_management_system.exceptions.MedicineNotFoundException;
import de.io.pharmacy_management_system.exceptions.NoMedicinesAssignedException;
import de.io.pharmacy_management_system.exceptions.PatientNotFoundException;
import de.io.pharmacy_management_system.exceptions.PatientsLimitReachedException;
import de.io.pharmacy_management_system.model.BillCart;
import de.io.pharmacy_management_system.model.Doctor;
import de.io.pharmacy_management_system.model.Medicine;
import de.io.pharmacy_management_system.model.Patient;
import de.io.pharmacy_management_system.repository.DoctorRepository;
import de.io.pharmacy_management_system.repository.MedicineRepository;
import de.io.pharmacy_management_system.repository.PatientRepository;
import de.io.pharmacy_management_system.service.PatientService;

@Service
public class PatientServicesImpl implements PatientService {

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	MedicineRepository medicineRepository;

	@Autowired
	DoctorServicesImpl doctorServices;

	// ADD PATIENT
	@Override
	public Patient addPatient(Patient newPatient)
			throws PatientsLimitReachedException, MedicineNotFoundException, DoctorNotFoundException {
		//Requirement-6: Not more than fifteen patients are allowed in to hospital
		if (patientRepository.count() == 15) {
			throw new PatientsLimitReachedException(); // throws patients limit reached exception
		}
		
		 
		
		for (Medicine medicine : newPatient.getMedicationsList()) {
			
			if (medicineRepository.findById(medicine.getMedicineId()).isEmpty()) {
				throw new MedicineNotFoundException();
			}
			
		}
		
		 
		
		newPatient.setMedicationCount(newPatient.getMedicationsList().size());
		
		if (newPatient.getDoctorId() != 0) {
			
			 
			//patientRepository.save(newPatient);
			doctorServices.addPatienttoDoctor(newPatient);
			 
		}
		else {
		 
		return patientRepository.save(newPatient);
	}
		return newPatient;
		
	}

	//Update patient
	@Override
	public Patient updatePatient(int id, Patient modifiedPatient)
			throws PatientNotFoundException, DoctorNotFoundException, MedicineNotFoundException {

		Patient oldpatient = getPatient(id);

		if (modifiedPatient.getPatientName() != null) {

			oldpatient.setPatientName(modifiedPatient.getPatientName());

		}

		if (modifiedPatient.getDateOfDischarge() != null) {

			oldpatient.setDateOfDischarge(modifiedPatient.getDateOfDischarge());
		}
		if (modifiedPatient.getAddress() != null) {
			oldpatient.setAddress(modifiedPatient.getAddress());
		}
		if (modifiedPatient.getBillAmountPaid() != null) {

			oldpatient.setBillAmountPaid(modifiedPatient.getBillAmountPaid());
		}
		if (modifiedPatient.getAdmittedReason() != null) {

			oldpatient.setAdmittedReason(modifiedPatient.getAdmittedReason());
		}
		if (modifiedPatient.getDoctorId() != 0) {
			System.out.println("Here--33");
				oldpatient.setDoctorId(modifiedPatient.getDoctorId());
				System.out.println("Here---44");
				doctorServices.addPatienttoDoctor(oldpatient);
		}
		if(!modifiedPatient.getMedicationsList().isEmpty()) {
			//oldpatient.setMedicationsList(modifiedPatient.getMedicationsList());
			for(Medicine medicineGiven:modifiedPatient.getMedicationsList()) {
				if(medicineRepository.findById(medicineGiven.getMedicineId()).isEmpty()) {
					throw new MedicineNotFoundException();
				}
			}
			
			oldpatient.getMedicationsList().addAll(modifiedPatient.getMedicationsList());
			oldpatient.setMedicationCount(oldpatient.getMedicationsList().size());
		}
		return patientRepository.save(oldpatient);
	}

	//Get Patient details by taking id
	@Override
	public Patient getPatient(int id) throws PatientNotFoundException {
		 
		Optional<Patient> patient = patientRepository.findById(id);
		if (patient.isEmpty()) {
			throw new PatientNotFoundException();
		}
		return patient.get();

	}

	//Delete Patient
	@Override
	public String deletePatient(int id) throws PatientNotFoundException {
		
		doctorServices.removePatientFromDoctor(getPatient(id));
		patientRepository.deleteById(id);
		return "Successfully Deleted";
	}

	//Produce bill for the mediction assigned
	@Override
	public BillCart produceBill(int patientId) throws PatientNotFoundException, NoMedicinesAssignedException {
		Patient patient;
		try {
			patient = patientRepository.findById(patientId).get();
		}
		catch(Exception ex) {
			throw new PatientNotFoundException();
		}
		Set<Medicine> medicineSet = patient.getMedicationsList();
		if(medicineSet.isEmpty()) {
			throw new NoMedicinesAssignedException();
		}
		BillCart pbc = new BillCart();
		pbc.setPatientId(patientId);
		pbc.setMedicines(medicineSet);
		double bill=0;
		for(Medicine medicine : medicineSet) {
			bill = bill + medicine.getCost();
		}
		pbc.setBill(bill);
		return pbc;
	}
}
