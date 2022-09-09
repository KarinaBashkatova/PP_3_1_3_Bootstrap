package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

/**
 * @author Karina Bashkatova.
 */
public interface UserService {
    List<User> showAllUsers ();
    User showUser (int id);
    void saveUser(User user);
    void update (int id, User updatedUser);
    void delete (int id);
}
