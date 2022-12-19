package de.io.pharmacy_management_system.service;

import java.text.ParseException;
import java.util.List;

import de.io.pharmacy_management_system.exceptions.MedicineAlreadyFoundException;
import de.io.pharmacy_management_system.exceptions.MedicineNameTakenException;
import de.io.pharmacy_management_system.exceptions.MedicineNotFoundException;
import de.io.pharmacy_management_system.model.Medicine;

public interface MedicineService {

	Medicine addMedicine(Medicine newMedicine) throws MedicineAlreadyFoundException, ParseException;

	Medicine updateMedicine(int medicineId, Medicine modifiedMedicine) throws MedicineNotFoundException, ParseException, MedicineNameTakenException;

	List<Medicine> getAllMedicines();

	List<Medicine> getAllByName(String medicinename);

	String deleteMedicineById(int medicineId) throws MedicineNotFoundException;

	Medicine getMedicineById(int medicineId) throws MedicineNotFoundException;

}
