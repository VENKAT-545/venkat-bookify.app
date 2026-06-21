package com.bookify.app.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * -----------------------------------------------------------
 * LoginController – handles the Login *view*.
 * Part of the MVC architecture (Controller layer).
 * Super-simple validation → if username & password are not empty
 * Author: Panagiotis Pitsikoulis – panagiotispitsikoulis.gr
 * -----------------------------------------------------------
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private String currentTheme = "light-theme";

    @FXML
    private void initialize() {
        // Runs automatically after FXML is loaded.
        // All we do here is hide the error label.
        System.out.println("LoginController initialized");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    @FXML
    private void login(ActionEvent event) {
        // Fired when the user clicks the "Login" button.
        // For demo purposes we accept any non-empty credentials.
        System.out.println("Login button clicked!");

        // Clear any previous error
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Username: '" + username + "'");
        System.out.println("Password: '" + (password != null ? "*".repeat(password.length()) : "null") + "'");

        // Check for empty credentials
        if (username == null || username.trim().isEmpty()) {
            showError("Please enter a username");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            showError("Please enter a password");
            return;
        }

        try {
            System.out.println("Attempting to load main view...");

            // Load main view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
            Parent mainView = loader.load();
            System.out.println("Main view loaded successfully");

            // Get stage from event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            System.out.println("Got stage reference");

            // Create and set the scene
            Scene scene = new Scene(mainView);
            stage.setScene(scene);
            stage.setTitle("Bookify Travel Management System");
            stage.setMaximized(true);
            stage.show();

            System.out.println("Successfully navigated to main view");

        } catch (IOException e) {
            System.err.println("Error loading main view: " + e.getMessage());
            e.printStackTrace();
            showError("System error: Could not load application - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error during login: " + e.getMessage());
            e.printStackTrace();
            showError("Unexpected error: " + e.getMessage());
        }
    }

    private void showError(String message) {
        // Utility to toggle the error label.
        System.out.println("Showing error: " + message);
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }
}