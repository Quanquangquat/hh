/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author DELL
 */


import java.sql.ResultSet;
import model.User;
import java.util.ArrayList;
import java.util.List;
import service.DBContext;

public class UserDAO extends DBContext{
    private static List<User> users = new ArrayList<>();

    static {
        users.add(new User("Admin", "admin@example.com", "adminpass", "admin"));
    }

    public User validateUser(String email, String password) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public boolean isEmailRegistered(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public User getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User user) {
        users.replaceAll(u -> u.getEmail().equals(user.getEmail()) ? user : u);
    }
    
    //--------------------------------------------------------------------------
    DBContext db = new DBContext();
    
    public User getUserById(int xUserId) {
        int xRoleId, xDepId, xStatus;
        String xFullName;
        User x = null;
        String xSql = "select * from user where user_id = ?";
        ResultSet rs = null;
        java.sql.PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(xSql);
            ps.setInt(1, xUserId);

            rs = ps.executeQuery();
            if (rs.next()) {
                xRoleId = rs.getInt("role_id");
                xDepId = rs.getInt("dep_id");
                xStatus = rs.getInt("status");
                xFullName = rs.getString("full_name");
                x = new User(xUserId, xFullName, xRoleId, xDepId, xStatus);
            }

            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                db.closeConnection();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return (x);
    }
}

