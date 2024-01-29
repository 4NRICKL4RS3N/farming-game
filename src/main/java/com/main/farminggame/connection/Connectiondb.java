package com.main.farminggame.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connectiondb {
    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://monorail.proxy.rlwy.net:24952/railway", "postgres", "5EdGFC2*ea-1ag-D4fCa5AF46B4cdAde");
        } catch(Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
