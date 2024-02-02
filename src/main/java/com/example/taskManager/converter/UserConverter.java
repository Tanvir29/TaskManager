package com.example.taskManager.converter;

import com.example.taskManager.managedBean.UserBean;
import com.example.taskManager.model.User;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "userConverter")
public class UserConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        UserBean userBean = facesContext.getApplication().
                evaluateExpressionGet(facesContext, "#{userBean}", UserBean.class);
        return userBean.findUserById(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof User) {
            return String.valueOf(((User) value).getId());
        }
        return null;
    }
}
