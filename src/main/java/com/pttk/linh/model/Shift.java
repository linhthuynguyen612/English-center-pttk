package com.pttk.linh.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    private Date startTime;
    private Date endTime;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @OneToMany(mappedBy = "shift")
    private List<Lesson> lessons;
}
