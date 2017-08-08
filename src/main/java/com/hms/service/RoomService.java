package com.hms.service;

import com.hms.domain.Department;
import com.hms.domain.Room;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
public interface RoomService {

    Room addRoom(Room room, Department department);

    Room addRoom(Room room);

    void deleteRoom(Room room);

    Room findById(Long roomId);

    Room findByCode(String code);

    List<Room> findByDepartment(Department department);

    List<Room> findAll();
}
