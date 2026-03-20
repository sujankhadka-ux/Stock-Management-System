package view;

import controller.LoginControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginView {

    public void show(Stage stage) {

        VBox root = new VBox(15);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f4f4f4;");

        Label header = new Label("STOCK MANAGEMENT SYSTEM");
        header.setStyle("-fx-text-fill: #337ab7; -fx-font-weight: bold;");

        Label title = new Label("Login Screen");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(250);

        Button loginButton = new Button("Login");
        loginButton.setMinWidth(250);
        loginButton.setStyle("-fx-background-color: #337ab7; -fx-text-fill: white; -fx-font-weight: bold;");

        Label message = new Label();
        message.setTextFill(Color.RED);

        Hyperlink registerLink = new Hyperlink("Don't have an account? Register");
        registerLink.setOnAction(e -> new RegistrationPage().show(stage));

        // FIX: removed duplicate setOnAction and hardcoded admin check.
        //      Now uses LoginControl to authenticate against the database.
        LoginControl lc = new LoginControl();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                message.setText("Please enter username and password.");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            if (lc.login(username, password)) {
                message.setText("Login Successful");
                message.setStyle("-fx-text-fill: green;");
                new DashboardView().show(stage);
            } else {
                message.setText("Invalid username or password.");
                message.setStyle("-fx-text-fill: red;");
            }
        });

        root.getChildren().addAll(
                header, title,
                usernameField, passwordField,
                loginButton, message, registerLink
        );

        stage.setScene(new Scene(root, 450, 550));
        stage.setTitle("Stock Management System - Login");
        stage.show();
    }
}