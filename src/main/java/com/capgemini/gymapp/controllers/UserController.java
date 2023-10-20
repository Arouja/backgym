package com.capgemini.gymapp.controllers;


import com.capgemini.gymapp.Repositories.UserRepository;
import com.capgemini.gymapp.auth.AuthenticationService;
import com.capgemini.gymapp.config.JwtService;
import com.capgemini.gymapp.entities.User;
import com.capgemini.gymapp.entities.UserDTO;
import com.capgemini.gymapp.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {
    @Autowired
    private AuthenticationService service;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  IUserService userService;
    @Autowired
    private  JwtService jwtService;


    @GetMapping("/getuserbyid/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getRole(), user.getAttachment().getName());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/all-users")
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDTO> userDTOs = users.stream()
                    .map(user -> new UserDTO(user.getId(),user.getFirstName(), user.getLastName(), user.getUsername(), user.getRole(), user.getAttachment().getName()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(userDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Users not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/all-clients")
    public ResponseEntity<Object> getAllClients() {
        try {
            List<User> clients = userService.getAllClients();
            List<UserDTO> clientDTOs = clients.stream()
                    .map(user -> new UserDTO(user.getId(),user.getFirstName(), user.getLastName(), user.getUsername(), user.getRole(), user.getAttachment().getFilePath()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Clients not found", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(value = "/all-coachs")
    public ResponseEntity<Object> getAllCoachs() {
        try {
            List<User> coachs = userService.getAllCoachs();
            List<UserDTO> clientDTOs = coachs.stream()
                    .map(user -> new UserDTO(user.getId(),user.getFirstName(), user.getLastName(), user.getUsername(), user.getRole(), user.getAttachment().getFilePath()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Coachs not found", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        userService.deleteUser (id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Object> updateUser (@PathVariable int id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        if (user  != null) {
            return new ResponseEntity<>(user , HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getuserbyemail/{email}")
    public ResponseEntity<Object> getUserByemail(@PathVariable String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getRole(), user.getAttachment().getFilePath());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email-from-token/{token}")
    public ResponseEntity<String> getEmailFromToken(@PathVariable String token) {
        try {
            String email = jwtService.getEmailFromToken(token);
            return ResponseEntity.ok(email);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/getuserbytoken/{token}")
    public ResponseEntity<Object> getUserByToken(@PathVariable String token) {

        String email = jwtService.getEmailFromToken(token);
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getRole(), user.getAttachment().getName());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }


}