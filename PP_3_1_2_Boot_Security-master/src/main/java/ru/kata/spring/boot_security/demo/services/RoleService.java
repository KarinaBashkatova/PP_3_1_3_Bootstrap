package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

/**
 * @author Karina Bashkatova.
 */
public interface RoleService {

    List<Role> getRoleList();
}