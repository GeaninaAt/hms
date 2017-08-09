package com.hms.ws;

import com.hms.domain.Department;
import com.hms.domain.Room;
import com.hms.service.DepartmentService;
import com.hms.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@RestController
@RequestMapping(value = "/rooms")
@CrossOrigin(origins = "http://localhost:8000")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/add/{departmentId}", method = RequestMethod.POST)
    public ResponseEntity<Room> addRoom(@PathVariable("departmentId") Long departmentId,
                                        @RequestBody Room room,
                                        UriComponentsBuilder ucBuilder) {

        Department department = departmentService.findById(departmentId);

        if(department == null) {
            System.out.println("Department not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roomService.addRoom(room, department);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/rooms/{id}").buildAndExpand(room.getId()).toUri());
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Room>> findAllRooms() {
        List<Room> rooms = roomService.findAll();

        if((rooms == null) || (rooms.isEmpty())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Room> getRoom(@PathVariable("id") Long roomId) {
        Room room = roomService.findById(roomId);

        if(room == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(room, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getByCode/{code}", method = RequestMethod.GET)
    public ResponseEntity<Room> getByCode(@PathVariable("code") String code) {
        Room room = roomService.findByCode(code);

        if(room == null) {
            System.out.println("No room having this code was found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @RequestMapping(value = "/getByDepartment/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Room>> getByDepartment(@PathVariable("id") Long departmentId) {
        Department department = departmentService.findById(departmentId);
        if(department == null) {
            System.out.println("No department having this id was found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Room> rooms = roomService.findByDepartment(department);

        if((rooms == null) || (rooms.isEmpty())) {
            System.out.println("No rooms were found in this department!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Room> updateRoom(@PathVariable("id") Long roomId, @RequestBody Room room) {
        Room currentRoom = roomService.findById(roomId);

        if(currentRoom == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(room.getCode() != null) {
            currentRoom.setCode(room.getCode());
        }

        if(room.getNumberOfBeds() != null) {
            currentRoom.setNumberOfBeds(room.getNumberOfBeds());
        }

        roomService.addRoom(currentRoom);
        return new ResponseEntity<>(currentRoom, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Room> deleteRoom(@PathVariable("id") Long roomId) {
        Room room = roomService.findById(roomId);

        if(room == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roomService.deleteRoom(room);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
