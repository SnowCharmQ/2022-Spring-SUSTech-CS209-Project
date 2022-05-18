package com.cs209.project.service.impl;

import com.cs209.project.entity.SpringBootIssueVersion;
import com.cs209.project.entity.SpringBootIteration;
import com.cs209.project.mapper.SpringBootIssueMapper;
import com.cs209.project.mapper.SpringBootIterMapper;
import com.cs209.project.service.ISpringBootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringBootServiceImpl implements ISpringBootService {
    @Autowired
    private SpringBootIterMapper springBootIterMapper;
    @Autowired
    private SpringBootIssueMapper springBootIssueMapper;


    @Override
    public List<SpringBootIteration> selectVersion() {
        return springBootIterMapper.selectIter();
    }

    @Override
    public List<SpringBootIssueVersion> selectOpenIssueVersion(){
        return springBootIssueMapper.selectOpenIssue();
    }

    @Override
    public List<SpringBootIssueVersion> selectClosedIssueVersion() {
        return springBootIssueMapper.selectClosedIssue();
    }

}
