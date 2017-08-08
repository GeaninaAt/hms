package com.hms.ws;

import com.hms.domain.Doctor;
import com.hms.domain.Examination;
import com.hms.domain.Patient;
import com.hms.service.DoctorService;
import com.hms.service.ExaminationService;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@RestController
@RequestMapping(value = "/examinations")
public class ExaminationController {

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/add/{pId}/{dId}", method = RequestMethod.POST)
    public ResponseEntity<Examination> addExamination(@PathVariable("pId") Long patientId,
                                                      @PathVariable("dId") Long doctorId,
                                                      @RequestBody Examination examination,
                                                      UriComponentsBuilder ucBuilder) {

        Patient patient = patientService.findById(patientId);
        if(patient == null) {
            System.out.println("Patient not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Doctor doctor = doctorService.findById(doctorId);
        if(doctor == null) {
            System.out.println("Doctor not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        examinationService.addExamination(examination, doctor, patient);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/examinations/{id}").buildAndExpand(examination.getId()).toUri());
        return new ResponseEntity<>(examination, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Examination> updateExamination(@PathVariable("id") Long examinationId,
                                                         @RequestBody Examination examination) {

        Examination currentExamination = examinationService.getById(examinationId);

        if(currentExamination == null) {
            System.out.println("Examination not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(examination.getDate() != null) {
            currentExamination.setDate(examination.getDate());
        }
        if(examination.getTime() != null) {
            currentExamination.setTime(examination.getTime());
        }
        if(examination.getConclusion() != null) {
            currentExamination.setConclusion(examination.getConclusion());
        }

        examinationService.addExamination(currentExamination);
        return new ResponseEntity<>(currentExamination, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Examination> deleteExamination(@PathVariable("id") Long examinationId) {
        Examination examination = examinationService.getById(examinationId);
        if(examination == null) {
            System.out.println("Examination not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        examinationService.deleteExamination(examination);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Examination>> getAll() {
        List<Examination> examinations = examinationService.getAllExaminations();
        if((examinations == null) || (examinations.isEmpty())) {
            System.out.println("No examinations registered!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(examinations, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Examination> getExamination(@PathVariable("id") Long examinationId) {
        Examination examination = examinationService.getById(examinationId);
        if(examination == null) {
            System.out.println("Examination not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(examination, HttpStatus.OK);
    }

    @RequestMapping(value = "/getForPatient/{patientId}", method = RequestMethod.GET)
    public ResponseEntity<List<Examination>> getAllForPatient(@PathVariable("patientId") Long patientId) {
        Patient patient = patientService.findById(patientId);
        if(patient == null) {
            System.out.println("Patient not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Examination> examinations = patient.getExaminations();
        if((examinations == null) || (examinations.isEmpty())) {
            System.out.println("No examinations registered for this patient!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        examinationService.getAllForPatient(patient);
        return new ResponseEntity<>(examinations, HttpStatus.OK);
    }

    @RequestMapping(value = "/getForDoctor/{doctorId}", method = RequestMethod.GET)
    public ResponseEntity<List<Examination>> getAllForDoctor(@PathVariable("doctorId") Long doctorId) {
        Doctor doctor = doctorService.findById(doctorId);
        if(doctor == null) {
            System.out.println("Doctor not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Examination> examinations = doctor.getExaminations();
        if((examinations == null) || (examinations.isEmpty())) {
            System.out.println("No examinations registered for this doctor!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        examinationService.getAllForDoctor(doctor);
        return new ResponseEntity<>(examinations, HttpStatus.OK);
    }
}
