package com.cs209.project.service;

import com.cs209.project.entity.MyBatisQuestion;

import java.util.List;

public interface IMyBatisService {
    List<MyBatisQuestion> selectMyBatisQuestion(String sorting, String key, String page);
    List<MyBatisQuestion> selectAllMyBatisQuestion();
}
