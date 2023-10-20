package com.capgemini.gymapp.auth;


import com.capgemini.gymapp.entities.User;
import com.capgemini.gymapp.services.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));

    }
    @GetMapping("demo")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured point");
    }


    @GetMapping("/activate")
    @Hidden
    public ResponseEntity<Object> activateAccount(@RequestParam String token) {
        User activatedUser = userService.activateUser(token);
        if (activatedUser != null) {
            return ResponseEntity.ok("Account activated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid activation token.");
        }
    }

}
