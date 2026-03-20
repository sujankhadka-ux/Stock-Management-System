package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.DBConnection;

public class LoginControl {

    // FIX: removed "DBConnection db = new DBConnection()" — getConnection() is static
    public boolean login(String username, String password) {
        // FIX: column name changed to 'password' to match RegisterControl insert
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // FIX: simplified — return directly

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}