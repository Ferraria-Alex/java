package com.alexandre.Java_Sql_CDA.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    static final String DB_URL = "jdbc:mysql://localhost/java";
    static final String USERNAME = "root";
    static final String PASSWORD = "";

    private static Connection connexion;
    static {
        try {
            connexion = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection get_connection(){
        return connexion;
    }
}
