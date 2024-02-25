package com.example.taskManager.converter;

import com.example.taskManager.model.Task;
import com.example.taskManager.service.TaskService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(forClass = Task.class, managed = true)
public class TaskConverter implements Converter<Task> {

    @Inject
    private TaskService taskService;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Task task) {
        System.out.println("Task:" + task);
        if (task == null) {
            return "";
        }
        if (task.getId() != null) {
            return task.getId().toString();
        } else {
            throw new ConverterException(new FacesMessage("Invalid task ID"));
        }
    }

    @Override
    public Task getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            Long taskId = Long.valueOf(value);
            Task task = taskService.getTaskById(taskId);
            if (task == null) {
                throw new ConverterException(new FacesMessage("Task not found with ID: " + value));
            }
            return task;
        } catch (NumberFormatException e) {
            throw new ConverterException("Invalid Task ID format.", e);
        }
    }
}

