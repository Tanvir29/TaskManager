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
import com.example.taskManager.model.User;
import com.example.taskManager.service.FeedbackService;
import com.example.taskManager.service.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class TaskBean {

    @Inject
    private TaskService taskService;
    
    @Inject
    private FeedbackService feedbackService;
    
    private Task task, taskToEdit;
    private List<Task> taskList;
    private List<User> selectedUsers;
    
    public List<User> getSelectedUsers() {
        return selectedUsers;
    }
    
    public Task getTaskToEdit() {
        return (Task) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("editedTask");
    }

    public void setSelectedUsers(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
    

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }
    

    @PostConstruct
    public void init() {
        task = new Task();
        loadTaskList();
    }

    public List<Task> loadTaskList() {
        return taskService.getAllTasks();
    }
    
    public String createTask() {
        System.out.println("Added task : " + task);
        taskService.createTask(task);
        return "/app/taskView/taskList";
    }
    
    public String findTask(Long id) {
        Task tasks = taskService.getTasksById(id);
        System.out.println("Value obt: " + tasks);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editedTask", tasks);
        return "/app/taskView/editTask";
    }
    
    
    public String editTask() {
        System.out.println("Edit:"+taskToEdit);
        taskService.updateTask(taskToEdit);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editedTask");
        return "/app/taskView/taskList";
}

    public String deleteTask(Long id){
        taskService.deleteTask(id);
        return "/app/taskView/taskList";
    }

}


