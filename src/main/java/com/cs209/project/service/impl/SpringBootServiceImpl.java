package com.cs209.project.service.impl;

import com.cs209.project.entity.SpringBootIssueVersion;
import com.cs209.project.entity.SpringBootIteration;
import com.cs209.project.entity.SpringBootQuestion;
import com.cs209.project.mapper.SpringBootIssueMapper;
import com.cs209.project.mapper.SpringBootIterMapper;
import com.cs209.project.mapper.SpringBootQuestionMapper;
import com.cs209.project.service.ISpringBootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SpringBootServiceImpl implements ISpringBootService {
    @Autowired
    private SpringBootIterMapper springBootIterMapper;
    @Autowired
    private SpringBootIssueMapper springBootIssueMapper;
    @Autowired
    private SpringBootQuestionMapper springBootQuestionMapper;

    @Override
    public List<SpringBootIteration> selectVersion() {
        return springBootIterMapper.selectIter();
    }

    @Override
    public List<SpringBootIssueVersion> selectOpenIssueVersion() {
        return springBootIssueMapper.selectOpenIssue();
    }

    @Override
    public List<SpringBootIssueVersion> selectClosedIssueVersion() {
        return springBootIssueMapper.selectClosedIssue();
    }

    @Override
    public List<SpringBootQuestion> selectSpringBootQuestion(String sorting, String key, String page) {
        int offset = (!Objects.equals(page, "")) ? (Integer.parseInt(page) - 1) * 5 : 0;
        if (Objects.equals(key, ""))
            return switch (sorting) {
                case "0" -> springBootQuestionMapper.select(offset);
                case "1" -> springBootQuestionMapper.selectByTimeAsc(offset);
                case "2" -> springBootQuestionMapper.selectByTimeDesc(offset);
                case "3" -> springBootQuestionMapper.selectByViewsAsc(offset);
                case "4" -> springBootQuestionMapper.selectByViewsDesc(offset);
                case "5" -> springBootQuestionMapper.selectByAnswersAsc(offset);
                case "6" -> springBootQuestionMapper.selectByAnswersDesc(offset);
                default -> new ArrayList<>();
            };
        else {
            key = key.toUpperCase();
            return switch (sorting) {
                case "0" -> springBootQuestionMapper.selectByKey(key, offset);
                case "1" -> springBootQuestionMapper.selectByKeyTimeAsc(key, offset);
                case "2" -> springBootQuestionMapper.selectByKeyTimeDesc(key, offset);
                case "3" -> springBootQuestionMapper.selectByKeyViewsAsc(key, offset);
                case "4" -> springBootQuestionMapper.selectByKeyViewsDesc(key, offset);
                case "5" -> springBootQuestionMapper.selectByKeyAnswersAsc(key, offset);
                case "6" -> springBootQuestionMapper.selectByKeyAnswersDesc(key, offset);
                default -> new ArrayList<>();
            };
        }
    }

    @Override
    public List<SpringBootQuestion> selectAllSpringBootQuestion() {
        return springBootQuestionMapper.selectAll();
    }

}
