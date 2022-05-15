package com.cs209.project.script;

import com.cs209.project.utils.ReadHTML;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;

public class ScriptStackoverflowSpringbootQuestion {
    public static void main(String []args) throws Exception {
        String str = "https://api.stackexchange.com/2.3/tags/spring-boot/faq?page=25&pagesize=100&site=stackoverflow";
        URL url = new URL(str);
        String urlsource = ReadHTML.getURLSource(url);
        Document d = Jsoup.parseBodyFragment(urlsource);
        JSONObject json = JSONObject.fromObject(d.getElementsByTag("pre").get(0).text());
        JSONArray json_array = JSONArray.fromObject(json.getString("items"));
        for(int i = 0;i<json_array.size();i++){
            JSONObject t = (JSONObject)json_array.get(i);
            System.out.println(t.getString("title"));
        }
    }
}
