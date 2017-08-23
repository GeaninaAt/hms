package com.hms.ws;

import com.hms.domain.Bed;
import com.hms.domain.Department;
import com.hms.domain.Room;
import com.hms.service.BedService;
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
@RequestMapping(value = "/beds")
@CrossOrigin(origins = "http://localhost:8000")
public class BedController {

    @Autowired
    private BedService bedService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/add/{roomId}", method = RequestMethod.POST)
    public ResponseEntity<Bed> addBed(@PathVariable("roomId") Long roomId,
                                      @RequestBody Bed bed,
                                      UriComponentsBuilder ucBuilder) {

        Room room = roomService.findById(roomId);

        if(room == null) {
            System.out.println("Room not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(room.getBeds().size() >= room.getNumberOfBeds()) {
            System.out.println("The room has reached max number of beds!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        bedService.addBed(bed, room);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/beds/{id}").buildAndExpand(bed.getId()).toUri());
        return new ResponseEntity<>(bed, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Bed>> findAllBeds() {
        List<Bed> beds = bedService.findAll();

        if((beds == null) || (beds.isEmpty())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(beds, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Bed> getBed(@PathVariable("id") Long bedId) {
        Bed bed = bedService.findById(bedId);

        if(bed == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bed, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getByNumber/{bedNo}", method = RequestMethod.GET)
    public ResponseEntity<Bed> getByNumber(@PathVariable("bedNo") String bedNo) {
        Bed bed = bedService.findByNumber(bedNo);

        if(bed == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bed, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/findAvailableByDep/{depName}", method = RequestMethod.GET)
    public ResponseEntity<List<Bed>> findAvailableByDep(@PathVariable("depName") String depName) {
        Department department = departmentService.findByName(depName);

        if(department == null) {
            System.out.println("Department not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Bed> beds = bedService.findByAvailabilityAndDepartment(department);
        return new ResponseEntity<>(beds, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Bed> deleteBed(@PathVariable("id") Long bedId) {
        Bed bed = bedService.findById(bedId);
        if(bed == null) {
            System.out.println("Bed not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        bedService.deleteBed(bed);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
