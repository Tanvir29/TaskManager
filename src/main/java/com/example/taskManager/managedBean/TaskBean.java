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
import com.example.taskManager.service.FeedbackService;
import com.example.taskManager.service.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class TaskBean implements Serializable{

    @Inject
    private TaskService taskService;
    
    @Inject
    private FeedbackService feedbackService;
    
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
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
        taskService.createTask(task);
        return "/app/taskView/taskList";
    }
    
    public String findTask(Long id) {
        task = taskService.getTasksById(id);
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("editedTask", task);
        return "/app/taskView/editTask.xhtml?faces-redirect=true&id=" + id;
    }
    
    
    public String editTask() {
        taskService.updateTask(task);
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().remove("editedTask");
        return "/app/taskView/taskList";
}

    public String deleteTask(Long id){
        taskService.deleteTask(id);
        return "/app/taskView/taskList";
    }

}


