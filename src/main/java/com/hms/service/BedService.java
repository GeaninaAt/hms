package com.hms.service;

import com.hms.domain.Bed;
import com.hms.domain.Department;
import com.hms.domain.Room;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
public interface BedService {

    Bed addBed(Bed bed, Room room);

    Bed addBed(Bed bed);

    void deleteBed(Bed bed);

    Bed findById(Long id);

    List<Bed> findByAvailabilityAndDepartment(Department department);

    List<Bed> findAll();

    List<Bed> findByRoom(Room room);

}
