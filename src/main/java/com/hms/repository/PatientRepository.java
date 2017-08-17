package com.hms.repository;

import com.hms.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by gatomulesei on 8/7/2017.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(value = "select distinct p from Patient as p where p.firstName = :name or p.lastName = :name")
    Patient findByName(@Param("name")String name);

    Patient findByCnp(String cnp);

}
