package com.hms.repository;

import com.hms.domain.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    List<Examination> findByPatientId(Long patientId);

    List<Examination> findByDoctorId(Long doctorId);
}
