package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.*;

import javax.validation.Valid;

/**
 * @author Karina Bashkatova.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final UserDetailServ userDetailServ;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, UserDetailServ userDetailServ, RoleService roleService) {
        this.userService = userService;
        this.userDetailServ = userDetailServ;
        this.roleService = roleService;
    }


    @GetMapping()
    public String allUsers(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDetailServ.loadUserByUsername(name).getUser();
        model.addAttribute("currentUser", user);
        model.addAttribute("users", userService.showAllUsers());
        model.addAttribute("roles", roleService.getRoleList());

        return "users/admin"  ;
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute ("user") User user, Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userDetailServ.loadUserByUsername(name).getUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("roles", roleService.getRoleList());

        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("{id}/edit")
    @Secured("ADMIN")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.showUser(id));
        return "users/edit";
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }

        userService.update(id, user);
        return  "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        model.addAttribute("roles", roleService.getRoleList());
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("user")
    public String show(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userDetailServ.loadUserByUsername(name).getUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("roles", roleService.getRoleList());
        return "users/show";
    }

}