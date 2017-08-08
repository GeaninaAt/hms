package com.hms.repository;

import com.hms.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByCode(String code);

    List<Room> findByDepartmentId(Long departmentId);
}
