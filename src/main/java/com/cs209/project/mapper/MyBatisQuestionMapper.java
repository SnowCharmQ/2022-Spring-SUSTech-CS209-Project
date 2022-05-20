package com.cs209.project.mapper;

import com.cs209.project.entity.MyBatisQuestion;

import java.util.List;

public interface MyBatisQuestionMapper {
    List<MyBatisQuestion> selectAll();

    List<MyBatisQuestion> select(int offset);

    List<MyBatisQuestion> selectByTimeAsc(int offset);

    List<MyBatisQuestion> selectByTimeDesc(int offset);

    List<MyBatisQuestion> selectByViewsAsc(int offset);

    List<MyBatisQuestion> selectByViewsDesc(int offset);

    List<MyBatisQuestion> selectByAnswersAsc(int offset);

    List<MyBatisQuestion> selectByAnswersDesc(int offset);

    List<MyBatisQuestion> selectByKey(String key, int offset);

    List<MyBatisQuestion> selectByKeyTimeAsc(String key, int offset);

    List<MyBatisQuestion> selectByKeyTimeDesc(String key, int offset);

    List<MyBatisQuestion> selectByKeyViewsAsc(String key, int offset);

    List<MyBatisQuestion> selectByKeyViewsDesc(String key, int offset);

    List<MyBatisQuestion> selectByKeyAnswersAsc(String key, int offset);

    List<MyBatisQuestion> selectByKeyAnswersDesc(String key, int offset);
}
