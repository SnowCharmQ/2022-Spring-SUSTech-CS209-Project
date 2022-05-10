package com.cs209.project.mapper;

import com.cs209.project.entity.User;

public interface UserMapper {
    Integer insert(User user);

    User selectByUsername(String username);
}
