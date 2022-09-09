package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.services.UserDetailServ;

import java.security.AuthProvider;

@Configuration
@EnableWebSecurity // помечаем конфиг.класс для spring security, обязательно наследуем этот класс от WebSecurityConfigurerAdapter
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserDetailServ userDetailServ;



    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailServ userDetailServ) {
        this.successUserHandler = successUserHandler;
        this.userDetailServ = userDetailServ;
    }


    // настраиваем аутентификацию
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServ);
    }

    // пока никак не шифрую пароль
    @Bean
    public PasswordEncoder getPasswordEncoder () {
        return NoOpPasswordEncoder.getInstance();
    }

    // конфигурируем Spring Security (Какая стр. отвечает за сход, за ошибки...),
    // тут же конфигурируем авторизацию
    @Override
    protected void configure(HttpSecurity http) throws Exception { // в метод поступает http запрос
        // нам надоп посмотреть, он от аутентиф. пользователя или нет

        http
                .csrf().disable()
                .authorizeRequests()// все запросы будут проходить через авторизацию
                .antMatchers( "/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/", "/index", "/login", "/error").permitAll()
                 .anyRequest().authenticated() // на все остальные страницы - только аутентиф.пользователя
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/user")
                .successHandler(successUserHandler)
                .failureUrl("/auth/login?error")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/auth/login")
                .permitAll();
    }

    // аутентификация inMemory
//    @Bean
  //  @Override
    //public UserDetailsService userDetailsService() {
  //      UserDetails user =
 //               User.withDefaultPasswordEncoder()
   //                     .username("user")
     //                   .password("user")
       //                 .roles("USER")
         //               .build();

    //    return new InMemoryUserDetailsManager(user);
   // }


    // jdbc-аутентификация
//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//         return jdbcUserDetailsManager;
//    }

    // DAO authentication provider
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailServ);
        return daoAuthenticationProvider;
    }


}