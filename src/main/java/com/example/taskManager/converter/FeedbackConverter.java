/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.taskManager.converter;

import com.example.taskManager.model.Feedback;
import com.example.taskManager.service.FeedbackService;
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


@FacesConverter(value = "feedbackConverter")
public class FeedbackConverter implements Converter {
    
    @Inject
    private FeedbackService feedbackService;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            Long feedbackId = Long.valueOf(value);
            Feedback feedback = feedbackService.getFeedbackById(feedbackId);
            if (feedback.getText() == null || feedback.getText().isEmpty()) {
                throw new ConverterException("Feedback text cannot be empty.");
            }

            return feedback;
        } catch (NumberFormatException e) {
            throw new ConverterException("Invalid feedback ID format.", e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || !(value instanceof Feedback)) {
            return null;
        }

        Feedback feedback = (Feedback) value;
        return String.valueOf(feedback.getId());
    }
}

