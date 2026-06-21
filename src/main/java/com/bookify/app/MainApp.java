package com.bookify.app;

/*
 * =============================================================
 *  Bookify – Travel Management System (MVC)
 *  Author : Panagiotis Pitsikoulis – panagiotispitsikoulis.gr
 *  This is the main JavaFX Application entry point (View launcher).
 *  It loads the login view (FXML), sets up a global exception handler,
 *  and takes care of establishing / closing the SQLite connection.
 *  Very simple – nothing fancy!
 * =============================================================
 */

import java.io.IOException;

import com.bookify.app.database.DatabaseConnection;
import com.bookify.app.database.SampleDataGenerator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Called by JavaFX runtime. We just set up the first scene.
        // Think of this as the "view" bootstrapper in MVC.
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Uncaught exception in thread: " + thread.getName());
            throwable.printStackTrace();

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "An unexpected error occurred: " + throwable.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
            });
        });

        try {
            System.out.println("Starting application...");

            // Load FXML with more detailed error handling
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/login.fxml"));

            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Bookify - Travel Management System");
                primaryStage.setMaximized(true);
                primaryStage.setResizable(true);
                primaryStage.centerOnScreen();
                primaryStage.show();

                System.out.println("JavaFX application started successfully");

            } catch (IOException ex) {
                System.err.println("Error loading FXML: " + ex.getMessage());
                ex.printStackTrace();

                Platform.runLater(() -> {
                    String errorDetails = "Error: " + ex.getMessage();
                    if (ex.getCause() != null) {
                        errorDetails += "\nCaused by: " + ex.getCause().getMessage();
                    }

                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Failed to load UI: " + errorDetails,
                            ButtonType.OK);
                    alert.showAndWait();
                    Platform.exit();
                });
            }

        } catch (Exception e) {
            System.err.println("Critical application error: " + e.getMessage());
            e.printStackTrace();

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Critical application error: " + e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
                Platform.exit();
            });
        }
    }

    @Override
    public void stop() {
        // This method runs when the window closes – tidy up resources.
        // Close database connection when application closes
        try {
            DatabaseConnection.closeConnection();
            System.out.println("JavaFX application stopped");
        } catch (Exception e) {
            System.err.println("Error while shutting down: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Launch the GUI. Pass --generate-data to fill the DB with demo rows.
        try {
            // Establish database connection
            DatabaseConnection.getConnection();

            // Check if database is empty and load sample data if needed
            if (isDatabaseEmpty()) {
                System.out.println("Database is empty. Loading sample data...");
                SampleDataGenerator.generateSampleDataFromSQL();
            }

            // Also support the --generate-data flag for forcing data regeneration
            if (args.length > 0 && args[0].equals("--generate-data")) {
                System.out.println("Force regenerating sample data...");
                SampleDataGenerator.generateSampleDataFromSQL();
            }

            // Launch JavaFX application
            launch(args);
        } catch (Exception e) {
            System.err.println("Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Check if the database is empty (no customers, destinations, or bookings)
     */
    private static boolean isDatabaseEmpty() {
        try {
            java.sql.Connection conn = DatabaseConnection.getConnection();
            java.sql.Statement stmt = conn.createStatement();

            // Check if customers table has any data
            java.sql.ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM customers");
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }

            // Check if destinations table has any data
            rs = stmt.executeQuery("SELECT COUNT(*) FROM destinations");
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }

            // Check if bookings table has any data
            rs = stmt.executeQuery("SELECT COUNT(*) FROM bookings");
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }

            return true;
        } catch (Exception e) {
            System.err.println("Error checking database: " + e.getMessage());
            return false;
        }
    }
}