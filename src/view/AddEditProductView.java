package view;

import controller.ProductControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddEditProductView {

    public void show(Stage stage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:#f4f4f4;");

        // ===== HEADER =====
        Label title = new Label("ADD / EDIT PRODUCT");
        title.setTextFill(Color.WHITE);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color:#d9534f; -fx-text-fill:white;");
        logoutBtn.setOnAction(e -> new LoginView().show(stage));

        BorderPane headerBar = new BorderPane();
        headerBar.setLeft(title);
        headerBar.setRight(logoutBtn);
        headerBar.setPadding(new Insets(10));
        headerBar.setStyle("-fx-background-color:#2f6ea3;");

        // ===== FORM =====
        GridPane form = new GridPane();
        form.setPadding(new Insets(20));
        form.setHgap(20);
        form.setVgap(15);

        TextField nameField     = new TextField();
        TextField skuField      = new TextField();
        TextField categoryField = new TextField();
        TextField quantityField = new TextField();
        TextField barcodeField  = new TextField();

        TextField purchaseField = new TextField();
        TextField retailField   = new TextField();
        TextField minStockField = new TextField();
        TextField supplierField = new TextField();

        // FIX: shared message label for validation feedback
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        messageLabel.setWrapText(true);

        // LEFT column
        form.add(new Label("Product Name *"), 0, 0);  form.add(nameField,     0, 1);
        form.add(new Label("SKU *"),           0, 2);  form.add(skuField,      0, 3);
        form.add(new Label("Category"),        0, 4);  form.add(categoryField, 0, 5);
        form.add(new Label("Quantity *"),      0, 6);  form.add(quantityField, 0, 7);
        form.add(new Label("Barcode (opt.)"),  0, 8);  form.add(barcodeField,  0, 9);

        // RIGHT column
        form.add(new Label("Purchase Price (Rs) *"), 1, 0); form.add(purchaseField, 1, 1);
        form.add(new Label("Retail Price (Rs) *"),   1, 2); form.add(retailField,   1, 3);
        form.add(new Label("Min. Reorder Level *"),  1, 4); form.add(minStockField, 1, 5);
        form.add(new Label("Supplier"),              1, 6); form.add(supplierField, 1, 7);

        // ===== BUTTONS =====
        Button saveBtn   = new Button("Save Product");
        Button cancelBtn = new Button("Cancel");

        saveBtn.setStyle("-fx-background-color:#2ecc71; -fx-text-fill:white;");
        cancelBtn.setStyle("-fx-background-color:#999; -fx-text-fill:white;");

        cancelBtn.setOnAction(e -> new ProductmanagementView().show(stage));

        // FIX: implement actual save logic with validation
        ProductControl pc = new ProductControl();

        saveBtn.setOnAction(e -> {
            String name     = nameField.getText().trim();
            String sku      = skuField.getText().trim();
            String category = categoryField.getText().trim();
            String qtyStr   = quantityField.getText().trim();
            String priceStr = retailField.getText().trim();
            String minStr   = minStockField.getText().trim();

            // Basic validation
            if (name.isEmpty() || sku.isEmpty() || qtyStr.isEmpty()
                    || priceStr.isEmpty() || minStr.isEmpty()) {
                messageLabel.setText("Please fill in all required (*) fields.");
                return;
            }

            int qty, min;
            double price;
            try {
                qty   = Integer.parseInt(qtyStr);
                price = Double.parseDouble(priceStr);
                min   = Integer.parseInt(minStr);
            } catch (NumberFormatException ex) {
                messageLabel.setText("Quantity, price and min level must be valid numbers.");
                return;
            }

            boolean saved = pc.addProduct(name, sku, category, qty, price, min);

            if (saved) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Product saved successfully!");
                new ProductmanagementView().show(stage);
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Failed to save product. Please try again.");
            }
        });

        HBox bottom = new HBox(10);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottom.getChildren().addAll(spacer, saveBtn, cancelBtn);
        bottom.setPadding(new Insets(10));

        VBox center = new VBox(form, messageLabel, bottom);
        VBox.setMargin(messageLabel, new Insets(0, 20, 0, 20));

        root.setTop(headerBar);
        root.setCenter(center);

        stage.setScene(new Scene(root, 750, 520));
        stage.setTitle("Add/Edit Product");
        stage.show();
    }
}