package com.hms.service.impl;

import com.hms.domain.Doctor;
import com.hms.domain.Examination;
import com.hms.domain.Patient;
import com.hms.repository.ExaminationRepository;
import com.hms.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@Service("examinationService")
public class ExaminationServiceImpl implements ExaminationService {

    @Autowired
    private ExaminationRepository examinationRepository;

    @Override
    public Examination addExamination(Examination examination, Doctor doctor, Patient patient) {
        examination.setDoctor(doctor);
        examination.setPatient(patient);
        examinationRepository.save(examination);
        return examination;
    }

    @Override
    public Examination addExamination(Examination examination) {
        return examinationRepository.save(examination);
    }

    @Override
    public void deleteExamination(Examination examination) {
        Doctor doctor = examination.getDoctor();
        doctor.getExaminations().remove(examination);
        Patient patient = examination.getPatient();
        patient.getExaminations().remove(examination);

        examination.setDoctor(null);
        examination.setPatient(null);
        examinationRepository.delete(examination);
    }

    @Override
    public List<Examination> getAllExaminations() {
        return examinationRepository.findAll();
    }

    @Override
    public List<Examination> getAllForPatient(Patient patient) {
        return examinationRepository.findByPatientId(patient.getId());
    }

    @Override
    public List<Examination> getAllForDoctor(Doctor doctor) {
        return examinationRepository.findByDoctorId(doctor.getId());
    }

    @Override
    public Examination getById(Long examinationId) {
        return examinationRepository.findOne(examinationId);
    }
}
