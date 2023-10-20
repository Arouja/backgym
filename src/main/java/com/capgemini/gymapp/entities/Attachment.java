package com.capgemini.gymapp.entities;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATT_ID")
    private Long id;

    private String name;
    private String type;
    private String filePath;


    @OneToOne(mappedBy="attachment")
    User user;

}