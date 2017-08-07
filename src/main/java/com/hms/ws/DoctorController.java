package com.hms.ws;

import com.hms.domain.Department;
import com.hms.domain.Doctor;
import com.hms.service.DepartmentService;
import com.hms.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.Doc;
import java.util.List;

/**
 * Created by gatomulesei on 8/7/2017.
 */
@RestController
@RequestMapping(value = "/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor, UriComponentsBuilder ucBuilder) {
        if(doctorService.isExists(doctor)) {
            System.out.println("Patient already exists!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        doctorService.addDoctor(doctor);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri());
        return new ResponseEntity<>(doctor, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Doctor>> findAllDoctors() {
        List<Doctor> doctors = doctorService.findAll();

        if((doctors == null) || (doctors.isEmpty())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Doctor> getDoctor(@PathVariable("id") Long doctorId) {
        Doctor doctor = doctorService.findById(doctorId);

        if(doctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Doctor> updateDoctor(@PathVariable("id") Long doctorId, @RequestBody Doctor doctor) {
        Doctor currentDoctor = doctorService.findById(doctorId);

        if(currentDoctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(doctor.getFirstName() != null) {
            currentDoctor.setFirstName(doctor.getFirstName());
        }

        if(doctor.getLastName() != null) {
            currentDoctor.setLastName(doctor.getLastName());
        }

        if(doctor.getCnp() != null) {
            currentDoctor.setCnp(doctor.getCnp());
        }

        if(doctor.getBirthDate() != null) {
            currentDoctor.setBirthDate(doctor.getBirthDate());
        }

        if(doctor.getEmail() != null) {
            currentDoctor.setEmail(doctor.getEmail());
        }

        if(doctor.getPhoneNumber() != null) {
            currentDoctor.setPhoneNumber(doctor.getPhoneNumber());
        }

        if(doctor.getDegree() != null) {
            currentDoctor.setDegree(doctor.getDegree());
        }

        if(doctor.getPosition() != null) {
            currentDoctor.setPosition(doctor.getPosition());
        }

        doctorService.addDoctor(currentDoctor);
        return new ResponseEntity<>(currentDoctor, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Doctor> deleteDoctor(@PathVariable("id") Long doctorId) {
        Doctor doctor = doctorService.findById(doctorId);

        if(doctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        doctorService.delete(doctorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/getByDegree/{degree}", method = RequestMethod.GET)
    public ResponseEntity<List<Doctor>> getByDegree(@PathVariable("degree") String degree) {
        List<Doctor> doctors = doctorService.findByDegree(degree);

        if((doctors == null) || (doctors.isEmpty())) {
            System.out.println("No doctors with this degree were found!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @RequestMapping(value = "/getByPosition/{pos}", method = RequestMethod.GET)
    public ResponseEntity<List<Doctor>> getByPosition(@PathVariable("pos") String position) {
        List<Doctor> doctors = doctorService.findByPosition(position);

        if((doctors == null) || (doctors.isEmpty())) {
            System.out.println("No doctors with this position were found!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @RequestMapping(value = "/addToDepartment/{doctorId}/{departmentId}", method = RequestMethod.POST)
    public ResponseEntity<Doctor> addToDepartment(@PathVariable("doctorId") Long doctorId, @PathVariable("departmentId") Long departmentId) {
        Doctor doctor = doctorService.findById(doctorId);
        Department department = departmentService.findById(departmentId);

        if(doctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        doctorService.addToDepartment(doctor, department);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @RequestMapping(value = "/removeFromDepartment/{doctorId}/{departmentId}", method = RequestMethod.POST)
    public ResponseEntity<Doctor> removeFromDepartment(@PathVariable("doctorId") Long doctorId, @PathVariable("departmentId") Long departmentId) {
        Doctor doctor = doctorService.findById(doctorId);
        Department department = departmentService.findById(departmentId);

        if(doctor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        doctorService.removeFromDepartment(doctor, department);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }
}
