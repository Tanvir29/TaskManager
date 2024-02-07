package com.example.taskManager.converter;

import com.example.taskManager.model.User;
import com.example.taskManager.service.UserService;
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

@FacesConverter(forClass = User.class, managed = true)
public class UserConverter implements Converter<User> {

    @Inject
    private UserService userService;

    @Override
    public String getAsString(FacesContext context, UIComponent component, User user) {
        if (user == null) {
            return "";
        }
        if (user.getId() != null) {
            return user.getId().toString();
        } else {
            throw new ConverterException(new FacesMessage("Invalid user ID"));
        }
    }

    @Override
    public User getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            Long userId = Long.valueOf(value);
            User user = userService.findUserById(userId);
            if (user == null) {
                throw new ConverterException(new FacesMessage("User not found with ID: " + value));
            }
            return user;
        } catch (NumberFormatException e) {
            throw new ConverterException("Invalid user ID format.", e);
        }
    }
}
