/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;


import com.example.taskManager.model.Project;
import com.example.taskManager.model.ProjectStatus;
import com.example.taskManager.model.TaskStatus;
import com.example.taskManager.service.ProjectStatsService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author saurav
 */
@Named
@ViewScoped
public class ProjectStatsBean implements Serializable {
    
    @Inject
    private ProjectStatsService projectStatsService;
    
   
    public Long getTotalProjects(){
        return projectStatsService.findTotalProjects();
    }
    
    public Long getTotalCompletedProjects(){
        return projectStatsService.findTotalProjectsByStatus(ProjectStatus.COMPLETED);
    }
    
    public Long getTotalRunningProjects(){
        return projectStatsService.findTotalProjectsByStatus(ProjectStatus.RUNNING);
    }
    
    public Long getTotalPausedProjects(){
        return projectStatsService.findTotalProjectsByStatus(ProjectStatus.ON_HOLD);
    }
    
    public Long getTotalCancelledProjects(){
        return projectStatsService.findTotalProjectsByStatus(ProjectStatus.CANCELED);
    }
    
    public Long getTotalTodoProjects(){
        return projectStatsService.findTotalProjectsByStatus(ProjectStatus.NOT_STARTED);
    }
    
    public Long getTotalTasksByProject(Project project){
        return projectStatsService.findTotalTasksByProject(project);
    }
    
    public Long getTotalOverduedTasksByProject(Project project){
        return projectStatsService.findTotalOverdueTasksCountInProject(project);
    }
    
    public Long getTotalCompletedTasksByProject(Project project){
        return projectStatsService.findTotalTasksCountInProjectByStatus(project,TaskStatus.COMPLETED);
    }
    
    public Long getTotalInProgressTasksByProject(Project project){
        return projectStatsService.findTotalTasksCountInProjectByStatus(project,TaskStatus.IN_PROGRESS);
    }
    public Long getTotalCancelledTasksByProject(Project project){
        return projectStatsService.findTotalTasksCountInProjectByStatus(project,TaskStatus.CANCELED);
    }
    
}
