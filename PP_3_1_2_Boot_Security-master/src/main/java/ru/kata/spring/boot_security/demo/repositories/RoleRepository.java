package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Role;

/**
 * @author Karina Bashkatova.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
