package com.hms.ws;

import com.hms.domain.*;
import com.hms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@RestController
@RequestMapping(value = "/admissions")
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


    @RequestMapping(value = "/add/{patientId}/{doctorId}/{departmentId}/{bedId}")
    public ResponseEntity<Admission> addAdmissionForPatient(@PathVariable("patientId") Long patientId,
                                                            @PathVariable("doctorId") Long doctorId,
                                                            @PathVariable("departmentId") Long departmentId,
                                                            @PathVariable("bedId") Long bedId,
                                                            @RequestBody Admission admission) {

        Patient patient = patientService.findById(patientId);
        Doctor doctor = doctorService.findById(doctorId);
        Department department = departmentService.findById(departmentId);
        Bed bed = bedService.findById(bedId);
    }
}
