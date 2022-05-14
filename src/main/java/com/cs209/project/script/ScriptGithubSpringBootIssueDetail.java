package com.cs209.project.script;

import com.cs209.project.utils.ReadHTML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ScriptGithubSpringBootIssueDetail {
    public static void main(String[] args) throws Exception {
        File f = new File("src/test/java/com/cs209/project/file/SpringBootIssueDetail.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String ans = "";
        String s;
        while ((s = br.readLine()) != null) {
            ans = ans + s + "\n";
        }
        br.close();
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        try (bw) {
            bw.write(ans);
            for (int i = 965; i < 1002; i++) {
                /**
                 * TvT被github给ban了
                 * 爬到这页
                 */
                String str = "https://github.com/spring-projects/spring-boot/issues?page=" + i + "&q=is%3Aissue+is%3Aclosed";
                URL url = new URL(str);
                String urlsource = ReadHTML.getURLSource(url);
                Document d = Jsoup.parseBodyFragment(urlsource);
                Supplier<Stream<Element>> te = () -> d.getElementsByTag("div").stream().filter((r) -> r.attr("id").startsWith("issue"));
                Stream<Element> Atemp = te.get().flatMap((t) -> t.getElementsByTag("a").stream()
                        .filter((e) -> e.attr("id").startsWith("issue_")));
                List<String> context = Atemp.map(Element::text).toList();
                List<String> time = te.get().flatMap((t) -> t.getElementsByTag("relative-time").stream()).map(Element::text).toList();
                List<String> label = te.get().map((t) -> t.getElementsByTag("a").stream()
                        .filter((e) -> e.attr("id").startsWith("label-"))
                        .map(Element::text)
                        .reduce((a, b) -> {
                            System.out.println(a);
                            System.out.println(b);
                            return a +"\t"+ b;})
                        .orElse("null")
                ).toList();
                List<Element> temp = te.get().toList();
                for (int j = 0; j < temp.size(); j++) {
                    String ta = "";
                    Elements eee = temp.get(j).getElementsByTag("span");
                    boolean exits = false;
                    for (Element w : eee) {
                        if (w.attr("class").equals("css-truncate-target")) {
                            exits = true;
//                            System.out.println(w.text());
                            ta = ta + w.text() + "\t";
                        }
                    }
                    if (!exits) {
//                        System.out.println("null");
                        ta = ta + "null" + "\t";
                    }
                    ta = ta + time.get(j) + "\t" + context.get(j) + "\t\t" + label.get(j) + "\n";
                    bw.write(ta);
                }
            }
        }
    }
}