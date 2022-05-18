package com.cs209.project.mapper;

import com.cs209.project.entity.SpringBootQuestion;

import java.util.List;

public interface SpringBootQuestionMapper {
    List<SpringBootQuestion> selectByTimeAsc(int offset);

    List<SpringBootQuestion> selectByTimeDesc(int offset);

    List<SpringBootQuestion> selectByViewsAsc(int offset);

    List<SpringBootQuestion> selectByViewsDesc(int offset);

    List<SpringBootQuestion> selectByAnswersAsc(int offset);

    List<SpringBootQuestion> selectByAnswersDesc(int offset);

    List<SpringBootQuestion> selectByKeyTimeAsc(String key, int offset);

    List<SpringBootQuestion> selectByKeyTimeDesc(String key, int offset);

    List<SpringBootQuestion> selectByKeyViewsAsc(String key, int offset);

    List<SpringBootQuestion> selectByKeyViewsDesc(String key, int offset);

    List<SpringBootQuestion> selectByKeyAnswersAsc(String key, int offset);

    List<SpringBootQuestion> selectByKeyAnswersDesc(String key, int offset);
}
