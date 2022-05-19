package com.cs209.project.script;

import java.io.*;

public class ScriptcorrectStackoverflow {
    public static void main(String []args) throws IOException {
        File f = new File("src/test/java/com/cs209/project/file/SpringBootStackoverflowQuestionDetail.txt");
        File ff = new File("src/test/java/com/cs209/project/file/correct.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(ff));
        String s;
        BufferedReader br = new BufferedReader(new FileReader(f));
        while((s = br.readLine())!=null){
            s = s.replaceAll("&#39;", "'");
            s = s.replaceAll("amp;", "");
            s = s.replaceAll("&quot;", "\"");
            s = s.replaceAll("&gt;", ">");
            s = s.replaceAll("&lt;", "<");
            bw.write(s + "\n");
            if(s.contains("&#39;"))
                System.out.println(s);
        }
    }
}
