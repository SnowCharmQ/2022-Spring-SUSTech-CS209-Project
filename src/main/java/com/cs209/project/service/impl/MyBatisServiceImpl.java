package com.cs209.project.service.impl;

import com.cs209.project.entity.MyBatisQuestion;
import com.cs209.project.mapper.MyBatisQuestionMapper;
import com.cs209.project.service.IMyBatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MyBatisServiceImpl implements IMyBatisService {
    @Autowired
    private MyBatisQuestionMapper myBatisQuestionMapper;

    @Override
    public List<MyBatisQuestion> selectMyBatisQuestion(String sorting, String key, String page) {
        int offset = (!Objects.equals(page, "")) ? (Integer.parseInt(page) - 1) * 5 : 0;
        if (Objects.equals(key, ""))
            return switch (sorting) {
                case "0" -> myBatisQuestionMapper.select(offset);
                case "1" -> myBatisQuestionMapper.selectByTimeAsc(offset);
                case "2" -> myBatisQuestionMapper.selectByTimeDesc(offset);
                case "3" -> myBatisQuestionMapper.selectByViewsAsc(offset);
                case "4" -> myBatisQuestionMapper.selectByViewsDesc(offset);
                case "5" -> myBatisQuestionMapper.selectByAnswersAsc(offset);
                case "6" -> myBatisQuestionMapper.selectByAnswersDesc(offset);
                default -> new ArrayList<>();
            };
        else {
            key = key.toUpperCase();
            return switch (sorting) {
                case "0" -> myBatisQuestionMapper.selectByKey(key, offset);
                case "1" -> myBatisQuestionMapper.selectByKeyTimeAsc(key, offset);
                case "2" -> myBatisQuestionMapper.selectByKeyTimeDesc(key, offset);
                case "3" -> myBatisQuestionMapper.selectByKeyViewsAsc(key, offset);
                case "4" -> myBatisQuestionMapper.selectByKeyViewsDesc(key, offset);
                case "5" -> myBatisQuestionMapper.selectByKeyAnswersAsc(key, offset);
                case "6" -> myBatisQuestionMapper.selectByKeyAnswersDesc(key, offset);
                default -> new ArrayList<>();
            };
        }
    }

    @Override
    public List<MyBatisQuestion> selectAllMyBatisQuestion() {
        return myBatisQuestionMapper.selectAll();
    }
}
