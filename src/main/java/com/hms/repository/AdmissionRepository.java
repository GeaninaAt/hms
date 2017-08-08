package com.hms.repository;

import com.hms.domain.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    Admission findByPatientIdAndActiveTrue(Long patientId);
    List<Admission> findByDoctorIdAndActiveTrue(Long doctorId);
    List<Admission> findByDepartmentIdAndActiveTrue(Long departmentId);
    List<Admission> findByPatientId(Long patientId);
    List<Admission> findByDoctorId(Long doctorId);
}
