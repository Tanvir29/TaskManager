/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;

import com.example.taskManager.model.Task;
import com.example.taskManager.model.User;
import com.example.taskManager.model.UserRole;
import com.example.taskManager.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UserBean implements Serializable{
    
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
        users = userService.findAllUsers();
    }
    
    public UserRole[] getUserRoleValues() {
        return UserRole.values();
    }
    
    public String saveUser() {
        userService.saveUser(user);
        
        return "/app/userView/users?faces-redirect=true";
    }
    
    public User findUserById(long id) {
        return userService.findUserById(id);
    }
    
    public String deleteUser(User user){
        userService.deleteUser(user);
        return "/app/userView/users?faces-redirect=true";
    }
    
    public List<Task> showTaskHistory(User user){
        return userService.findUserTasks(user,true);
    }
    
    public List<Task> showCurrentTasks(User user){
        return userService.findUserTasks(user,false);
    }

}
