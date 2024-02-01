/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;

import com.example.taskManager.model.User;
import com.example.taskManager.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.util.List;

@Named
@RequestScoped
public class UserBean {

    @Inject
    private UserService userService;

    private List<User> users;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @PostConstruct
    public void init() {
        user = new User();
        loadAllUsers();
    }

    public void loadAllUsers() {
        users = userService.getAllUsers();
    }
    
    public String saveUser() {
        userService.saveUser(user);
        user = new User();
        loadAllUsers();
        
        return "userSuccess";
    }

}
