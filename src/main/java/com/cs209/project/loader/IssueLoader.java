package com.cs209.project.loader;

import com.cs209.project.entity.MyBatisIssue;
import com.cs209.project.entity.SpringBootIssue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;

public class IssueLoader {
    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static HashSet<SpringBootIssue> openIssues = new HashSet<>();
    private static HashSet<SpringBootIssue> closedIssues = new HashSet<>();
    private static HashSet<MyBatisIssue> issues = new HashSet<>();

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

    private static void loadSpringBootOpenIssue() {
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/com/cs209/project/file/SpringBootOpenIssueDetail.txt")))) {
            String line;
            while ((line = inline.readLine()) != null) {
                String[] content = line.split("\t");
                String version = content[0];
                String date = content[1];
                int index = line.indexOf(date);
                String info = line.substring(index + date.length() + 1);
                String[] dates = date.split(" ");
                int year = Integer.parseInt(dates[2]) - 1900;
                int month = judgeMonth(dates[0]) - 1;
                int day = Integer.parseInt(dates[1].substring(0, dates[1].length() - 1));
                Date d = new Date(year, month, day);
                openIssues.add(new SpringBootIssue(version, d, year + 1900, month + 1, info));
            }
            stmt = con.prepareStatement("insert into springboot_open_issue (version, publish_date, year, month, info) values(?,?,?,?,?);");
            for (SpringBootIssue i : openIssues) {
                stmt.setString(1, i.getVersion());
                stmt.setDate(2, i.getPublishDate());
                stmt.setInt(3, i.getYear());
                stmt.setInt(4, i.getMonth());
                stmt.setString(5, i.getInfo());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadSpringBootClosedIssue() {
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/com/cs209/project/file/SpringBootClosedIssueDetail.txt")))) {
            String line;
            while ((line = inline.readLine()) != null) {
                String[] content = line.split("\t");
                String version = content[0];
                String date = content[1];
                int index = line.indexOf(date);
                String info = line.substring(index + date.length() + 1);
                String[] dates = date.split(" ");
                int year = Integer.parseInt(dates[2]) - 1900;
                int month = judgeMonth(dates[0]) - 1;
                int day = Integer.parseInt(dates[1].substring(0, dates[1].length() - 1));
                Date d = new Date(year, month, day);
                closedIssues.add(new SpringBootIssue(version, d, year + 1900, month + 1, info));
            }
            stmt = con.prepareStatement("insert into springboot_closed_issue (version, publish_date, year, month, info) values(?,?,?,?,?);");
            for (SpringBootIssue i : closedIssues) {
                stmt.setString(1, i.getVersion());
                stmt.setDate(2, i.getPublishDate());
                stmt.setInt(3, i.getYear());
                stmt.setInt(4, i.getMonth());
                stmt.setString(5, i.getInfo());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadMyBatisIssue() {
        try {
            BufferedReader in1 = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/com/cs209/project/file/MybatisOpenIssueDetail.txt")));
            BufferedReader in2 = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/com/cs209/project/file/MybatisClosedIssueDetail.txt")));
            String line;
            while ((line = in1.readLine()) != null) {
                String[] content = line.split("\t");
                String version = content[0];
                String date = content[1];
                int index = line.indexOf(date);
                String info = line.substring(index + date.length() + 1);
                String[] dates = date.split(" ");
                int year = Integer.parseInt(dates[2]) - 1900;
                int month = judgeMonth(dates[0]) - 1;
                int day = Integer.parseInt(dates[1].substring(0, dates[1].length() - 1));
                Date d = new Date(year, month, day);
                issues.add(new MyBatisIssue(version, d, year + 1900, info));
            }
            while ((line = in2.readLine()) != null) {
                String[] content = line.split("\t");
                String version = content[0];
                String date = content[1];
                int index = line.indexOf(date);
                String info = line.substring(index + date.length() + 1);
                String[] dates = date.split(" ");
                int year = Integer.parseInt(dates[2]) - 1900;
                int month = judgeMonth(dates[0]) - 1;
                int day = Integer.parseInt(dates[1].substring(0, dates[1].length() - 1));
                Date d = new Date(year, month, day);
                issues.add(new MyBatisIssue(version, d, year + 1900, info));
            }
            stmt = con.prepareStatement("insert into mybatis_issue (version, date, year, info) values (?,?,?,?);");
            for (MyBatisIssue mbi : issues) {
                stmt.setString(1, mbi.getVersion());
                stmt.setDate(2, mbi.getDate());
                stmt.setInt(3, mbi.getYear());
                stmt.setString(4, mbi.getInfo());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int judgeMonth(String month) {
        return switch (month) {
            case "Jan" -> 1;
            case "Feb" -> 2;
            case "Mar" -> 3;
            case "Apr" -> 4;
            case "May" -> 5;
            case "Jun" -> 6;
            case "Jul" -> 7;
            case "Aug" -> 8;
            case "Sep" -> 9;
            case "Oct" -> 10;
            case "Nov" -> 11;
            case "Dec" -> 12;
            default -> 0;
        };
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
            stmt0.execute("truncate table springboot_open_issue, springboot_closed_issue, mybatis_issue cascade;");
            con.commit();
            stmt0.close();
        }
        loadSpringBootOpenIssue();
        loadSpringBootClosedIssue();
        loadMyBatisIssue();
        closeDB();
        System.out.println("LOAD DONE!");
    }
}
