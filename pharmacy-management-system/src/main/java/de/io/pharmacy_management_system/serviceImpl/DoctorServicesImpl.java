package de.io.pharmacy_management_system.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.io.pharmacy_management_system.exceptions.DoctorAlreadyExistWithIdTException;
import de.io.pharmacy_management_system.exceptions.DoctorIdMissingException;
import de.io.pharmacy_management_system.exceptions.DoctorNotFoundException;
import de.io.pharmacy_management_system.model.Doctor;
import de.io.pharmacy_management_system.model.Patient;
import de.io.pharmacy_management_system.repository.DoctorRepository;
import de.io.pharmacy_management_system.repository.PatientRepository;
import de.io.pharmacy_management_system.service.DoctorService;

@Service
public class DoctorServicesImpl implements DoctorService {
	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	PatientRepository patientRepository;
	//CODE TO ADD A DOCTOR TO PATIENT
	@Override
	public void addPatienttoDoctor(Patient patient) throws DoctorNotFoundException {
		 
		Doctor nDoctor;
		Optional<Doctor> doctor = doctorRepository.findById(patient.getDoctorId());
		 
		if(doctor.isEmpty()) {
			throw new DoctorNotFoundException();
		}
		else {
			nDoctor = doctor.get();
		}
		 
		List<Doctor> doctorsList = doctorRepository.findAll();
		for(Doctor mDoctor: doctorsList) {
			boolean tf=false;
		if(!mDoctor.getPatientsAssigned().isEmpty()) {
			 
		for(Patient assignedPatient: mDoctor.getPatientsAssigned()) {
			 
			if(assignedPatient.getPatientId()==patient.getPatientId()) {
				 
				mDoctor.getPatientsAssigned().remove(assignedPatient);
				
				System.out.println("REMOVED ALREADY PRESENT PATIENT");
				
				doctorRepository.save(mDoctor);
				tf=true;
				break;
			}
		}
		if(tf) {
			break;
		}
		}
		}
		
		 
		patientRepository.save(patient);
		nDoctor.getPatientsAssigned().add(patient);
		 
		doctorRepository.save(nDoctor);
		 
	}

	//CODE TO READ A DOCTOR DETAILS BY TAKING ID REQUIRMENT-5
	@Override
	public Doctor findDoctorById(int id) throws DoctorNotFoundException {
		 
		Doctor d;
		try {
		d = doctorRepository.findById(id).get();
		}
		catch(Exception ex) {
			throw new DoctorNotFoundException(); 
		}
		 
		return d;

	}

	//CODE TO READ A DOCTOR DETAILS BY TAKING iDT REQUIRMENT-5
	@Override
	public Doctor findDoctorById(String doctorIdT) throws DoctorNotFoundException {
		// System.out.println("Here-11");
		Doctor d = doctorRepository.findByDoctorIdT(doctorIdT);
		if (d == null) {
			throw new DoctorNotFoundException();
		}
		// System.out.println("here-12");
		return d;

	}

	//CODE TO ADD A NEW DOCTOR REQUIREMENT --2
	@Override
	public Doctor addDoctor(Doctor doctor) throws DoctorAlreadyExistWithIdTException {

		if (doctorRepository.findByDoctorIdT(doctor.getDoctorIdT()) == null) {

			return doctorRepository.save(doctor);

		}
		throw new DoctorAlreadyExistWithIdTException();

	}

	// CODE TO UPDATE DOCTOR DETAILS BY TAKING ID REQUIRMENT-5
	@Override
	public Doctor updateDoctor(int docotorId, Doctor doctor)
			throws DoctorNotFoundException, DoctorAlreadyExistWithIdTException {

		Doctor oldDoctor = findDoctorById(docotorId);

		return doctorUpdater(doctor, oldDoctor);

	}
//CODE TO UPDATE DOCTOR BY TAKING IDT REQUIRMENT-5
	@Override
	public Doctor updateDoctor(String docotorIdT, Doctor doctor)
			throws DoctorNotFoundException, DoctorAlreadyExistWithIdTException {
		Doctor oldDoctor = findDoctorById(docotorIdT);
		return doctorUpdater(doctor, oldDoctor);

	}
//CODE TO UPDATE DOCTOR BY JUST TAKING DOCTOR REQUIRMENT-5
	@Override
	public Doctor updateDoctor(Doctor doctor)
			throws DoctorNotFoundException, DoctorIdMissingException, DoctorAlreadyExistWithIdTException {
		boolean go = true;
		if (doctor.getDoctorIdT() == null) {
			throw new DoctorIdMissingException();
		} else if (go) {
			return doctorUpdater(doctor, findDoctorById(doctor.getDoctorIdT()));
		} else if (doctor.getDoctorId() == 0) {
			throw new DoctorIdMissingException();
		} else {
			return doctorUpdater(doctor, findDoctorById(doctor.getDoctorId()));
		}

	}
//CODE WHICH PERFORMS THE DOCTOR UPDATION DETAILS 
	private Doctor doctorUpdater(Doctor doctor, Doctor oldDoctor) throws DoctorAlreadyExistWithIdTException {

//		if (doctor.getDoctorIdT() != null) {
//			if (doctorRepository.findByDoctorIdT(doctor.getDoctorIdT()) == null) {
//				oldDoctor.setDoctorIdT(doctor.getDoctorIdT());
//			} else {
//				throw new DoctorAlreadyExistWithIdTException();
//			}
//		}
		if (doctor.getAddress() != null) {
			oldDoctor.setAddress(doctor.getAddress());
		}
		if (doctor.getDesignation() != null) {
			oldDoctor.setDesignation(doctor.getDesignation());
		}
		if (doctor.getDoctorName() != null) {
			oldDoctor.setDoctorName(doctor.getDoctorName());
		}
		if (doctor.getSpecialization() != null) {
			oldDoctor.setSpecialization(doctor.getSpecialization());
		}

		return doctorRepository.save(oldDoctor);
	}
//CODE TO DELETE A DOCTOR BY TAKING IDT  REQUIRMENT-5
	@Override
	public String deleteDoctor(int doctorId) throws DoctorNotFoundException {
		findDoctorById(doctorId);
		doctorRepository.deleteById(doctorId);
		return "Successfully deleted";

	}
//CODE TO DELETE A DOCTOR BY TAKING IDT----REQUIRMENT-5
	@Override
	public String deleteDoctor(String doctorIdT) throws DoctorNotFoundException {
		doctorRepository.deleteById(findDoctorById(doctorIdT).getDoctorId() );
		//System.out.println("here-4");
		return "Successfully deleted";

	}
//CODE TO ADD A LIST OF DOCTORS
	@Override
	public List<Doctor> addDoctorList(List<Doctor> doctorsList) throws DoctorAlreadyExistWithIdTException {

		for (Doctor doctor : doctorsList) {
			addDoctor(doctor);
		}

		return doctorsList;
	}
//CODE TO REMOVE PATIENT FROM DOCTOR
	public void removePatientFromDoctor(Patient patient){
		Doctor doctor;
		try {
		 doctor = doctorRepository.findById(patient.getDoctorId()).get();
		}
		catch(Exception ex) {
			return;
		}
		doctor.getPatientsAssigned().remove(patient);
		doctorRepository.save(doctor);
		
	}

}
