package controller;

import javafx.scene.control.Label;
import model.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardControl {

    // FIX: use try-with-resources to properly close connections
    public void loadDashboardData(Label totalProducts, Label lowStock) {

        // ===== TOTAL PRODUCTS =====
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM products");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalProducts.setText(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ===== LOW STOCK =====
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT COUNT(*) FROM products WHERE quantity <= min_level");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                lowStock.setText(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}