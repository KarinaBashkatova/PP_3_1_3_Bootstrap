package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

/**
 * @author Karina Bashkatova.
 */

// Реализация доступа к БД
@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    @EntityGraph(value = "graph.User.roles", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByUserName(String username);
}
