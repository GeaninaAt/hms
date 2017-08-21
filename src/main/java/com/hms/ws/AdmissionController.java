package com.hms.ws;

import com.hms.domain.*;
import com.hms.service.*;
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
@RequestMapping(value = "/admissions")
@CrossOrigin(origins = "http://localhost:8000")
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private BedService bedService;


    @RequestMapping(value = "/add/{patientId}/{doctorId}/{departmentName}/{bedId}", method = RequestMethod.POST)
    public ResponseEntity<Admission> addAdmissionForPatient(@PathVariable("patientId") Long patientId,
                                                            @PathVariable("doctorId") Long doctorId,
                                                            @PathVariable("departmentName") String departmentName,
                                                            @PathVariable("bedId") Long bedId,
                                                            @RequestBody Admission admission,
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

        Department department = departmentService.findByName(departmentName);
        if(department == null) {
            System.out.println("Department not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Bed bed = bedService.findById(bedId);
        if(bed == null) {
            System.out.println("Bed not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        admissionService.addAdmissionForPatient(admission, patient, doctor, department, bed);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/admissions/{id}").buildAndExpand(admission.getId()).toUri());
        return new ResponseEntity<>(admission, HttpStatus.OK);
    }

    @RequestMapping(value = "/findAllForPatient/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Admission>> findAllForPatient(@PathVariable("id") Long patientId) {
        Patient patient = patientService.findById(patientId);

        if(patient == null) {
            System.out.println("Patient not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Admission> admissions = admissionService.findAllForPatient(patient);
        return new ResponseEntity<>(admissions, HttpStatus.OK);
    }

    @RequestMapping(value = "/findActiveForPatient/{id}", method = RequestMethod.GET)
    public ResponseEntity<Admission> findActiveForPatient(@PathVariable("id") Long patientId) {
        Patient patient = patientService.findById(patientId);

        if(patient == null) {
            System.out.println("Patient not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Admission admission = admissionService.findActiveForPatient(patient);
        return new ResponseEntity<>(admission, HttpStatus.OK);
    }

    @RequestMapping(value = "/findAllForDoctor/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Admission>> findAllForDoctor(@PathVariable("id") Long doctorId) {
        Doctor doctor = doctorService.findById(doctorId);

        if(doctor == null) {
            System.out.println("Doctor not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Admission> admissions = admissionService.findAllForDoctor(doctor);
        return new ResponseEntity<>(admissions, HttpStatus.OK);
    }

    @RequestMapping(value = "/findActiveForDoctor/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Admission>> findActiveForDoctor(@PathVariable("id") Long doctorId) {
        Doctor doctor = doctorService.findById(doctorId);

        if(doctor == null) {
            System.out.println("Doctor not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Admission> admissions = admissionService.findActiveForDoctor(doctor);
        return new ResponseEntity<>(admissions, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Admission> updateAdmission(@PathVariable("id") Long admissionId,
                                                         @RequestBody Admission admission) {

        Admission currentAdmission = admissionService.findById(admissionId);

        if(currentAdmission == null) {
            System.out.println("Admission not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(admission.getAdmissionDate() != null) {
            currentAdmission.setAdmissionDate(admission.getAdmissionDate());
        }
        if(admission.getDischargeDate() != null) {
            currentAdmission.setDischargeDate(admission.getDischargeDate());
        }
        if(admission.getDiagnosis() != null) {
            currentAdmission.setDiagnosis(admission.getDiagnosis());
        }
        if(admission.getTreatmentInfo() != null) {
            currentAdmission.setTreatmentInfo(admission.getTreatmentInfo());
        }

        admissionService.updateAdmission(currentAdmission);
        return new ResponseEntity<>(currentAdmission, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Admission> getAdmission(@PathVariable("id") Long admissionId) {
        Admission admission = admissionService.findById(admissionId);
        if(admission == null) {
            System.out.println("Admission not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(admission, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Admission> deleteAdmission(@PathVariable("id") Long admissionId) {
        Admission admission = admissionService.findById(admissionId);
        if(admission == null) {
            System.out.println("Admission not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        admissionService.deleteAdmission(admission);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/discharge/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Admission> discharge(@PathVariable("id") Long admissionId) {
        Admission admission = admissionService.findById(admissionId);
        if(admission == null) {
            System.out.println("Admission not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!admission.isActive()) {
            System.out.println("Patient has already been discharged!");
        }

        admissionService.discharge(admission);
        return new ResponseEntity<>(admission, HttpStatus.OK);
    }
}
