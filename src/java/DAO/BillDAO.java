package DAO;

import models.Bill;
import utils.BillResult;
import utils.BillResultStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    // CREATE BILL (with stock check + transaction)
    public static BillResult createBill(Bill bill) {
        Connection con = null;
        PreparedStatement psCheckStock = null;
        PreparedStatement psInsertBill = null;
        PreparedStatement psUpdateStock = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // begin transaction

            // 1. Check item availability
            String sqlStock = "SELECT stock, price FROM items WHERE id = ?";
            psCheckStock = con.prepareStatement(sqlStock);
            psCheckStock.setInt(1, bill.getItemId());
            rs = psCheckStock.executeQuery();

            if (!rs.next()) {
                return new BillResult(BillResultStatus.ITEM_NOT_FOUND, "Item not found.");
            }

            int availableStock = rs.getInt("stock");
            float unitPrice = rs.getFloat("price");

            if (bill.getUnits() > availableStock) {
                return new BillResult(
                        BillResultStatus.OUT_OF_STOCK,
                        "Only " + availableStock + " units available."
                );
            }

            float totalAmount = bill.getUnits() * unitPrice;

            // 2. Insert bill
            String sqlInsert = "INSERT INTO bills (customer_id, item_id, units, unit_price, total_amount, billing_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            psInsertBill = con.prepareStatement(sqlInsert);
            psInsertBill.setInt(1, bill.getCustomerId());
            psInsertBill.setInt(2, bill.getItemId());
            psInsertBill.setInt(3, bill.getUnits());
            psInsertBill.setFloat(4, unitPrice);
            psInsertBill.setFloat(5, totalAmount);
            psInsertBill.setTimestamp(6, bill.getBillingDate());

            int inserted = psInsertBill.executeUpdate();
            if (inserted <= 0) {
                throw new SQLException("Failed to insert bill");
            }

            // 3. Update stock
            String sqlUpdateStock = "UPDATE items SET stock = stock - ? WHERE id = ?";
            psUpdateStock = con.prepareStatement(sqlUpdateStock);
            psUpdateStock.setInt(1, bill.getUnits());
            psUpdateStock.setInt(2, bill.getItemId());

            int updated = psUpdateStock.executeUpdate();
            if (updated <= 0) {
                throw new SQLException("Failed to update stock");
            }

            con.commit();
            return new BillResult(
                    BillResultStatus.SUCCESS,
                    "Bill created successfully. Total: " + totalAmount
            );

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return new BillResult(BillResultStatus.DB_ERROR, "Database error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (psCheckStock != null) psCheckStock.close();
                if (psInsertBill != null) psInsertBill.close();
                if (psUpdateStock != null) psUpdateStock.close();
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // READ ALL BILLS
    public static List<Bill> getAllBills() {
        List<Bill> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM bills";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bill b = new Bill(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("item_id"),
                        rs.getInt("units"),
                        rs.getFloat("unit_price"),
                        rs.getFloat("total_amount"),
                        rs.getTimestamp("billing_date"),
                        rs.getTimestamp("created_at")
                );
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // READ BILL BY CUSTOMER
    public static List<Bill> filterBillByCustomer(int customerId) {
        List<Bill> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM bills WHERE customer_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bill b = new Bill(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("item_id"),
                        rs.getInt("units"),
                        rs.getFloat("unit_price"),
                        rs.getFloat("total_amount"),
                        rs.getTimestamp("billing_date"),
                        rs.getTimestamp("created_at")
                );
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE BILL (and adjust stock difference if units change)
    public static BillResult updateBill(Bill bill) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Get old bill
            String sqlOld = "SELECT item_id, units FROM bills WHERE id = ?";
            PreparedStatement psOld = con.prepareStatement(sqlOld);
            psOld.setInt(1, bill.getId());
            ResultSet rs = psOld.executeQuery();
            if (!rs.next()) {
                return new BillResult(BillResultStatus.ITEM_NOT_FOUND, "Bill not found.");
            }

            int oldItemId = rs.getInt("item_id");
            int oldUnits = rs.getInt("units");

            // 2. Restore old stock first
            String restoreStock = "UPDATE items SET stock = stock + ? WHERE id = ?";
            PreparedStatement psRestore = con.prepareStatement(restoreStock);
            psRestore.setInt(1, oldUnits);
            psRestore.setInt(2, oldItemId);
            psRestore.executeUpdate();

            // 3. Check new stock for updated item
            String checkStock = "SELECT stock, price FROM items WHERE id = ?";
            PreparedStatement psCheck = con.prepareStatement(checkStock);
            psCheck.setInt(1, bill.getItemId());
            ResultSet rsCheck = psCheck.executeQuery();
            if (!rsCheck.next()) {
                return new BillResult(BillResultStatus.ITEM_NOT_FOUND, "Item not found.");
            }

            int availableStock = rsCheck.getInt("stock");
            float unitPrice = rsCheck.getFloat("price");

            if (bill.getUnits() > availableStock) {
                return new BillResult(BillResultStatus.OUT_OF_STOCK,
                        "Only " + availableStock + " units available.");
            }

            float totalAmount = bill.getUnits() * unitPrice;

            // 4. Update bill
            String sqlUpdate = "UPDATE bills SET customer_id=?, item_id=?, units=?, unit_price=?, total_amount=?, billing_date=? WHERE id=?";
            PreparedStatement psUpdate = con.prepareStatement(sqlUpdate);
            psUpdate.setInt(1, bill.getCustomerId());
            psUpdate.setInt(2, bill.getItemId());
            psUpdate.setInt(3, bill.getUnits());
            psUpdate.setFloat(4, unitPrice);
            psUpdate.setFloat(5, totalAmount);
            psUpdate.setTimestamp(6, bill.getBillingDate());
            psUpdate.setInt(7, bill.getId());
            psUpdate.executeUpdate();

            // 5. Deduct stock
            String deductStock = "UPDATE items SET stock = stock - ? WHERE id = ?";
            PreparedStatement psDeduct = con.prepareStatement(deductStock);
            psDeduct.setInt(1, bill.getUnits());
            psDeduct.setInt(2, bill.getItemId());
            psDeduct.executeUpdate();

            con.commit();
            return new BillResult(BillResultStatus.SUCCESS, "Bill updated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return new BillResult(BillResultStatus.DB_ERROR, "Database error: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // DELETE BILL (restore stock)
    public static BillResult deleteBill(int billId) {
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // 1. Get bill info
            String sqlGet = "SELECT item_id, units FROM bills WHERE id = ?";
            PreparedStatement psGet = con.prepareStatement(sqlGet);
            psGet.setInt(1, billId);
            ResultSet rs = psGet.executeQuery();
            if (!rs.next()) {
                return new BillResult(BillResultStatus.ITEM_NOT_FOUND, "Bill not found.");
            }
            int itemId = rs.getInt("item_id");
            int units = rs.getInt("units");

            // 2. Delete bill
            String sqlDelete = "DELETE FROM bills WHERE id = ?";
            PreparedStatement psDelete = con.prepareStatement(sqlDelete);
            psDelete.setInt(1, billId);
            psDelete.executeUpdate();

            // 3. Restore stock
            String sqlRestore = "UPDATE items SET stock = stock + ? WHERE id = ?";
            PreparedStatement psRestore = con.prepareStatement(sqlRestore);
            psRestore.setInt(1, units);
            psRestore.setInt(2, itemId);
            psRestore.executeUpdate();

            con.commit();
            return new BillResult(BillResultStatus.SUCCESS, "Bill deleted successfully and stock restored.");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return new BillResult(BillResultStatus.DB_ERROR, "Database error: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
