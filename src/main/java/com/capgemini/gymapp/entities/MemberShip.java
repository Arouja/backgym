package com.capgemini.gymapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

import static java.lang.System.currentTimeMillis;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="_Membership")
public class MemberShip implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;
    private int price;
    @Column(nullable = false, updatable = false)
    Timestamp createdAt;
    @Column(nullable = false)
    Timestamp updatedAt;
    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(currentTimeMillis());
        updatedAt= new Timestamp(currentTimeMillis());
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(currentTimeMillis());
    }

    @ManyToOne
    User user;

}
