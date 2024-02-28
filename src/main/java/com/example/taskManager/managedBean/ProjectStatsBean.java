/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;


import com.example.taskManager.model.Project;
import com.example.taskManager.model.ProjectStatus;
import com.example.taskManager.model.TaskStatus;
import com.example.taskManager.service.ProjectService;
import com.example.taskManager.service.ProjectStatsService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author saurav
 */
@Named
@ViewScoped
public class ProjectStatsBean implements Serializable {

    @Inject
    private ProjectStatsService projectStatsService;
    
    @Inject
    private ProjectService projectService;

    private Long totalProjects;
    private Long totalCompletedProjects;
    private Long totalRunningProjects;
    private Long totalPausedProjects;
    private Long totalCancelledProjects;
    private Long totalTodoProjects;
    private List<Project> allProjects;
    
    @PostConstruct
    public void init() {
        totalProjects = projectStatsService.findTotalProjects();
        totalCompletedProjects = projectStatsService.findTotalProjectsByStatus(ProjectStatus.COMPLETED);
        totalRunningProjects = projectStatsService.findTotalProjectsByStatus(ProjectStatus.RUNNING);
        totalPausedProjects = projectStatsService.findTotalProjectsByStatus(ProjectStatus.ON_HOLD);
        totalCancelledProjects = projectStatsService.findTotalProjectsByStatus(ProjectStatus.CANCELED);
        totalTodoProjects = projectStatsService.findTotalProjectsByStatus(ProjectStatus.NOT_STARTED);
        allProjects = projectService.findAllProjects();
    }

    public Long getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(Long totalProjects) {
        this.totalProjects = totalProjects;
    }

    public Long getTotalCompletedProjects() {
        return totalCompletedProjects;
    }

    public void setTotalCompletedProjects(Long totalCompletedProjects) {
        this.totalCompletedProjects = totalCompletedProjects;
    }

    public Long getTotalRunningProjects() {
        return totalRunningProjects;
    }

    public void setTotalRunningProjects(Long totalRunningProjects) {
        this.totalRunningProjects = totalRunningProjects;
    }

    public Long getTotalPausedProjects() {
        return totalPausedProjects;
    }

    public void setTotalPausedProjects(Long totalPausedProjects) {
        this.totalPausedProjects = totalPausedProjects;
    }

    public Long getTotalCancelledProjects() {
        return totalCancelledProjects;
    }

    public void setTotalCancelledProjects(Long totalCancelledProjects) {
        this.totalCancelledProjects = totalCancelledProjects;
    }

    public Long getTotalTodoProjects() {
        return totalTodoProjects;
    }

    public void setTotalTodoProjects(Long totalTodoProjects) {
        this.totalTodoProjects = totalTodoProjects;
    }

    public List<Project> getAllProjects() {
        return allProjects;
    }

    public void setAllProjects(List<Project> allProjects) {
        this.allProjects = allProjects;
    }
    
    public Map<Project, Map<String, Long>> getProjectStats() {
        Map<Project, Map<String, Long>> projectStats = new HashMap<>();

        for (Project project : allProjects) {
            Map<String, Long> projectData = new HashMap<>();
            projectData.put("totalTasks", projectStatsService.findTotalTasksByProject(project));
            projectData.put("overdueTasks", projectStatsService.findTotalOverdueTasksCountInProject(project));
            projectData.put("completedTasks", projectStatsService.findTotalTasksCountInProjectByStatus(project, TaskStatus.COMPLETED));
            projectData.put("inProgressTasks", projectStatsService.findTotalTasksCountInProjectByStatus(project, TaskStatus.IN_PROGRESS));
            projectData.put("cancelledTasks", projectStatsService.findTotalTasksCountInProjectByStatus(project, TaskStatus.CANCELED));
            projectStats.put(project, projectData);
        }

        return projectStats;
    }

    

}
