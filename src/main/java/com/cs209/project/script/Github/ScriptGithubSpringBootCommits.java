package com.cs209.project.script.Github;

import com.cs209.project.utils.ReadHTML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


public class ScriptGithubSpringBootCommits {
    public static void main(String []args) throws IOException {
        File f = new File("src/test/java/com/cs209/project/file/GithubSpringBootCommits.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String c = br.readLine();
        String []k = c.split("\\s+");
        HashMap<Integer, Integer> ans = new HashMap<>();
        for(int i = 0;i<11;i++){
            ans.put(2012 + i, Integer.parseInt(k[i]));
        }
        try{
            for(int i = 1195;i<38083;i++){
                String s = "https://github.com/spring-projects/spring-boot/commits/main?after=68bd9d6b6d38631d1d748c087e522439e9e867f0+"+i+"&branch=main&qualified_name=refs%2Fheads%2Fmain";
                URL url = new URL(s);
                System.out.println(i);
                String urlsource = ReadHTML.getURLSource(url);
                Document d = Jsoup.parseBodyFragment(urlsource);
                String temp = d.getElementsByTag("div").stream()
                        .filter((e) -> e.attr("class").equals("TimelineItem TimelineItem--condensed pt-0 pb-2"))
                        .map((e) -> e.getElementsByTag("h2").stream()
                                .map(Element::text)
                                .map((t)->t.split(",")[1].trim()).reduce((a, b)->a + b).orElse("null"))
                        .toList().get(0);
                int o = Integer.parseInt(temp);
                System.out.println(o);
                ans.replace(o, ans.get(o), ans.get(o) +1);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            String u = "";
            for(int i = 0;i<11;i++){
                u = u + ans.get(2012 + i) +"\t";
            }
            bw.write(u);
            bw.close();
        }
    }
}
