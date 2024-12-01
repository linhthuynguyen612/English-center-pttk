package com.pttk.linh.DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    private String jdbcURL = "jdbc:mysql://localhost/englishcenter";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    public DAO() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
