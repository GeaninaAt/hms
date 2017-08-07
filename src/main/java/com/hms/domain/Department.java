package com.hms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gatomulesei on 8/7/2017.
 */
@Entity
public class Department extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    @Lob
    private String description;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Doctor> doctors = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}
