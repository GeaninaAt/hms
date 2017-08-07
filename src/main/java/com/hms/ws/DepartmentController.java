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

import java.util.List;

/**
 * Created by gatomulesei on 8/7/2017.
 */
@RestController
@RequestMapping(value = "/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorService doctorService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Department> addDepartment(@RequestBody Department department, UriComponentsBuilder ucBuilder) {
        if(departmentService.isExists(department)) {
            System.out.println("Department already exists!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        departmentService.addDepartment(department);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/departments/{id}").buildAndExpand(department.getId()).toUri());
        return new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Department>> findAllDepartments() {
        List<Department> departments = departmentService.findAll();

        if((departments == null) || (departments.isEmpty())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(departments, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Department> getDepartment(@PathVariable("id") Long departmentId) {
        Department department = departmentService.findById(departmentId);

        if(department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(department, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Department> updateDepartment(@PathVariable("id") Long departmentId, @RequestBody Department department) {
        Department currentDepartment = departmentService.findById(departmentId);

        if(currentDepartment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(department.getName() != null) {
            currentDepartment.setName(department.getName());
        }

        if(department.getDescription() != null) {
            currentDepartment.setDescription(department.getDescription());
        }

        departmentService.addDepartment(currentDepartment);
        return new ResponseEntity<>(currentDepartment, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Department> deleteDepartment(@PathVariable("id") Long departmentId) {
        Department department = departmentService.findById(departmentId);

        if(department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        departmentService.delete(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/getAllDoctors/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Doctor>> getAllDoctorsForDepartment(@PathVariable("id") Long departmentId) {
        Department department = departmentService.findById(departmentId);

        if(department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Doctor> doctors = doctorService.findByDepartment(departmentId);

        if((doctors == null) || (doctors.isEmpty())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(doctors, HttpStatus.OK);
        }
    }
}
