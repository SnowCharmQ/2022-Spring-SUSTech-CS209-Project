package com.cs209.project.mapper;

import com.cs209.project.entity.IssueWord;
import com.cs209.project.entity.QuestionWord;

import java.util.List;

public interface WordMapper {
    List<IssueWord> selectIssueWord();

    List<QuestionWord> selectQuestionWord();
}
