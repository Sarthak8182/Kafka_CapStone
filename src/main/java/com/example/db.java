package com.example;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class db {
    public void InsRow(product product) {
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/capstone", "root", "root");

            PreparedStatement pstatement = conn.prepareStatement("select * from prodtable where pogid =?");
            pstatement.setString(1, product.getPogId());
            rs = pstatement.executeQuery();
            // checks if entry for the particular pogid is present in table..If yes then update else insert
            if(rs.next())
            {
                PreparedStatement stmt=conn.prepareStatement("update prodtable set supc=?,price=?,quantity=? where pogid=?");
                stmt.setString(1,product.getSupc());
                stmt.setString(2,product.getPrice());
                stmt.setString(3,product.getQuantity());
                stmt.setString(3,product.getPogId());

                int i=stmt.executeUpdate();
                System.out.println(i+"records updated");
            }
            else {
                PreparedStatement pstmt = conn.prepareStatement("Insert into prodtable (pogid,supc,price,quantity) values(?,?,?,?)");
                pstmt.setString(1, product.getPogId());
                pstmt.setString(2, product.getSupc());
                pstmt.setString(3, product.getPrice());
                pstmt.setString(4, product.getQuantity());

                int temp = pstmt.executeUpdate();
                if (temp == 1) {
                    System.out.println("The row is inserted");
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if(rs != null)  rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}