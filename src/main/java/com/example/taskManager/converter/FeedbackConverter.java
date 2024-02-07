/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.converter;

import com.example.taskManager.model.Feedback;
import com.example.taskManager.service.FeedbackService;
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
@FacesConverter(forClass = Feedback.class, managed = true)
public class FeedbackConverter implements Converter<Feedback> {

    @Inject
    private FeedbackService feedbackService;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Feedback feedback) {
        if (feedback == null) {
            return "";
        }
        if (feedback.getId() != null) {
            return feedback.getId().toString();
        } else {
            throw new ConverterException(new FacesMessage("Invalid feedback ID"));
        }
    }

    @Override
    public Feedback getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            Long feedbackId = Long.valueOf(value);
            Feedback feedback = feedbackService.getFeedbackById(feedbackId);
            if (feedback == null) {
                throw new ConverterException(new FacesMessage("Feedback not found with ID: " + value));
            }
            return feedback;
        } catch (NumberFormatException e) {
            throw new ConverterException("Invalid feedback ID format.", e);
        }
    }
}
