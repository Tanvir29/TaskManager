package com.example.taskManager.managedBean;

import com.example.taskManager.model.UserRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class UserRoleBean {

    public UserRole[] getUserRoleValues() {
        return UserRole.values();
    }
}
