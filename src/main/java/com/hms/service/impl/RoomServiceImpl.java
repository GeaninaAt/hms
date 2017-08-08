package com.hms.service.impl;

import com.hms.domain.Department;
import com.hms.domain.Room;
import com.hms.repository.RoomRepository;
import com.hms.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@Service("roomService")
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room addRoom(Room room, Department department) {
        room.setDepartment(department);
        department.getRooms().add(room);
        return roomRepository.save(room);
    }

    @Override
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoom(Room room) {
        Department department = room.getDepartment();
        department.getRooms().remove(room);
        room.setDepartment(null);
        roomRepository.delete(room);
    }

    @Override
    public Room findById(Long roomId) {
        return roomRepository.findOne(roomId);
    }

    @Override
    public Room findByCode(String code) {
        return roomRepository.findByCode(code);
    }

    @Override
    public List<Room> findByDepartment(Department department) {
        return roomRepository.findByDepartmentId(department.getId());
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }
}
