/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;

import com.example.taskManager.model.User;
import com.example.taskManager.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UserBean {

    @Inject
    private UserService userService;

    private List<User> users;

    // Getters and setters

    @PostConstruct
    public void init() {
        loadAllUsers();
    }

    public void loadAllUsers() {
        users = userService.getAllUsers();
    }

    public List<User> getUsers() {
        return users;
    }

}
