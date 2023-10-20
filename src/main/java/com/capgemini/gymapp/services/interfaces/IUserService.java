package com.capgemini.gymapp.services.interfaces;

import com.capgemini.gymapp.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> getAllUsers();

    List<User> getAllClients();

    List<User> getAllCoachs();

    User updateUser(int id, User updatedUser);

    void deleteUser(int id);

    User activateUser(String activationToken);

    User findByActivationToken(String activationToken);

    User getUser(Integer id);
}
