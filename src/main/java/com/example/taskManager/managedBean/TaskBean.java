/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;

/**
 *
 * @author hasan
 */

import com.example.taskManager.model.Task;
import com.example.taskManager.model.TaskStatus;
import com.example.taskManager.service.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TaskBean implements Serializable{

    @Inject
    private TaskService taskService;
    
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @PostConstruct
    public void init() {
        createNewTask();
        loadTaskList();
    }
    
    public TaskStatus[] getTaskStatusValues() {
        return TaskStatus.values();
    }
    
    public void createNewTask() {
        task = new Task();
    }

    public List<Task> loadTaskList() {
        return taskService.getAllTasks();
    }
    
    public String createTask() {
        taskService.createTask(task);
        createNewTask();

        return "/app/taskView/taskList?faces-redirect=true";
    }
    
    public String findTask(Long id) {
        task = taskService.getTaskById(id);
        return null;
    }
    
    public String editTask() {
        taskService.updateTask(task);
        createNewTask();
        return "/app/taskView/taskList?faces-redirect=true";
}

    public String deleteTask(Task task){
        taskService.deleteTask(task);
        createNewTask();     
        return "/app/taskView/taskList?faces-redirect=true";
    }
}


