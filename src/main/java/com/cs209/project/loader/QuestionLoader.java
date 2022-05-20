package com.cs209.project.loader;


import com.cs209.project.entity.MyBatisQuestion;
import com.cs209.project.entity.SpringBootQuestion;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;

public class QuestionLoader {
    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static HashSet<SpringBootQuestion> springBootQuestions = new HashSet<>();
    private static HashSet<MyBatisQuestion> mybatisQuestions = new HashSet<>();

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

    private static void loadSpringBoot() {
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/com/cs209/project/file/SpringBootStackoverflowQuestionDetail.txt")))) {
            String line;
            while ((line = inline.readLine()) != null) {
                String[] content = line.split("\t");
                SpringBootQuestion sqb = new SpringBootQuestion();
                sqb.setQuestion(content[0]);
                sqb.setDate(Date.valueOf(content[1]));
                sqb.setViews(Integer.parseInt(content[2]));
                sqb.setAnswers(Integer.parseInt(content[3]));
                sqb.setHref(content[4]);
                springBootQuestions.add(sqb);
            }
            stmt = con.prepareStatement("insert into springboot_question (question, date, views, answers, href) values(?,?,?,?,?);");
            for (SpringBootQuestion sbq: springBootQuestions) {
                stmt.setString(1, sbq.getQuestion());
                stmt.setDate(2, sbq.getDate());
                stmt.setInt(3, sbq.getViews());
                stmt.setInt(4, sbq.getAnswers());
                stmt.setString(5, sbq.getHref());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadMybatis(){
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/com/cs209/project/file/MybatisStackoverflowQuestionDetail.txt")))) {
            String line;
            while ((line = inline.readLine()) != null){
                String[] content = line.split("\t");
                MyBatisQuestion mq = new MyBatisQuestion();
                mq.setQuestion(content[0]);
                mq.setDate(Date.valueOf(content[1]));
                mq.setViews(Integer.parseInt(content[2]));
                mq.setAnswers(Integer.parseInt(content[3]));
                mq.setHref(content[4]);
                mybatisQuestions.add(mq);
            }
            stmt = con.prepareStatement("insert into mybatis_question (question, date, views, answers, href) values (?,?,?,?,?);");
            for (MyBatisQuestion mq:mybatisQuestions){
                stmt.setString(1, mq.getQuestion());
                stmt.setDate(2, mq.getDate());
                stmt.setInt(3, mq.getViews());
                stmt.setInt(4, mq.getAnswers());
                stmt.setString(5, mq.getHref());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
        }catch (Exception e){
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
            stmt0.execute("truncate table springboot_question, mybatis_question cascade;");
            con.commit();
            stmt0.close();
        }
        loadSpringBoot();
        loadMybatis();
        closeDB();
        System.out.println("LOAD DONE!");
    }
}
