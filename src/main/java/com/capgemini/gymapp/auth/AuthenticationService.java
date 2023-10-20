package com.capgemini.gymapp.auth;

import com.capgemini.gymapp.Repositories.TokenRepository;
import com.capgemini.gymapp.Repositories.UserRepository;
import com.capgemini.gymapp.config.JwtService;
import com.capgemini.gymapp.entities.User;
import com.capgemini.gymapp.exception.UserAlreadyExistsException;
import com.capgemini.gymapp.exception.UserNotActivatedException;
import com.capgemini.gymapp.services.impl.EmailService;
import com.capgemini.gymapp.token.Token;
import com.capgemini.gymapp.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    @Autowired
    JavaMailSender javaMailSender;



    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    "User with email "+request.getEmail() + " already exists");

        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .activated(false)
                .build();
        String activationToken =generateActivationToken(user);
        emailService.sendActivationEmail(user, activationToken);
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    private String generateActivationToken(User user) {
        String activationToken = UUID.randomUUID().toString();

        user.setActivationToken(activationToken);
        return activationToken;
    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User is not activated.");
        }
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t ->{
            t.setExpired(true);
            t.setRevoked(true);
        } );
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }
}


