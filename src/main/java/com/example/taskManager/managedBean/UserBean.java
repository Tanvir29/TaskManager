/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;

import com.example.taskManager.model.User;
import com.example.taskManager.model.UserRole;
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
    
    public UserRole[] getUserRoleValues() {
        return UserRole.values();
    }

    public void loadAllUsers() {
        users = userService.findAllUsers();
    }
    
    public String saveUser() {
        userService.saveUser(user);
        
        return "/app/userView/users?faces-redirect=true";
    }

    public User findUserById(long id) {
        return userService.findUserById(id);
    }
    
    public String deleteUser(Long id){
        userService.deleteUser(id);
        return "/app/userView/users?faces-redirect=true";
    }

}
