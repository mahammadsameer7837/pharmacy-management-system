package de.io.pharmacy_management_system.service;

import de.io.pharmacy_management_system.exceptions.DoctorNotFoundException;
import de.io.pharmacy_management_system.exceptions.MedicineNotFoundException;
import de.io.pharmacy_management_system.exceptions.NoMedicinesAssignedException;
import de.io.pharmacy_management_system.exceptions.PatientNotFoundException;
import de.io.pharmacy_management_system.exceptions.PatientsLimitReachedException;
import de.io.pharmacy_management_system.model.BillCart;
import de.io.pharmacy_management_system.model.Patient;

public interface PatientService {

	Patient addPatient(Patient newPatient)
			throws PatientsLimitReachedException, MedicineNotFoundException, DoctorNotFoundException;

	Patient updatePatient(int id, Patient modifiedPatient)
			throws PatientNotFoundException, DoctorNotFoundException, MedicineNotFoundException;

	Patient getPatient(int id) throws PatientNotFoundException;

	String deletePatient(int id) throws PatientNotFoundException;

	BillCart produceBill(int patientId) throws PatientNotFoundException, NoMedicinesAssignedException;

}
