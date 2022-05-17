//package com.cs209.project.script;
//
//import com.cs209.project.utils.ReadHTML;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//
//import java.io.*;
//import java.net.URL;
//import java.util.List;
//import java.util.function.Supplier;
//import java.util.stream.Stream;
//
//public class ScriptStackoverflowSpringBootDetail {
//    public static void main(String []args) throws Exception {
//        File f = new File("C:\\Users\\HP\\Desktop\\java2_proj\\src\\test\\java\\com\\cs209\\project\\file\\SpringBootQuestion.txt");
//        BufferedReader br = new BufferedReader(new FileReader(f));
//        String ans = "";
//        String s;
//        while ((s = br.readLine()) != null) {
//            ans = ans + s + "\n";
//        }
//        br.close();
//        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
//        try (bw) {
//            bw.write(ans);
//            for(int i = 1;i<1433;i++){
//                String str = "https://stackoverflow.com/search?page="+i+"&tab=Relevance&q=springboot";
//                URL url = new URL(str);
//                String urlsource = ReadHTML.getURLSource(url);
//                Document d = Jsoup.parseBodyFragment(urlsource);
//                Supplier<Stream<Element>> te = () -> d.getElementsByTag("div").stream()
//                        .filter((r) -> r.attr("id").startsWith("question-summary"));
//                List<String> votes = te.get().flatMap((e)->e.getElementsByTag("div").stream()
//                        .filter((r)->r.attr("class").equals("s-post-summary--stats-item s-post-summary--stats-item__emphasized"))
//                        .map((y)->y.attr("title").split(" ")[2])).toList();
//                List<String> answers = te.get().flatMap((e)->e.getElementsByTag("div").stream()
//                        .filter((r)->r.attr("class").equals("s-post-summary--stats-item has-answers "))
//                        .map((y)->y.attr("title").split(" ")[0])).toList();
//                List<String> views = te.get().flatMap((e)->e.getElementsByTag("div").stream()
//                        .filter((r)->r.attr("class").equals("s-post-summary--stats-item "))
//                        .map((y)->y.attr("title").split(" ")[0])).toList();
//                List<String> name = te.get().flatMap((e)->e.getElementsByTag("h3").stream()
//                        .flatMap((y)->y.getElementsByTag("a").stream()
//                                .map(Element::text))).toList();
//                List<String> time = te.get().flatMap((e)->e.getElementsByTag("div").stream()
//                        .filter((y)->y.attr("class").equals("s-user-card s-user-card__minimal"))
//                        .flatMap((r)->r.getElementsByTag("time").stream())
//                        .flatMap((z)->z.getElementsByTag("span").stream()
//                                .map(Element::text))).toList();
//                for(int j = 0;j< votes.size();j++){
//                    bw.write(votes.get(j) + "\t" + answers.get(j) + "\t"
//                        + views.get(j) + "\t" + name.get(j) +"\t" + time.get(j) + "\n");
//                    System.out.println(name.get(j));
//                }
//            }
//        }
//    }
//}
