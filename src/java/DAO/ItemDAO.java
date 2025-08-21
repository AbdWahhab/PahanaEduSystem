package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Item;

public class ItemDAO {
    public static boolean addItem (Item item){
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO items (code, title, description, category, price, stock) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, item.getCode());
            ps.setString(2, item.getTitle());
            ps.setString(3, item.getDescription());
            ps.setString(4, item.getCategory());
            ps.setFloat(5, item.getPrice());
            ps.setInt(6, item.getStock());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Item> getAllItems(){
        List<Item> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM items";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item i = new Item(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getFloat("price"),
                        rs.getInt("stock"),
                        rs.getTimestamp("created_at")
                );
                list.add(i);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // READ ONE BY ID
    public static Item filterItemById(String code) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM items WHERE code = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Item(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getFloat("price"),
                        rs.getInt("stock"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // SEARCH ANY COLUMN
    public static List<Item> searchItems(String keyword) {
        List<Item> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM items WHERE " +
                    "code LIKE ? OR " +
                    "title LIKE ? OR " +
                    "description LIKE ? OR " +
                    "category LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item i = new Item(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getFloat("price"),
                        rs.getInt("stock"),
                        rs.getTimestamp("created_at")
                );
                list.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public static boolean updateItem(Item item) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE items SET title = ?, description = ?, category = ?, price = ?, stock = ? WHERE code = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getCategory());
            ps.setFloat(4, item.getPrice());
            ps.setInt(5, item.getStock());
            ps.setString(6, item.getCode()); // WHERE clause

            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public static boolean deleteItem(String code) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM items WHERE code = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, code);

            int result = ps.executeUpdate();
            return result > 0; // true if at least 1 row deleted
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
