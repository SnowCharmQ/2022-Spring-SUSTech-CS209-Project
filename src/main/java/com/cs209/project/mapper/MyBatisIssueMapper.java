package com.cs209.project.mapper;

import com.cs209.project.entity.MyBatisIssue;

import java.util.List;

public interface MyBatisIssueMapper {
    List<MyBatisIssue> selectIssue();
}
