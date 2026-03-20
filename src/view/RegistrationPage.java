package view;

import controller.RegisterControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RegistrationPage {

    public void show(Stage stage) {

        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color:#f4f4f4;");

        Label header = new Label("STOCK MANAGEMENT SYSTEM");
        header.setTextFill(Color.web("#2166A0"));

        VBox card = new VBox(12);
        card.setPadding(new Insets(25));
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(300);
        card.setStyle("-fx-background-color:white; -fx-border-color:#ddd;");

        Label title = new Label("Register");

        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        PasswordField confirm = new PasswordField();
        confirm.setPromptText("Confirm Password");

        // FIX: added role selector so the role is not hard-coded or blank
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("admin", "staff");
        roleBox.setValue("staff");
        roleBox.setMaxWidth(Double.MAX_VALUE);

        Label message = new Label();
        message.setWrapText(true);

        Button registerBtn = new Button("Register");
        registerBtn.setMinWidth(200);
        registerBtn.setStyle("-fx-background-color:#2166A0; -fx-text-fill:white;");

        Hyperlink loginLink = new Hyperlink("Back to Login");
        loginLink.setOnAction(e -> new LoginView().show(stage));

        // FIX: actually call RegisterControl.register() with validation
        RegisterControl rc = new RegisterControl();

        registerBtn.setOnAction(e -> {
            String user = username.getText().trim();
            String pass = password.getText().trim();
            String conf = confirm.getText().trim();
            String role = roleBox.getValue();

            // Validation
            if (user.isEmpty() || pass.isEmpty() || conf.isEmpty()) {
                message.setTextFill(Color.RED);
                message.setText("All fields are required.");
                return;
            }

            if (!pass.equals(conf)) {
                message.setTextFill(Color.RED);
                message.setText("Passwords do not match.");
                return;
            }

            if (pass.length() < 6) {
                message.setTextFill(Color.RED);
                message.setText("Password must be at least 6 characters.");
                return;
            }

            boolean success = rc.register(user, pass, role);

            if (success) {
                message.setTextFill(Color.GREEN);
                message.setText("Registration successful! You can now log in.");
                // Navigate to login after a short delay so user sees the message
                new LoginView().show(stage);
            } else {
                message.setTextFill(Color.RED);
                message.setText("Registration failed. Username may already exist.");
            }
        });

        card.getChildren().addAll(title, username, password, confirm, roleBox,
                                  message, registerBtn, loginLink);
        root.getChildren().addAll(header, card);

        stage.setScene(new Scene(root, 400, 480));
        stage.setTitle("Register");
        stage.show();
    }
}