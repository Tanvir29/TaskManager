package com.example.taskManager.managedBean;

import com.example.taskManager.model.TaskStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class TaskStatusBean {

    public TaskStatus[] getTaskStatusValues() {
        return TaskStatus.values();
    }
}


