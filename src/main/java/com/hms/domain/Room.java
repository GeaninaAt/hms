package com.hms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gatomulesei on 8/8/2017.
 */
@Entity
public class Room extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 1L;

    @Size(min = 1, max = 5)
    @Column(unique = true)
    private String code;

    private Integer numberOfBeds;

    @ManyToOne
    private Department department;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<Bed> beds = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Integer numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Bed> getBeds() {
        return beds;
    }

    public void setBeds(List<Bed> beds) {
        this.beds = beds;
    }
}
