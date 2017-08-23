package com.hms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@Entity
public class Bed extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String number;

    private boolean available;

    @ManyToOne
    private Room room;

    @OneToOne
    @JsonIgnore
    private Admission admission;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }
}
