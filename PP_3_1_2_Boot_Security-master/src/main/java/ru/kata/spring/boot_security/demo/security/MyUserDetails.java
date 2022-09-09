package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Karina Bashkatova.
 */

public class MyUserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
 /*       Collection<Role> roles = user.getRoles();
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());*/
        return user.getRoles().stream()
                .map(Role::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword(); // возвращает пароль пользователя
    }

    @Override
    public String getUsername() {
        return this.user.getUsername(); // возвращает имя пользователя
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // аккаунт не просрочен
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // не заблокирован
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // пароль не просрочен
    }

    @Override
    public boolean isEnabled() {
        return true; // аккаунт включен и работает
    }

    public User getUser() { // нужен нам для полуения данных аутентифицированного пользователя
        return this.user;
    }

}
