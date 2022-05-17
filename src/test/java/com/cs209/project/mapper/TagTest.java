package com.cs209.project.mapper;

import com.cs209.project.entity.stackoverflow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TagTest {
    @Autowired
    StackOverFlowAllTagMapper StackOverFlowAllTagMapper;

    @Test
    public void insertTest() throws IOException {
        File f = new File("src/test/java/com/cs209/project/file/2002_5_9stackoverflowtag.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String s;
        while((s = br.readLine())!=null){
            stackoverflow t = new stackoverflow();
            String[]parts = s.split("\\s+");
            t.setTag(parts[0]);
            t.setAllQuestion(Integer.parseInt(parts[1]));
            for(int i = 3;i<parts.length;i++){
                if(parts[i].equals("today"))
                    t.setToday(Integer.parseInt(parts[i - 2]));
                if(parts[i].equals("year"))
                    if(parts[i - 3].matches("[0-9]*")){
                        t.setYear(Integer.parseInt(parts[i - 3]));
                    }
                    else{
                        t.setYear(Integer.parseInt(parts[i - 2]));
                    }
                if(parts[i].equals("week"))
                    t.setWeek(Integer.parseInt(parts[i - 2]));
                if(parts[i].equals("month"))
                    t.setMonth(Integer.parseInt(parts[i - 3]));
            }
            StackOverFlowAllTagMapper.insert(t);
        }
    }
}
