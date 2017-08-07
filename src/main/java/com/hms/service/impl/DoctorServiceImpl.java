package com.hms.service.impl;

import com.hms.domain.Doctor;
import com.hms.repository.DoctorRepository;
import com.hms.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gatomulesei on 8/7/2017.
 */
@Service("doctorService")
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor findById(Long id) {
        return doctorRepository.findOne(id);
    }

    @Override
    public Doctor findByName(String name) {
        return doctorRepository.findByName(name);
    }

    @Override
    public Doctor findByCnp(String cnp) {
        return doctorRepository.findByCnp(cnp);
    }

    @Override
    public Doctor addDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public void delete(Long id) {
        doctorRepository.delete(id);
    }

    @Override
    public boolean isExists(Doctor patient) {
        return (doctorRepository.findByCnp(patient.getCnp()) != null);
    }

    @Override
    public List<Doctor> findByDegree(String degree) {
        return doctorRepository.findByDegree(degree);
    }

    @Override
    public List<Doctor> findByPosition(String position) {
        return doctorRepository.findByPosition(position);
    }

    @Override
    public List<Doctor> findByDepartment(Long departmentId) {
        return doctorRepository.findByDepartment(departmentId);
    }

}
