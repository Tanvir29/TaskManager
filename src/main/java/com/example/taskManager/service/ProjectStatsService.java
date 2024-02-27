/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.service;

import com.example.taskManager.model.Project;
import com.example.taskManager.model.ProjectStatus;
import com.example.taskManager.model.TaskStatus;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;

/**
 *
 * @author saurav
 */
@Stateless
public class ProjectStatsService {
    
    @PersistenceContext
    private EntityManager entityManager;

    public Long findTotalProjects() {
        return entityManager.createQuery("SELECT COUNT(p) from Project p", Long.class).getSingleResult();
    }
    
    public Long findTotalProjectsByStatus(ProjectStatus status) {
        return entityManager.createQuery(
                "SELECT COUNT(p) FROM Project p WHERE p.status = :status", Long.class)
                .setParameter("status", status)
                .getSingleResult();
    }
    
    public Long findTotalTasksByProject(Project project) {
        return entityManager.createQuery(
                "SELECT COUNT(t) FROM Task t WHERE t.project = :project", Long.class)
                .setParameter("project", project)
                .getSingleResult();
    }
     
    public long findTotalOverdueTasksCountInProject(Project project) {
        return entityManager.createQuery(
                "SELECT COUNT(t) FROM Task t WHERE t.project = :project AND t.dueDate < :currentDate AND t.status != :status", Long.class)
                .setParameter("project", project)
                .setParameter("currentDate", LocalDate.now())
                .setParameter("status", TaskStatus.COMPLETED)
                .getSingleResult();
    }

    public long findTotalTasksCountInProjectByStatus(Project project, TaskStatus status) {
        return entityManager.createQuery(
                "SELECT COUNT(t) FROM Task t WHERE t.project = :project AND t.status = :status", Long.class)
                .setParameter("project", project)
                .setParameter("status", status)
                .getSingleResult();
    }
   
}
