package com.cs209.project.loader;


import com.cs209.project.entity.SpringBootIteration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;

public class IterationLoader {
    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static HashSet<SpringBootIteration> springBootIterations = new HashSet<>();

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
        try (BufferedReader inline = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/java/com/cs209/project/file/SpringBootIteration.txt")))) {
            String line;
            while ((line = inline.readLine()) != null) {
                String time = line.substring(0, 10);
                String version = inline.readLine().trim();
                String[] content = time.split("-");
                int year = Integer.parseInt(content[0]);
                int month = Integer.parseInt(content[1]);
                int day = Integer.parseInt(content[2]);
                SpringBootIteration sbi = new SpringBootIteration();
                sbi.setTime(time);
                sbi.setVersion(version);
                sbi.setYear(year);
                sbi.setMonth(month);
                sbi.setDay(day);
                springBootIterations.add(sbi);
            }
            stmt = con.prepareStatement("insert into springboot_iteration (version, year, month, day) values(?,?,?,?);");
            for (SpringBootIteration sbi : springBootIterations) {
                stmt.setString(1, sbi.getVersion());
                stmt.setInt(2, sbi.getYear());
                stmt.setInt(3, sbi.getMonth());
                stmt.setInt(4, sbi.getDay());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.clearBatch();
            con.commit();
        } catch (Exception e) {
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
            stmt0.execute("truncate table springboot_iteration cascade;");
            con.commit();
            stmt0.close();
        }
        loadSpringBoot();
        closeDB();
        System.out.println("LOAD DONE!");
    }
}
