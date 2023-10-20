package com.capgemini.gymapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="_Session")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String name;
    private LocalDate date;
    private LocalTime time;
    private int duration;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<User> clients;

}
