package com.hms.repository;

import com.hms.domain.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {

    @Query(value = "select b from Bed as b\n" +
                    "left join b.room as r\n" +
                    "left join r.department as d\n" +
                    "where d.id = :departmentId and b.available = true")
    List<Bed> findByAvailabilityAndDepartment(@Param("departmentId") Long departmentId);

    List<Bed> findByRoomId(Long roomId);

    Bed findByNumber(String bedNumber);
}
