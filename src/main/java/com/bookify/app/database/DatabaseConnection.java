package com.bookify.app.database;

// DatabaseConnection – Singleton-ish utility for SQLite.
// Keeps a single Connection open for whole app.

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:travel_agency.db";
    private static Connection connection;

    static {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
                // Set auto-commit to true
                connection.setAutoCommit(true);
                System.out.println("Database connection established successfully");

                // Initialize database tables
                initializeDatabase();
            } catch (SQLException e) {
                System.err.println("Database connection error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // Check if connection is valid and reconnect if needed
            try {
                if (connection.isClosed()) {
                    connection = DriverManager.getConnection(DB_URL);
                    connection.setAutoCommit(true);
                    System.out.println("Database connection re-established");
                }
            } catch (SQLException e) {
                System.err.println("Error checking database connection: " + e.getMessage());
                e.printStackTrace();
                // Try to create a new connection
                try {
                    connection = DriverManager.getConnection(DB_URL);
                    connection.setAutoCommit(true);
                    System.out.println("Database connection re-established after error");
                } catch (SQLException ex) {
                    System.err.println("Failed to reconnect to database: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void initializeDatabase() {
        try {
            // Load and execute create tables SQL
            InputStream inputStream = DatabaseConnection.class.getResourceAsStream("/create_tables.sql");
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sqlBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sqlBuilder.append(line).append("\n");
                }
                reader.close();

                // Execute the SQL
                Statement stmt = connection.createStatement();
                String[] statements = sqlBuilder.toString().split(";");
                for (String sql : statements) {
                    sql = sql.trim();
                    if (!sql.isEmpty()) {
                        stmt.executeUpdate(sql);
                    }
                }
                stmt.close();

                System.out.println("Database tables initialized successfully");
            }
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}