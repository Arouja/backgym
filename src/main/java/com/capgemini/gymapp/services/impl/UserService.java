package com.capgemini.gymapp.services.impl;

import com.capgemini.gymapp.Repositories.UserRepository;
import com.capgemini.gymapp.entities.Role;
import com.capgemini.gymapp.entities.User;
import com.capgemini.gymapp.services.interfaces.IUserService;
import lombok.With;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();

    }

    @Override
    public List<User> getAllClients() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .filter(user -> user.getRole() == Role.CLIENT)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllCoachs() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .filter(user -> user.getRole() == Role.COACH)
                .collect(Collectors.toList());
    }


    @Override
    public User updateUser(int id, User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            userRepository.save(existingUser);

        }
        return existingUser;
    }

    @Override
    public void deleteUser(int id) {
        Optional<User> userOptional = userRepository.findById(id);

        userOptional.ifPresent(user -> {
            // Set user for tokens to null to prevent reference issues
            user.getTokens().forEach(token -> token.setUser(null));
            userRepository.delete(user);
        });
    }



    @Override
    public User activateUser(String activationToken) {
        User user = userRepository.findByActivationToken(activationToken);
        if (user != null) {
            user.setActivated(true);
            user.setActivationToken(null); // Clear the activation token
            userRepository.save(user);
            return user;
        }
        return null;
    }


    @Override
    public User findByActivationToken(String activationToken) {
        return userRepository.findByActivationToken(activationToken);
    }

    @Override
    public User getUser(Integer id){
        return userRepository.findById(id).orElse(null);
    }



}