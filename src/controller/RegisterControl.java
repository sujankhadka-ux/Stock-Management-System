package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.DBConnection;

public class RegisterControl {

    // FIX: removed "DBConnection db = new DBConnection()" — getConnection() is static
    public boolean register(String username, String password, String role) {
        // FIX: column changed from 'password_hash' to 'password' to match LoginControl query
        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}