package com.cs209.project.script.Github;

import com.cs209.project.entity.SpringBootDetail;
import com.cs209.project.utils.ReadHTML;
import org.apache.el.stream.Stream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;

public class ScriptGithubSpringBootDetail {
    public static void main(String []args) throws Exception {
        File f = new File("src/test/java/com/cs209/project/file/SpringBootIteration3.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        try{
            for(int i = 1;i<=998;i++){
                String s = "https://github.com/spring-projects/spring-boot/issues?page="+i+"&q=is%3Aissue+is%3Aclosed";
                URL url = new URL(s);
                String urlsource = ReadHTML.getURLSource(url);
                Document d = Jsoup.parseBodyFragment(urlsource);
                Elements e = d.getElementsByTag("div");
                ArrayList<Element> temp = new ArrayList<>();
                for(Element w : e){
                    if(w.attr("id").matches("issue_[0-9]*")){
                        temp.add(w);
                    }
                }
                for(int j = 0;j<temp.size();j++){
                    SpringBootDetail sbd = new SpringBootDetail();
                    sbd.setTime(temp.get(j).getElementsByTag("relative-time").get(0).attr("datetime"));
                    System.out.println(sbd.getTime());
                    Elements w = temp.get(j).getElementsByTag("span");
                    for(int k = 0;k<w.size();k++){
                        if(w.get(k).attr("class").equals("css-truncate-target")){
                            sbd.setVersion(w.get(k).text());
                        }
                    }
                    sbd.setId(temp.get(j).id().split("_")[1]);
                    Elements m = temp.get(j).getElementsByTag("a");
                    ArrayList<String> r = new ArrayList<>();
                    for(int k = 0;k<m.size();k++){
                        if(m.get(k).id().matches("label-[0-9a-z]*")){
                            r.add(m.get(k).attr("data-name"));
                        }
                    }
                    sbd.setTag(r);
                    bw.write(sbd.getId() + "\t" + sbd.getVersion() + "\t" + sbd.getTime() + "\t" + sbd.getTag().toString() + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println(1);
            bw.close();
        }


    }
}
