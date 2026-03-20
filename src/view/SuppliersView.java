package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SuppliersView {

    public void show(Stage stage) {

        BorderPane root = new BorderPane();

        // ===== HEADER =====
        Label header = new Label("SUPPLIER MANAGEMENT");
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

        // ===== TABLE =====
        TableView<String[]> table = new TableView<>();

        String[] cols = {"ID", "Name", "Phone", "Address", "Action"};

        for (int i = 0; i < cols.length; i++) {
            final int index = i;
            TableColumn<String[], String> col = new TableColumn<>(cols[i]);
            col.setCellValueFactory(data ->
                    new javafx.beans.property.SimpleStringProperty(
                            data.getValue()[index]));
            table.getColumns().add(col);
        }

        table.getItems().addAll(
                new String[]{"S01", "ABC Traders",   "9800000000", "Kathmandu", ""},
                new String[]{"S02", "XYZ Suppliers", "9811111111", "Pokhara",   ""}
        );

        VBox center = new VBox(10, table);
        center.setPadding(new Insets(10));

        root.setTop(topBar);
        root.setCenter(center);

        stage.setScene(new Scene(root, 700, 400));
        stage.setTitle("Suppliers");
        stage.show();
    }
}