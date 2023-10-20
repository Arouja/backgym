package com.capgemini.gymapp.controllers;

import com.capgemini.gymapp.Repositories.UserRepository;
import com.capgemini.gymapp.config.JwtService;
import com.capgemini.gymapp.entities.User;
import com.capgemini.gymapp.entities.UserDTO;
import com.capgemini.gymapp.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    IUserService userService;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured point");
    }

/*
    @GetMapping("/email-from-token")
    public ResponseEntity<String> getEmailFromToken(@RequestParam("token") String token) {
        try {
            String email = jwtService.getEmailFromToken(token);
            return ResponseEntity.ok(email);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @GetMapping("/user-by-email")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if user is not found
        }
    }
*/


    @GetMapping("/getuserbyid/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getRole(), user.getAttachment().getFilePath());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

}
