package com.hms.ws;

import com.hms.domain.Admission;
import com.hms.domain.Department;
import com.hms.domain.Doctor;
import com.hms.domain.Patient;
import com.hms.service.AdmissionService;
import com.hms.service.DepartmentService;
import com.hms.service.DoctorService;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gatomulesei on 8/7/2017.
 */
@RestController
@RequestMapping(value = "/patients")
@CrossOrigin(origins = "http://localhost:8000")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AdmissionService admissionService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient, UriComponentsBuilder ucBuilder) {
        if(patientService.isExists(patient)) {
            System.out.println("Patient already exists!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        patientService.addPatient(patient);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri());
        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Patient>> findAllPatients() {
        List<Patient> patients = patientService.findAll();

        if((patients == null) || (patients.isEmpty())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(patients, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Patient> getPatient(@PathVariable("id") Long patientId) {
        Patient patient = patientService.findById(patientId);

        if(patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(patient, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Patient> updatePatient(@PathVariable("id") Long patientId, @RequestBody Patient patient) {
        Patient currentPatient = patientService.findById(patientId);

        if(currentPatient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(patient.getFirstName() != null) {
            currentPatient.setFirstName(patient.getFirstName());
        }

        if(patient.getLastName() != null) {
            currentPatient.setLastName(patient.getLastName());
        }

        if(patient.getCnp() != null) {
            currentPatient.setCnp(patient.getCnp());
        }

        if(patient.getDateOfBirth() != null) {
            currentPatient.setDateOfBirth(patient.getDateOfBirth());
        }

        if(patient.getEmail() != null) {
            currentPatient.setEmail(patient.getEmail());
        }

        if(patient.getPhoneNumber() != null) {
            currentPatient.setPhoneNumber(patient.getPhoneNumber());
        }

        if(patient.getBloodType() != null) {
            currentPatient.setBloodType(patient.getBloodType());
        }

        if(patient.getOtherInfo() != null) {
            currentPatient.setOtherInfo(patient.getOtherInfo());
        }

        patientService.addPatient(currentPatient);
        return new ResponseEntity<>(currentPatient, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Patient> deletePatient(@PathVariable("id") Long patientId) {
        Patient patient = patientService.findById(patientId);

        if(patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        patientService.delete(patientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/addDoctor/{patientId}/{doctorId}", method = RequestMethod.POST)
    public ResponseEntity<Patient> assignDoctor(@PathVariable("patientId") Long patientId, @PathVariable("doctorId") Long doctorId) {
        Patient patient = patientService.findById(patientId);
        Doctor doctor = doctorService.findById(doctorId);

        if(patient == null) {
            System.out.println("Patient not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(doctor == null) {
            System.out.println("Doctor not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(patient.getDoctor() != null) {
            System.out.println("Patient already has a doctor assigned!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        patientService.assignDoctor(patient, doctor);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @RequestMapping(value = "/removeDoctor/{patientId}/{doctorId}", method = RequestMethod.POST)
    public ResponseEntity<Patient> unassignDoctor(@PathVariable("patientId") Long patientId, @PathVariable("doctorId") Long doctorId) {
        Patient patient = patientService.findById(patientId);
        Doctor doctor = doctorService.findById(doctorId);

        if(patient == null) {
            System.out.println("Patient not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(doctor == null) {
            System.out.println("Doctor not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(patient.getDoctor() == null) {
            System.out.println("Patient does not have a doctor assigned!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        patientService.unassignDoctor(patient, doctor);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllForDoctor/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Patient>> getAllForDoctor(@PathVariable("id") Long doctorId) {
        Doctor doctor = doctorService.findById(doctorId);

        if(doctor == null) {
            System.out.println("Doctor not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if((doctor.getPatients() == null) || (doctor.getPatients().isEmpty())) {
            System.out.println("No patients found for this doctor!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Patient> patients = patientService.getAllForDoctor(doctor);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @RequestMapping(value = "/getByDepartment/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Patient>> getByDepartment(@PathVariable("name") String departmentName) {
        Department department = departmentService.findByName(departmentName);

        if(department == null) {
            System.out.println("department not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Admission> admissions = admissionService.findActiveByDepartment(department.getId());
        List<Patient> patients = new ArrayList<>();
        for(Admission a : admissions) {
            patients.add(a.getPatient());
        }

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @RequestMapping(value = "/getUnadmitted", method = RequestMethod.GET)
    public ResponseEntity<List<Patient>> getUnadmitted() {
        List<Patient> patients = patientService.findUnadmitted();

        if((patients == null) || (patients.isEmpty())) {
            System.out.println("no unadmitted patients found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
}
