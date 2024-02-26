/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.converter;

import com.example.taskManager.model.Feedback;
import com.example.taskManager.model.Project;
import com.example.taskManager.service.ProjectService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

/**
 *
 * @author saurav
 */
@FacesConverter(forClass = Project.class, managed = true)
public class ProjectConverter implements Converter<Project> {

    @Inject
    private ProjectService projectService;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Project project) {
        if (project == null) {
            return "";
        }
        if (project.getId() != null) {
            return project.getId().toString();
        } else {
            throw new ConverterException(new FacesMessage("Invalid feedback ID"));
        }
    }

    @Override
    public Project getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            Long projectId = Long.valueOf(value);
            Project project = projectService.findProjectById(projectId);
            if (project == null) {
                throw new ConverterException(new FacesMessage("Project not found with ID: " + value));
            }
            return project;
        } catch (NumberFormatException e) {
            throw new ConverterException("Invalid project ID format.", e);
        }
    }

}
