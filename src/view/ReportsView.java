package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ReportsView {

    public void show(Stage stage) {

        BorderPane root = new BorderPane();

        // ===== HEADER =====
        Label header = new Label("REPORTS");
        header.setStyle("-fx-text-fill:white;");

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> new DashboardView().show(stage));

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color:#d9534f; -fx-text-fill:white;");
        logoutBtn.setOnAction(e -> new LoginView().show(stage));

        BorderPane topBar = new BorderPane();
        topBar.setLeft(backBtn);
        topBar.setCenter(header);
        topBar.setRight(logoutBtn);
        topBar.setStyle("-fx-background-color:#2f6ea3;");
        topBar.setPadding(new Insets(10));

        // ===== CONTENT =====
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label report1 = new Label("📊 Sales Report");
        Label report2 = new Label("📦 Stock Report");
        Label report3 = new Label("⚠ Low Stock Report");

        content.getChildren().addAll(report1, report2, report3);

        root.setTop(topBar);
        root.setCenter(content);

        stage.setScene(new Scene(root, 600, 350));
        stage.setTitle("Reports");
        stage.show();
    }
}