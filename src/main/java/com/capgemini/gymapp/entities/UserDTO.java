package com.capgemini.gymapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private Role role;
    private String path;
}
