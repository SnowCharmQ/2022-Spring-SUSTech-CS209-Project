package com.cs209.project.service;

import com.cs209.project.entity.*;

import java.util.List;

public interface ISpringBootService {
    List<SpringBootIteration> selectVersion();

    List<SpringBootIssueVersion> selectOpenIssueVersion();

    List<SpringBootIssueVersion> selectClosedIssueVersion();

    List<SpringBootQuestion> selectSpringBootQuestion(String sorting, String key, String page);

    List<SpringBootQuestion> selectAllSpringBootQuestion();

    List<IssueWord> selectIssueWord();

    List<QuestionWord> selectQuestionWord();
}
