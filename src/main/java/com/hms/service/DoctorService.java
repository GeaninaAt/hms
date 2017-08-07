package com.hms.service;

import com.hms.domain.Department;
import com.hms.domain.Doctor;

import javax.print.Doc;
import java.util.List;

/**
 * Created by gatomulesei on 8/7/2017.
 */
public interface DoctorService {

    List<Doctor> findAll();

    Doctor findById(Long id);

    Doctor findByName(String name);

    Doctor findByCnp(String cnp);

    Doctor addDoctor(Doctor doctor);

    void delete(Long id);

    boolean isExists(Doctor patient);

    List<Doctor> findByDegree(String degree);

    List<Doctor> findByPosition(String position);

    /*List<Doctor> findByDepartment(Long departmentId);*/

    Doctor addToDepartment(Doctor doctor, Department department);

    Doctor removeFromDepartment(Doctor doctor, Department department);
}
