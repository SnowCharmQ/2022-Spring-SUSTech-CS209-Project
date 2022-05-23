package com.cs209.project.script.Github;

import com.cs209.project.utils.ReadHTML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.*;
import java.net.URL;
import java.util.List;


public class ScriptGithubGuiceStar {
    public static void main(String[] args) throws Exception {
        File f = new File("src/test/java/com/cs209/project/file/GuiceGithubstar.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        long ans = Integer.parseInt(br.readLine());
        br.close();
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        try  {
            for (int i = 1; i < 50; i++) {
                String str = "https://github.com/search?l=Java&o=desc&p="+i+"&q=Guice&s=stars&type=Repositories";
                URL url = new URL(str);
                String urlsource = ReadHTML.getURLSource(url);
                Document d = Jsoup.parseBodyFragment(urlsource);
                List<Element> e = d.getElementsByTag("a")
                        .stream().filter((k) -> k.attr("class").equals("Link--muted")).toList();
                for (Element element : e) {
                    String r = element.text().replace("\"", "").trim();
                    if (r.matches("[0-9]*")) {
                        ans = ans + Long.parseLong(r);
                    } else if (r.contains("k")) {
                        ans = ans + (long)Double.parseDouble(r.replaceAll("k", "")) * 1000;
                    }
                }
                }
        }finally {
            bw.write(ans + "");
            bw.close();
        }
    }
}
