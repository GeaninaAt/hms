package com.hms.repository;

import com.hms.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by gatomulesei on 8/7/2017.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query(value = "select distinct d from Doctor as d where d.firstName = :name or d.lastName = :name")
    Doctor findByName(@Param("name") String name);

    //TODO: see if List is needed

    Doctor findByCnp(String cnp);

    @Query(value = "select d from Doctor as d where d.degree like :degree")
    List<Doctor> findByDegree(@Param("degree") String degree);

    @Query(value = "select d from Doctor as d where d.position like :position")
    List<Doctor> findByPosition(@Param("position") String position);

    /*@Query(value = "select d from Doctor d as d left join d.department as x where x.id = :departmentId")
    List<Doctor> findByDepartment(@Param("departmentId") Long departmentId);*/
}
