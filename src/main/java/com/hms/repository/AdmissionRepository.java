package com.hms.repository;

import com.hms.domain.Admission;
import com.hms.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "select distinct a from Admission as a\n" +
            "left join a.bed as b\n" +
            "where b.number = :bedNo")
    Admission findByBedNumber(@Param("bedNo") String bedNo);
}
