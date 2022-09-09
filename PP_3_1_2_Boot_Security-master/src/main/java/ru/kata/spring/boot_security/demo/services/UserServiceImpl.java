package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Karina Bashkatova.
 */
@Service
@Transactional(readOnly = true) //все публичные методы этого класса будут с такой аннотацией, и только чтение. Поэтому там, где нужно изменение, будет своя аннотация,
//имеющая преимущество надо аннотацией класса
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> showAllUsers () {
        return usersRepository.findAll();
    }

    @Override
    public User showUser (int id) {
        Optional<User> foundUser = usersRepository.findById(id);

        return foundUser.orElse(null);
    }
    @Override
    @Transactional
    public void saveUser(User user) {
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void update (int id, User updatedUser) {
        updatedUser.setId(id);
        usersRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public void delete (int id) {

        usersRepository.deleteById(id);
    }
}
