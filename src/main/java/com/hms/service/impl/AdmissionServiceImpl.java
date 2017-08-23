package com.hms.service.impl;

import com.hms.domain.*;
import com.hms.repository.AdmissionRepository;
import com.hms.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@Service("admissionService")
public class AdmissionServiceImpl implements AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Override
    public List<Admission> findAll() {
        return admissionRepository.findAll();
    }

    @Override
    public List<Admission> findAllForPatient(Patient patient) {
        return admissionRepository.findByPatientId(patient.getId());
    }

    @Override
    public Admission findActiveForPatient(Patient patient) {
        return admissionRepository.findByPatientIdAndActiveTrue(patient.getId());
    }

    @Override
    public List<Admission> findAllForDoctor(Doctor doctor) {
        return admissionRepository.findByDoctorId(doctor.getId());
    }

    @Override
    public List<Admission> findActiveForDoctor(Doctor doctor) {
        return admissionRepository.findByDoctorIdAndActiveTrue(doctor.getId());
    }

    @Override
    public Admission addAdmissionForPatient(Admission admission, Patient patient, Doctor doctor, Department department, Bed bed) {
        admission.setPatient(patient);
        admission.setDoctor(doctor);
        patient.setDoctor(doctor);
        admission.setDepartment(department);
        admission.setBed(bed);
        bed.setAvailable(false);
        admission.setActive(true);
        admission.setAdmissionDate(new Date());
        patient.setAdmitted(true);
        return admissionRepository.save(admission);
    }

    @Override
    public Admission updateAdmission(Admission admission) {
        return admissionRepository.save(admission);
    }

    @Override
    public void deleteAdmission(Admission admission) {
        Patient patient = admission.getPatient();
        patient.getAdmissions().remove(admission);

        Doctor doctor = admission.getDoctor();
        doctor.getAdmissions().remove(admission);

        Department department = admission.getDepartment();
        department.getAdmissions().remove(admission);

        Bed bed = admission.getBed();
        bed.setAdmission(null);

        admission.setBed(null);
        admission.setDoctor(null);
        admission.setPatient(null);
        admission.setDepartment(null);

        admissionRepository.delete(admission);
    }

    @Override
    public Admission discharge(Admission admission) {
        admission.setDischargeDate(new Date());
        admission.setActive(false);
        admissionRepository.save(admission);
        return admission;
    }

    @Override
    public Admission findById(Long admissionId) {
        return admissionRepository.findOne(admissionId);
    }

    @Override
    public List<Admission> findActiveByDepartment(Long departmentId) {
        return admissionRepository.findByDepartmentIdAndActiveTrue(departmentId);
    }

    @Override
    public Admission findByBedNumber(String bedNo) {
        return admissionRepository.findByBedNumber(bedNo);
    }
}
