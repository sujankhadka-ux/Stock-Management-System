package view;

import controller.ProductControl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ProductmanagementView {

    public void show(Stage stage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:#f4f4f4;");

        // ===== HEADER =====
        Label title = new Label("PRODUCT MANAGEMENT");
        title.setStyle("-fx-text-fill:white; -fx-font-weight:bold;");

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> new DashboardView().show(stage));

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color:#d9534f; -fx-text-fill:white;");
        logoutBtn.setOnAction(e -> new LoginView().show(stage));

        BorderPane topBar = new BorderPane();
        topBar.setLeft(backBtn);
        topBar.setCenter(title);
        topBar.setRight(logoutBtn);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color:#2f6ea3;");

        // ===== SEARCH + ADD =====
        TextField searchField = new TextField();
        searchField.setPromptText("Search products...");
        searchField.setPrefWidth(250);

        Button searchBtn = new Button("Search");

        Button addBtn = new Button("+ Add Product");
        addBtn.setStyle("-fx-background-color:#2ecc71; -fx-text-fill:white;");
        addBtn.setOnAction(e -> new AddEditProductView().show(stage));

        HBox topControls = new HBox(10, searchField, searchBtn, addBtn);
        topControls.setPadding(new Insets(10));

        // ===== TABLE =====
        TableView<String[]> table = new TableView<>();

        String[] columns = {"ID", "Product Name", "SKU", "Category", "Qty", "Price"};

        for (int i = 0; i < columns.length; i++) {
            final int index = i;
            TableColumn<String[], String> col = new TableColumn<>(columns[i]);
            col.setCellValueFactory(data ->
                    new SimpleStringProperty(data.getValue()[index]));
            col.setPrefWidth(120);
            table.getColumns().add(col);
        }

        // ===== ACTION COLUMN =====
        TableColumn<String[], Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn   = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox box         = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color:#337ab7; -fx-text-fill:white;");
                deleteBtn.setStyle("-fx-background-color:#d9534f; -fx-text-fill:white;");
                editBtn.setOnAction(e -> new AddEditProductView().show(stage));

                deleteBtn.setOnAction(e -> {
                    String[] row = getTableView().getItems().get(getIndex());
                    ProductControl pc = new ProductControl();
                    if (pc.deleteProduct(row[0])) {
                        getTableView().getItems().remove(getIndex());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        table.getColumns().add(actionCol);

        // FIX: load real data from DB instead of hardcoded rows
        ProductControl pc = new ProductControl();
        table.getItems().setAll(pc.getAllProducts());

        // FIX: wire search button to ProductControl.searchProduct()
        searchBtn.setOnAction(e -> {
            String keyword = searchField.getText().trim();
            ObservableList<String[]> results = keyword.isEmpty()
                    ? pc.getAllProducts()
                    : pc.searchProduct(keyword);
            table.getItems().setAll(results);
        });

        // Also search on Enter key in the search field
        searchField.setOnAction(e -> searchBtn.fire());

        // ===== LAYOUT =====
        VBox center = new VBox(topControls, table);
        root.setTop(topBar);
        root.setCenter(center);

        stage.setScene(new Scene(root, 850, 500));
        stage.setTitle("Product Management");
        stage.show();
    }
}