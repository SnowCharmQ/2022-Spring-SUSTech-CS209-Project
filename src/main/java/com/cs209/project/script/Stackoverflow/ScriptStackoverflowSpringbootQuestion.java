package com.cs209.project.script.Stackoverflow;
import java.io.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;

public class ScriptStackoverflowSpringbootQuestion {
    public static void main(String []args) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        File f = new File("src/test/java/com/cs209/project/file/SpringBootStackoverflowQuestionDetail.txt");
        BufferedReader bt = new BufferedReader(new FileReader(f));
        String ans = "";
        String ss;
        while ((ss = bt.readLine()) != null) {
            ans = ans + ss + "\n";
        }
        bt.close();
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        try (bw) {
            bw.write(ans);
            for (int q = 1; q < 93; q++) {
                String str = "https://api.stackexchange.com/2.3/tags/spring-boot/faq?page=" + q + "&pagesize=100&site=stackoverflow&key=*Q4hDtZ8bVAQZve4zcfkqw((";
                URL url = new URL(str);
                HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
                httpUrlConn.setDoInput(true);
                httpUrlConn.setRequestMethod("GET");
                BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(httpUrlConn.getInputStream()), StandardCharsets.UTF_8));
                StringBuilder s = new StringBuilder();
                String p;
                while ((p = br.readLine()) != null) {
                    s.append(p);
                }
                String j = s.toString();
                JSONObject json = JSONObject.fromObject(j);
                JSONArray json_array = JSONArray.fromObject(json.getString("items"));
                for (Object o : json_array) {
                    JSONObject t = (JSONObject) o;
                    long date_temp = Long.parseLong(t.getString("creation_date"));
                    String date_string = sdf.format(new Date(date_temp * 1000));
                    bw.write(t.getString("title") + "\t" + date_string + "\t" + t.getString("view_count") + "\t" + t.getString("answer_count") + "\t" + t.getString("link")+"\n");
                    System.out.println(date_string);
                    System.out.println(t.getString("title"));
                }
            }
        }
    }
}
