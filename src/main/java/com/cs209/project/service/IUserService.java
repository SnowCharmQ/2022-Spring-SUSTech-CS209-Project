package com.cs209.project.service;

import com.cs209.project.entity.User;

public interface IUserService {
    void reg(User user);

    User login(String username, String password);
}
