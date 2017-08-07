package com.hms.repository;

import com.hms.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by gatomulesei on 8/7/2017.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "select distinct d from Department as d where d.name = :name")
    Department findByName(@Param("name") String name);
}
