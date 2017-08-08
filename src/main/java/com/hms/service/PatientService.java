package com.hms.service;

import com.hms.domain.Doctor;
import com.hms.domain.Patient;

import java.util.List;

/**
 * Created by gatomulesei on 8/7/2017.
 */
public interface PatientService {

    List<Patient> findAll();

    Patient findById(Long id);

    Patient findByName(String name);

    Patient findByCnp(String cnp);

    Patient addPatient(Patient patient);

    void delete(Long id);

    boolean isExists(Patient patient);

    Patient assignDoctor(Patient patient, Doctor doctor);

    Patient unassignDoctor(Patient patient, Doctor doctor);

    List<Patient> getAllForDoctor(Doctor doctor);

}
