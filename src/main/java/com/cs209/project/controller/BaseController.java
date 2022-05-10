package com.cs209.project.controller;

import com.cs209.project.service.ex.PasswordNotMatchException;
import com.cs209.project.service.ex.ServiceException;
import com.cs209.project.service.ex.UsernameDuplicatedException;
import com.cs209.project.service.ex.UserNotFoundException;
import com.cs209.project.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    public static final int ok = 200;

    protected final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }

    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
        }
        return result;
    }
}
