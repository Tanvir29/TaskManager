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
import com.example.taskManager.model.TaskPriority;
import com.example.taskManager.model.TaskStatus;
import com.example.taskManager.model.User;
import com.example.taskManager.service.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Named
@ViewScoped
public class TaskBean implements Serializable{

    @Inject
    private TaskService taskService;
    
    private Task task;
    
    private List<Task> filteredTaskList;
    
    private String statusFilter;
    private String priorityFilter;
    private LocalDate dueDateFilter;

    public LocalDate getDueDateFilter() {
        return dueDateFilter;
    }

    public void setDueDateFilter(LocalDate dueDateFilter) {
        this.dueDateFilter = dueDateFilter;
    }

    public String getStatusFilter() {
        return statusFilter;
    }

    public void setStatusFilter(String statusFilter) {
        this.statusFilter = statusFilter;
    }

    public String getPriorityFilter() {
        return priorityFilter;
    }

    public void setPriorityFilter(String priorityFilter) {
        this.priorityFilter = priorityFilter;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    
    public List<Task> getFilteredTaskList() {
        return filteredTaskList;
    }

    public void setFilteredTaskList(List<Task> filteredTaskList) {
        this.filteredTaskList = filteredTaskList;
    }

    @PostConstruct
    public void init() {
        task = new Task();
    }
    
    public TaskStatus[] getTaskStatusValues() {
        return TaskStatus.values();
    }
    
    public TaskPriority[] getTaskPriorityValues() {
        return TaskPriority.values();
    }

    public List<Task> loadTaskList() {
        return taskService.getAllTasks();
    }
    
    public String createTask(List<User> users) throws MessagingException, IOException{
        taskService.createTask(task, users);
     
        return "/app/taskView/taskList?faces-redirect=true";
    }
    
    public void findTask(Long id) {
        task = taskService.getTaskById(id);
    }
    
    public String editTask() throws MessagingException, IOException {
        taskService.updateTask(task);
        return "/app/taskView/taskList?faces-redirect=true";
    }

    public String deleteTask(Task task) throws MessagingException, IOException{
        taskService.deleteTask(task);
        return "/app/taskView/taskList?faces-redirect=true";
    }
      
    public List<Task> filterTasks() {
        return taskService.filterTasks(statusFilter, priorityFilter, dueDateFilter);
    }
}


