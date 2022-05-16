package com.cs209.project.mapper;

import com.cs209.project.entity.SpringBootIssueVersion;

import java.util.List;

public interface SpringBootIssueMapper {
    List<SpringBootIssueVersion> selectClosedIssue();

    List<SpringBootIssueVersion> selectOpenIssue();

}
