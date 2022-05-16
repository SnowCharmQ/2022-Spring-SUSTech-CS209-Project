package com.cs209.project.service;

import com.cs209.project.entity.SpringBootIssueVersion;
import com.cs209.project.entity.SpringBootIteration;

import java.util.List;

public interface ISpringBootService {
    List<SpringBootIteration> selectVersion();
    List<SpringBootIssueVersion> selectOpenIssueVersion();
    List<SpringBootIssueVersion> selectClosedIssueVersion();

}
