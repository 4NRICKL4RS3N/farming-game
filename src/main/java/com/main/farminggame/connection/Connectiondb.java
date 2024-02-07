package com.main.farminggame.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connectiondb {
    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://roundhouse.proxy.rlwy.net:55916/railway", "postgres", "ADEbbB15G-2*1A-DeaA6G52EeagGg1B1");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
