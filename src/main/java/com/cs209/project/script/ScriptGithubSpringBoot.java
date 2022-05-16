package com.cs209.project.script;

import com.cs209.project.entity.SpringBootIteration;
import com.cs209.project.utils.ReadHTML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class ScriptGithubSpringBoot {
    public static void main(String []args) throws Exception {
        String s = "https://github.com/spring-projects/spring-boot/milestones?state=closed";
        URL url = new URL(s);
        String urlsource = ReadHTML.getURLSource(url);
        Document d = Jsoup.parseBodyFragment(urlsource);
        Elements e = d.getElementsByTag("li");
        ArrayList<SpringBootIteration> temp = new ArrayList<>();
        for(Element w : e){
            if(w.attr("class").equals("table-list-item milestone")){
                if(!Objects.equals(w.getElementsByTag("relative-time").attr("datetime"), "") && w.getElementsByTag("h2").text().matches("[0-9.]*")){
                    SpringBootIteration r = new SpringBootIteration();
                    r.setVersion(w.getElementsByTag("h2").text());
                    r.setTime(w.getElementsByTag("relative-time").attr("datetime"));
                    temp.add(r);
                }
            }

        }
        s = "https://github.com/spring-projects/spring-boot/milestones?state=open";
        URL lru = new URL(s);
        String lrusource = ReadHTML.getURLSource(lru);
        Document dd = Jsoup.parseBodyFragment(lrusource);
        Elements ee = dd.getElementsByTag("li");
        for(Element w : ee){
            if(w.attr("class").equals("table-list-item milestone")){
                if(!Objects.equals(w.getElementsByTag("relative-time").attr("datetime"), "") && w.getElementsByTag("h2").text().matches("[0-9.]*")){
                    SpringBootIteration r = new SpringBootIteration();
                    r.setVersion(w.getElementsByTag("h2").text());
                    r.setTime(w.getElementsByTag("relative-time").attr("datetime"));
                    temp.add(r);
                }
            }
        }
        temp.sort((e1, e2)->{
            String []a = e1.getVersion().split("\\.");
            String []b = e2.getVersion().split("\\.");
            int c = 0;
            for(int i = 0;i<a.length;i++){
                if(Integer.parseInt(a[i])>Integer.parseInt(b[i])){
                    c = 1;
                    break;
                }else if(Integer.parseInt(a[i])<Integer.parseInt(b[i])){
                    c = -1;
                    break;
                }
            }
            return c;
        });
        File f = new File("src/test/java/com/cs209/project/file/SpringBootIteration.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        for(SpringBootIteration t : temp){
            bw.write(t.getTime() + "\n");
            bw.write(t.getVersion() + "\n");
        }
        bw.close();
    }
}
