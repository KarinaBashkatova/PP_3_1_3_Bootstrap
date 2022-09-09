package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.security.MyUserDetails;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserDetailServ;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

/**
 * @author Karina.
 */
@Controller
@RequestMapping()
public class UserController {
    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public UserController(RoleServiceImpl roleServiceImpl) {
        this.roleServiceImpl = roleServiceImpl;
    }



    @GetMapping("/user")
    public String showUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("roles", roleServiceImpl.getRoleList());
        return "user";
    }




}
