package com.hms.service.impl;

import com.hms.domain.Bed;
import com.hms.domain.Department;
import com.hms.domain.Room;
import com.hms.repository.BedRepository;
import com.hms.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@Service("bedService")
public class BedServiceImpl implements BedService {

    @Autowired
    private BedRepository bedRepository;

    @Override
    public Bed addBed(Bed bed, Room room) {
        room.getBeds().add(bed);
        bed.setRoom(room);
        return bedRepository.save(bed);
    }

    @Override
    public Bed addBed(Bed bed) {
        return bedRepository.save(bed);
    }

    @Override
    public void deleteBed(Bed bed) {
        Room room = bed.getRoom();
        room.getBeds().remove(bed);
        bed.setRoom(null);
        bedRepository.delete(bed);
    }

    @Override
    public Bed findById(Long id) {
        return bedRepository.findOne(id);
    }

    @Override
    public List<Bed> findByAvailabilityAndDepartment(Department department) {
        return bedRepository.findByAvailabilityAndDepartment(department.getId());
    }

    @Override
    public List<Bed> findAll() {
        return bedRepository.findAll();
    }

    @Override
    public List<Bed> findByRoom(Room room) {
        return bedRepository.findByRoomId(room.getId());
    }
}
