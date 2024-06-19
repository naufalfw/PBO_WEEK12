/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
    Naufal Ferdiansyah Wirahutama
    A11.2022.14652
 */

import java.sql.*;
import java.util.Scanner;

public class KoneksiDb {
    static final String DB_URL = "jdbc:mysql://localhost:3306/penjualan";
    static final String USER = "root"; 
    static final String PASS = ""; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            while (true) {
                System.out.println("Menu:");
                System.out.println("1. Input Data");
                System.out.println("2. Edit Data");
                System.out.println("3. Delete Data");
                System.out.println("4. Show Data");
                System.out.println("5. Exit");
                System.out.print("Pilih menu: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        inputData(conn, scanner);
                        break;
                    case 2:
                        editData(conn, scanner);
                        break;
                    case 3:
                        deleteData(conn, scanner);
                        break;
                    case 4:
                        showData(conn);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inputData(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Kode Barang: ");
        String kode_brg = scanner.nextLine();
        System.out.print("Nama Barang: ");
        String nama_brg = scanner.nextLine();
        System.out.print("Stok: ");
        int stok = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Satuan: ");
        String satuan = scanner.nextLine();
        System.out.print("Stok Minimal: ");
        int stok_min = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String sql = "INSERT INTO barang (kode_brg, nama_brg, stok, satuan, stok_min) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kode_brg);
            pstmt.setString(2, nama_brg);
            pstmt.setInt(3, stok);
            pstmt.setString(4, satuan);
            pstmt.setInt(5, stok_min);
            pstmt.executeUpdate();
            System.out.println("Data berhasil ditambahkan.");
        }
    }

    public static void editData(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Kode Barang yang akan diedit: ");
        String kode_brg = scanner.nextLine();
        System.out.print("Nama Barang baru: ");
        String nama_brg = scanner.nextLine();
        System.out.print("Stok baru: ");
        int stok = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Satuan baru: ");
        String satuan = scanner.nextLine();
        System.out.print("Stok Minimal baru: ");
        int stok_min = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String sql = "UPDATE barang SET nama_brg = ?, stok = ?, satuan = ?, stok_min = ? WHERE kode_brg = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nama_brg);
            pstmt.setInt(2, stok);
            pstmt.setString(3, satuan);
            pstmt.setInt(4, stok_min);
            pstmt.setString(5, kode_brg);
            pstmt.executeUpdate();
            System.out.println("Data berhasil diupdate.");
        }
    }

    public static void deleteData(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Kode Barang yang akan dihapus: ");
        String kode_brg = scanner.nextLine();

        String sql = "DELETE FROM barang WHERE kode_brg = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kode_brg);
            pstmt.executeUpdate();
            System.out.println("Data berhasil dihapus.");
        }
    }

    public static void showData(Connection conn) throws SQLException {
        String sql = "SELECT * FROM barang";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-10s %-50s %-10s %-20s %-10s%n", "Kode", "Nama Barang", "Stok", "Satuan", "Stok Min");
            while (rs.next()) {
                System.out.printf("%-10s %-50s %-10d %-20s %-10d%n",
                        rs.getString("kode_brg"),
                        rs.getString("nama_brg"),
                        rs.getInt("stok"),
                        rs.getString("satuan"),
                        rs.getInt("stok_min"));
            }
        }
    }
}
