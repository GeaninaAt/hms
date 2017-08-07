package com.hms.service;

import com.hms.domain.Department;
import com.hms.domain.Doctor;

import java.util.List;

/**
 * Created by gatomulesei on 8/7/2017.
 */
public interface DepartmentService {

    List<Department> findAll();

    Department findById(Long id);

    Department findByName(String name);

    Department addDepartment(Department department);

    void delete(Long id);

    boolean isExists(Department department);

}
