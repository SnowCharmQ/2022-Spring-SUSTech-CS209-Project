package com.cs209.project.scrip;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ScripTag {
    public static void main(String []args) throws Exception {
        Pattern p = Pattern.compile("\\w*java\\w*");
        Pattern q = Pattern.compile("\\w*javascript\\w*");
        for(int k = 1;k<=1861;k++){
            String s = "https://stackoverflow.com/tags?page="+k+"&tab=popular";
            URL url = new URL(s);
            String urlsource = getURLSource(url);
            Document d = Jsoup.parseBodyFragment(urlsource);
            Elements e = d.getElementsByTag("div");
            ArrayList<Element> temp = new ArrayList<>();
            for(Element ee :e){
                if(ee.attr("class").equals("grid--item s-card js-tag-cell d-flex fd-column")){
                    temp.add(ee);
                }
            }
            for(Element element : temp){
                Element a1 = element.child(0);
                if(p.matcher(a1.select("a").text()).matches()&&!q.matcher(a1.select("a").text()).matches()){
                    System.out.print(a1.select("a").text());
                    Element a2 = element.child(2);
                    Element b1 = a2.child(0);
                    System.out.print("\t"+b1.select("div").text());
                    if(a2.childNodeSize()>1){
                        Element b2 = a2.child(1);
                        System.out.println("\t"+b2.select("a").text());
                    }
                }
            }
        }
    }
    public static String getURLSource(URL url) throws Exception    {
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream =  conn.getInputStream();
        byte[] data = readInputStream(inStream);
        String htmlSource = new String(data);
        return htmlSource;
    }

    public static byte[] readInputStream(InputStream instream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[]  buffer = new byte[1204];
        int len = 0;
        while ((len = instream.read(buffer)) != -1){
            outStream.write(buffer,0,len);
        }
        instream.close();
        return outStream.toByteArray();
    }
}
