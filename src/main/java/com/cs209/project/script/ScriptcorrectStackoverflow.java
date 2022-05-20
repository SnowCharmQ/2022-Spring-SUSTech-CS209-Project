package com.cs209.project.script;

import java.io.*;

public class ScriptcorrectStackoverflow {
    public static void main(String []args) throws IOException {
        File f = new File("src/test/java/com/cs209/project/file/MybatisStackoveflowQuestionDetail.txt");
        File ff = new File("src/test/java/com/cs209/project/file/MybaitsCorrect.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(ff));
        String s;
        BufferedReader br = new BufferedReader(new FileReader(f));
        while((s = br.readLine())!=null){
            s = s.replaceAll("&#39;", "'");
            s = s.replaceAll("&amp;", "");
            s = s.replaceAll("&quot;", "\"");
            s = s.replaceAll("&gt;", ">");
            s = s.replaceAll("&lt;", "<");
            s = s.replaceAll("&#160;", "");
            if(s.contains("&#39;")){
                s = s.replaceAll("&#39;", "'");
                bw.write(s + "\n");
                System.out.println(s);
            }else if (s.contains("&quot;")){
                s = s.replaceAll("&quot;", "\"");
                bw.write(s + "\n");
                System.out.println(s);
            }else if (s.contains("&amp;")){
                s = s.replaceAll("&amp;", "");
                bw.write(s + "\n");
                System.out.println(s);
            }else if (s.contains("&#180;")){
                s = s.replaceAll("&#180;", "'");
                bw.write(s + "\n");
                System.out.println(s);
            }else if (s.contains("#39;")){
                s = s.replaceAll("#39;", "'");
                bw.write(s + "\n");
                System.out.println(s);
            }else
                bw.write(s + "\n");


        }
//        BufferedReader bb = new BufferedReader(new FileReader(ff));
//        while((s = bb.readLine())!=null){
//            if(s.contains("&gt;"))
//                System.out.println(s);
//        }
    }
}
