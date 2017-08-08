package com.hms.service.impl;

import com.hms.domain.Bed;
import com.hms.domain.Doctor;
import com.hms.domain.Patient;
import com.hms.domain.Room;
import com.hms.repository.DoctorRepository;
import com.hms.repository.PatientRepository;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gatomulesei on 8/7/2017.
 */
@Service("patientService")
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(Long id) {
        return patientRepository.findOne(id);
    }

    @Override
    public Patient findByName(String name) {
        return patientRepository.findByName(name);
    }

    @Override
    public Patient findByCnp(String cnp) {
        return patientRepository.findByCnp(cnp);
    }

    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void delete(Long id) {
        patientRepository.delete(id);
    }

    @Override
    public boolean isExists(Patient patient) {
        return (patientRepository.findByCnp(patient.getCnp()) != null);
    }

    @Override
    public Patient assignDoctor(Patient patient, Doctor doctor) {
        patient.setDoctor(doctor);
        doctor.getPatients().add(patient);
        patientRepository.save(patient);
        doctorRepository.save(doctor);
        return patient;
    }

    @Override
    public Patient unassignDoctor(Patient patient, Doctor doctor) {
        patient.setDoctor(null);
        doctor.getPatients().remove(patient);
        patientRepository.save(patient);
        doctorRepository.save(doctor);
        return patient;
    }

    @Override
    public List<Patient> getAllForDoctor(Doctor doctor) {
        return doctor.getPatients();
    }

}
