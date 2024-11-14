package com.example.dethimau;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateProductController {
    @FXML
    private TextField nameBook;
    @FXML
    private TextField authorBook;
    @FXML
    private TextField priceBook;
    @FXML
    private ChoiceBox<String> statusBook;
    @FXML
    private int bookID;
    @FXML
    public  void initialize() {
        statusBook.getItems().addAll("Còn hàng", "Hết hàng");
        statusBook.getSelectionModel().selectFirst();
    }
    public void setProduct(int bookID) throws SQLException {
        this.bookID = bookID;
        String query = "Select * from book where bookID = ?";
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, bookID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            nameBook.setText(rs.getString(2));
            authorBook.setText(rs.getString(3));
            priceBook.setText(rs.getString(4));
            statusBook.setValue(rs.getString(5));
        }
    }


    public void updateBook(ActionEvent event) throws SQLException {
        String name = nameBook.getText();
        String author = authorBook.getText();
        String price = priceBook.getText();
        String status = statusBook.getValue();
        String query = "Update book set bookName = ?, author = ?, price = ?, inStock = ? where bookID = ?";
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, author);
        ps.setString(3, price);
        ps.setInt(5, bookID);
        ps.setString(4, status);
        ps.executeUpdate();
    }
}
