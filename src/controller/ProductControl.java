package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductControl {

    // ===== LOAD ALL PRODUCTS =====
    public ObservableList<String[]> getAllProducts() {
        ObservableList<String[]> list = FXCollections.observableArrayList();

        // FIX: use try-with-resources so connection and statement are always closed
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM products");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new String[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("sku"),
                        rs.getString("category"),
                        rs.getString("quantity"),
                        "Rs " + rs.getString("price")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===== SEARCH PRODUCT =====
    public ObservableList<String[]> searchProduct(String keyword) {
        ObservableList<String[]> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM products WHERE name LIKE ? OR category LIKE ?";

        // FIX: try-with-resources
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new String[]{
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("sku"),
                            rs.getString("category"),
                            rs.getString("quantity"),
                            "Rs " + rs.getString("price")
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===== ADD PRODUCT =====
    public boolean addProduct(String name, String sku, String category,
                              int quantity, double price, int minLevel) {

        String sql = "INSERT INTO products(name, sku, category, quantity, price, min_level) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, sku);
            ps.setString(3, category);
            ps.setInt(4, quantity);
            ps.setDouble(5, price);
            ps.setInt(6, minLevel);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== DELETE PRODUCT =====
    public boolean deleteProduct(String id) {
        String sql = "DELETE FROM products WHERE id=?";

        // FIX: try-with-resources
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}