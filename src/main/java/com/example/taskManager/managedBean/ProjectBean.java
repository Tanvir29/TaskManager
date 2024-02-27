/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.managedBean;
import com.example.taskManager.model.Project;
import com.example.taskManager.model.ProjectStatus;
import com.example.taskManager.service.ProjectService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author saurav
 */
@Named
@ViewScoped
public class ProjectBean implements Serializable {
    private Project project;
    
    @Inject
    private ProjectService projectService; 
    
    @PostConstruct
    public void init() {
        project = new Project();
    }
    
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    public List<Project> findAllProjects() {
        return projectService.findAllProjects();
    }
    
    public void findProject(Long projectId){
        project = projectService.findProjectById(projectId);
    }
    public String createProject() {
        try {
            projectService.createProject(project);
            return "/app/projectView/projects?faces-redirect=true";
        } catch (Exception e) {          
            throw e;
        }
    }

     public String editProject() {
        projectService.updateProject(project);
        return "/app/projectView/projects?faces-redirect=true";
    }
    
    public String deleteProject(Project project){
        projectService.deleteProject(project);
        return "/app/projectView/projects?faces-redirect=true";
    }
    public ProjectStatus[] getProjectStatusValues() {
        return ProjectStatus.values();
    }
   
}