package view;

import controller.DashboardControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DashboardView {

    public void show(Stage stage) {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:#f4f4f4;");

        // ===== HEADER =====
        Label header = new Label("ONLINE STOCK MANAGEMENT SYSTEM");
        header.setTextFill(Color.WHITE);

        StackPane topBar = new StackPane(header);
        topBar.setStyle("-fx-background-color:#2f6ea3;");
        topBar.setPadding(new Insets(10));

        // ===== MENU =====
        Label home      = new Label("Home");
        Label products  = new Label("Products");
        Label suppliers = new Label("Suppliers");
        Label reports   = new Label("Reports");
        Label logout    = new Label("Logout");

        products.setOnMouseClicked(e  -> new ProductmanagementView().show(stage));
        suppliers.setOnMouseClicked(e -> new SuppliersView().show(stage));
        reports.setOnMouseClicked(e   -> new ReportsView().show(stage));
        logout.setOnMouseClicked(e    -> new LoginView().show(stage));

        HBox menu = new HBox(25, home, products, suppliers, reports, logout);
        menu.setPadding(new Insets(10));

        VBox top = new VBox(topBar, menu);

        // ===== CARDS =====
        // FIX: use Labels so DashboardControl can update them with real DB values
        Label totalProductsValue = new Label("...");
        Label lowStockValue      = new Label("...");

        HBox cards = new HBox(15,
                createCard("Total Products", totalProductsValue, "#d9e6f2"),
                createCard("Low Stock",      lowStockValue,      "#f2d6d6"),
                createCard("Suppliers",      new Label("—"),     "#d6f2e0"),
                createCard("Today's Updates",new Label("—"),     "#f2ecd6")
        );
        cards.setAlignment(Pos.CENTER);
        cards.setPadding(new Insets(15));

        // FIX: load real data from database via DashboardControl
        DashboardControl dc = new DashboardControl();
        dc.loadDashboardData(totalProductsValue, lowStockValue);

        // ===== QUICK ACTIONS =====
        Label quickTitle = new Label("Quick Actions:");

        HBox actions = new HBox(10,
                createButton("Add Product",      "#2f6ea3", () -> new AddEditProductView().show(stage)),
                createButton("View Reports",     "#337ab7", () -> new ReportsView().show(stage)),
                createButton("Manage Suppliers", "#2e8b57", () -> new SuppliersView().show(stage)),
                createButton("Low-Stock Alerts", "#d9534f", null)
        );

        VBox actionSection = new VBox(5, quickTitle, actions);
        actionSection.setPadding(new Insets(10));

        // ===== ALERTS =====
        Label alertTitle = new Label("Recent Low-Stock Alerts:");

        VBox alerts = new VBox(8,
                createAlert("Rice 5kg Bag",    "3", "10"),
                createAlert("Cooking Oil 1L",  "5", "20"),
                createAlert("Sugar 2kg",       "2", "15")
        );

        VBox alertSection = new VBox(5, alertTitle, alerts);
        alertSection.setPadding(new Insets(10));

        // ===== CENTER =====
        VBox center = new VBox(10, cards, actionSection, alertSection);
        center.setPadding(new Insets(10));

        root.setTop(top);
        root.setCenter(center);

        stage.setScene(new Scene(root, 800, 550));
        stage.setTitle("Dashboard");
        stage.show();
    }

    // ===== CARD (accepts a Label so it can be updated externally) =====
    private VBox createCard(String title, Label valueLabel, String color) {
        Label t = new Label(title);
        valueLabel.setStyle("-fx-font-size:18px; -fx-font-weight:bold;");

        VBox box = new VBox(5, t, valueLabel);
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(160, 90);
        box.setStyle("-fx-background-color:" + color + "; -fx-background-radius:8;");
        return box;
    }

    // ===== BUTTON =====
    private Button createButton(String text, String color, Runnable action) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color:" + color + "; -fx-text-fill:white;");
        if (action != null) btn.setOnAction(e -> action.run());
        return btn;
    }

    // ===== ALERT ROW =====
    private HBox createAlert(String name, String qty, String min) {
        Label item    = new Label(name);
        Label q       = new Label("Qty: " + qty);
        Label m       = new Label("Min: " + min);
        Button reorder = new Button("Reorder");
        reorder.setStyle("-fx-background-color:#d9534f; -fx-text-fill:white;");

        HBox row = new HBox(20, item, q, m, reorder);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }
}