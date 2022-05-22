package com.cs209.project.loader;


import com.cs209.project.entity.IssueWord;
import com.cs209.project.entity.SpringBootQuestion;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class WordLoader {
    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static HashMap<IssueWord, Integer> map = new HashMap<>();

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {//find the driver
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {//connect
            con = DriverManager.getConnection(url, props);
            if (con != null) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            assert con != null;
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    public static void loadIssueWord() {
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/com/cs209/project/file/SpringBootIssueNLP.txt")))) {
            String line;
            while ((line = inline.readLine()) != null) {
                String[] content = line.split("\t");
                if (content[0].length() <= 1) continue;
                IssueWord iw = new IssueWord(content[0]);
                Integer temp;
                if (map.containsKey(iw)) {
                    temp = map.get(iw);
                    temp = temp + Integer.parseInt(content[1]);
                } else temp = Integer.parseInt(content[1]);
                map.put(iw, temp);
            }
            stmt = con.prepareStatement("insert into issue_word (word, count) values (?,?);");
            Set<Map.Entry<IssueWord, Integer>> set = map.entrySet();
            for (Map.Entry<IssueWord, Integer> entry : set) {
                IssueWord iw = entry.getKey();
                int count = entry.getValue();
                stmt.setString(1, iw.getWord());
                stmt.setInt(2, count);
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "test");
        defprop.put("password", "123456");
        defprop.put("database", "project");
        Properties prop = new Properties(defprop);
        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));
        Statement stmt0;
        if (con != null) {
            stmt0 = con.createStatement();
            stmt0.execute("truncate table issue_word cascade;");
            con.commit();
            stmt0.close();
        }
        loadIssueWord();
        closeDB();
        System.out.println("LOAD DONE!");
    }
}
