package com.hms.service;

import com.hms.domain.*;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
public interface AdmissionService {

    List<Admission> findAll();
    List<Admission> findAllForPatient(Patient patient);
    Admission findActiveForPatient(Patient patient);

    List<Admission> findAllForDoctor(Doctor doctor);
    List<Admission> findActiveForDoctor(Doctor doctor);

    Admission addAdmissionForPatient(Admission admission,
                                     Patient patient,
                                     Doctor doctor,
                                     Department department,
                                     Bed bed);

    Admission updateAdmission(Admission admission);

    void deleteAdmission(Admission admission);

    Admission discharge(Admission admission);

    Admission findById(Long admissionId);
}
