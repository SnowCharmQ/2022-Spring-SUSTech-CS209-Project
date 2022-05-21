package com.cs209.project.controller;

import com.cs209.project.entity.MyBatisIssue;
import com.cs209.project.entity.MyBatisQuestion;
import com.cs209.project.service.IMyBatisService;
import com.cs209.project.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("mybatis")
public class MyBatisController extends BaseController {
    @Autowired
    private IMyBatisService iMyBatisService;

    @RequestMapping("mybatis-issue")
    public JsonResult<List<MyBatisIssue>> getIssue() {
        List<MyBatisIssue> list = iMyBatisService.selectMyBatisIssue();
        return new JsonResult<>(ok, list);
    }

    @RequestMapping("mybatis-question")
    public JsonResult<List<MyBatisQuestion>> getQuestion(String sorting, String key, String page) {
        List<MyBatisQuestion> list = iMyBatisService.selectMyBatisQuestion(sorting, key, page);
        if (list == null) return new JsonResult<>(220);
        else if (list.isEmpty()) return new JsonResult<>(220);
        return new JsonResult<>(ok, list);
    }

    @RequestMapping("mybatis-select")
    public JsonResult<List<MyBatisQuestion>> getAllQuestion() {
        List<MyBatisQuestion> list = iMyBatisService.selectAllMyBatisQuestion();
        return new JsonResult<>(ok, list);
    }
}
