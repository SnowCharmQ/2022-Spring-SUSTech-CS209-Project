package com.cs209.project.script.Github;

import com.cs209.project.entity.SpringBootBug;
import com.cs209.project.mapper.SpringBootBugMapper;
import com.cs209.project.utils.ReadHTML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class ScriptGithubSpringBootBug {
    public static void main(String []args) throws Exception {
        File f = new File("C:\\Users\\HP\\Desktop\\java2_proj\\src\\test\\java\\com\\cs209\\project\\file\\SpringBootBug.txt");
        BufferedWriter br = new BufferedWriter(new FileWriter(f));
        try {
            for (int i = 1; i <= 110; i++) {
                String s = "https://github.com/spring-projects/spring-boot/issues?page=" + i + "&q=is%3Aissue+label%3A%22type%3A+bug%22+is%3Aclosed";
                URL url = new URL(s);
                String urlsource = ReadHTML.getURLSource(url);
                Document d = Jsoup.parseBodyFragment(urlsource);
                Elements e = d.getElementsByTag("div");
                ArrayList<Element> temp = new ArrayList<>();
                for (Element w : e) {
                    if (w.attr("id").startsWith("issue")) {
                        temp.add(w);
                    }
                }
                for (int j = 0; j < temp.size(); j++) {
                    SpringBootBug n = new SpringBootBug();
                    Elements ee = temp.get(j).getElementsByTag("a");
                    Elements eee = temp.get(j).getElementsByTag("span");
                    for (Element p : ee) {
                        if (p.attr("id").startsWith("issue_")) {
                            n.setBug(p.text());
                            break;
                        }
                    }
                    for (Element p : eee) {
                        if (p.attr("class").equals("css-truncate-target")){
                            n.setVersion(p.text());
                            break;
                        }
                    }
                    System.out.println(n.getBug());
                    br.write(n.getBug() + "\n" + n.getVersion() +"\n");
                }
            }
        }finally {
            System.out.println(1);
            br.close();
        }



    }


}
