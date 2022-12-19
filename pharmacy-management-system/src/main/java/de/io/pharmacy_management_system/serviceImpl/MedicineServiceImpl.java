package de.io.pharmacy_management_system.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.io.pharmacy_management_system.exceptions.MedicineAlreadyFoundException;
import de.io.pharmacy_management_system.exceptions.MedicineNameTakenException;
import de.io.pharmacy_management_system.exceptions.MedicineNotFoundException;
import de.io.pharmacy_management_system.model.Disease;
import de.io.pharmacy_management_system.model.Medicine;
import de.io.pharmacy_management_system.model.Patient;
import de.io.pharmacy_management_system.repository.DiseaseRepository;
import de.io.pharmacy_management_system.repository.MedicineRepository;
import de.io.pharmacy_management_system.repository.PatientRepository;
import de.io.pharmacy_management_system.service.MedicineService;

@Service
public class MedicineServiceImpl implements MedicineService {
	
	@Autowired
	MedicineRepository medicineRepository;
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	DiseaseRepository diseaseRepository;
	
	//CODE TO ADD A NEW MEDICINE
	@Override
	public Medicine addMedicine(Medicine newMedicine) throws MedicineAlreadyFoundException, ParseException {
		
		List<Medicine> OldMedicines=medicineRepository.findAllByMedicineName(newMedicine.getMedicineName());
		
		for(Medicine foundMedicine: OldMedicines ) {
		//if(foundMedicine != null) {
			
		if(foundMedicine.getMedicineName().equalsIgnoreCase(newMedicine.getMedicineName())) {
				if(foundMedicine.getManufacturedCompanyName().equalsIgnoreCase(newMedicine.getManufacturedCompanyName())) {
					 
					if(foundMedicine.getCost().equals(newMedicine.getCost())) {
						 
						throw new MedicineAlreadyFoundException("medicine already found");
						
					}
				}
			}
		}
		//}
		
	
		return medicineRepository.save(newMedicine);
	}
	
	//CODE TO UPDATE THE EXISTING MEDICINE
	@Override
	public Medicine updateMedicine(int medicineId,Medicine modifiedMedicine) throws MedicineNotFoundException, ParseException, MedicineNameTakenException {
		Medicine oldMedicine;
		try {
		 oldMedicine = medicineRepository.findById(medicineId).get();
		}
		 catch(Exception ex) {
			 throw new MedicineNotFoundException();
		 }
		 
		 if(modifiedMedicine.getMedicineName() != null) { 
			 if(medicineRepository.existsByMedicineName(modifiedMedicine.getMedicineName())) {
					throw new MedicineNameTakenException();
				}
			 oldMedicine.setMedicineName(modifiedMedicine.getMedicineName());
			 
			 }
		 if(modifiedMedicine.getExpiryDate() != null) { 
			 
			 oldMedicine.setExpiryDate(modifiedMedicine.getExpiryDate());
			 
		 }
		 if(modifiedMedicine.getCost() != null)
		 {
			 oldMedicine.setCost(modifiedMedicine.getCost());
			 
			 }
		 if(modifiedMedicine.getManufacturedCompanyName() != null) {
			 
			 oldMedicine.setManufacturedCompanyName(modifiedMedicine.getManufacturedCompanyName());
		 }
		 if(modifiedMedicine.getYearOfManufacture() != null){
			 
			 oldMedicine.setYearOfManufacture(modifiedMedicine.getYearOfManufacture());
		 }
		return medicineRepository.save(oldMedicine);
	}
	

	//CODE TO GET ALL MEDICINES AS LIST
	@Override
	public List<Medicine> getAllMedicines(){
		return medicineRepository.findAll();
	}
	//CODE TO GET ALL MEDICINES HAVING SAME NAME
	@Override
	public List<Medicine> getAllByName(String medicinename) {
		 
		return medicineRepository.findAllByMedicineName(medicinename);
	}
	//CODE TO DELETE MEDICINE BY ID
	@Override
	public String deleteMedicineById(int medicineId) throws MedicineNotFoundException {
		Medicine medicine; 
		try{
			medicine =medicineRepository.findById(medicineId).get();
		}
		catch(Exception ex) {
			 throw new MedicineNotFoundException();
		}
		
		for(Patient patient:patientRepository.findAll()) {
			 if(patient.getMedicationsList().remove(medicine)) {
				 patient.setMedicationCount(patient.getMedicationCount()-1);
				 patientRepository.save(patient);
			 } 
		}
		
		for(Disease disease:diseaseRepository.findAll()) {
			 if(disease.getMedicationSuggested().remove(medicine)) {
				 diseaseRepository.save(disease);
			 } 
		}
		
		medicineRepository.deleteById(medicineId);
		return "Medicine with Id "+medicineId+" Deleted Successfully";
	}
	//Code to get medicine by Id
	@Override
	public Medicine getMedicineById(int medicineId) throws MedicineNotFoundException {
		
		try {
			return medicineRepository.findById(medicineId).get();
		}
		catch(Exception ex) {
			throw new MedicineNotFoundException();
		}
		
	}
	
}
