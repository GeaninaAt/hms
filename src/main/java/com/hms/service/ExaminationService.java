package com.hms.service;

import com.hms.domain.Doctor;
import com.hms.domain.Examination;
import com.hms.domain.Patient;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
public interface ExaminationService {

    Examination addExamination(Examination examination, Doctor doctor, Patient patient);

    Examination addExamination(Examination examination);

    void deleteExamination(Examination examination);

    List<Examination> getAllExaminations();

    List<Examination> getAllForPatient(Patient patient);

    List<Examination> getAllForDoctor(Doctor doctor);

    Examination getById(Long examinationId);



}
