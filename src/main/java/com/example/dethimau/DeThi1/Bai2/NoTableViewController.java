package com.example.dethimau.DeThi1.Bai2;

import com.example.dethimau.DeThi1.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestContronller {
    @FXML
    private TextArea book;
    @FXML
    private TextField searchTextField;
    @FXML
    private TextField bookID;
    @FXML
    private TextField bookName;
    @FXML
    private TextField bookAuthor;
    @FXML
    private TextField bookPrice;
    @FXML
    private TextField bookStatus;
    @FXML
    private TextField bookID1;
    @FXML
    private TextField bookName1;
    @FXML
    private TextField bookAuthor1;
    @FXML
    private TextField bookPrice1;
    @FXML
    private TextField bookStatus1;
    @FXML
    public void initialize() throws SQLException {
    }

    public void loadData(ActionEvent event) throws SQLException {
        String query = "select * from book";
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        book.clear();
        while (rs.next()) {
            book.appendText("Ma Sach : " + rs.getString("bookID")
                    + "\t Ten sach :" + rs.getString("bookName")
                    + "\t Tac gia :  " + rs.getString("author")
                    + "\t Gia sach : " + rs.getString("price")
                    + "\t Tinh Trang : " + rs.getBoolean("inStock") + "\n");
            System.out.println(book.getText());
        }
    }

    public void searchBook(ActionEvent event) throws SQLException {
        String query = "select * from book where bookName like ?";
        String keySearch = searchTextField.getText();
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, "%" + keySearch + "%");
        ResultSet rs = ps.executeQuery();
        book.clear();
        while (rs.next()) {
            book.appendText("Ma Sach : " + rs.getString("bookID")
                    + "\t Ten sach :" + rs.getString("bookName")
                    + "\t Tac gia :  " + rs.getString("author")
                    + "\t Gia sach : " + rs.getString("price")
                    + "\t Tinh Trang : " + rs.getBoolean("inStock") + "\n");
        }
        System.out.println(book.getText());
    }

    public void addBook(ActionEvent event) throws SQLException {
        String query = "insert into book(bookID,bookName,author,price,inStock) values(?,?,?,?,?)";
        String id = bookID.getText();
        String name = bookName.getText();
        String author = bookAuthor.getText();
        String price = bookPrice.getText();
        String inStock = bookStatus.getText();
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, name);
        ps.setString(3, author);
        ps.setDouble(4, Double.parseDouble(price));
        ps.setBoolean(5, Boolean.parseBoolean(inStock));
        ps.executeUpdate();
        loadData(event);
    }

    public void updateBook(ActionEvent event) throws SQLException {
        String name = bookName1.getText();
        String author = bookAuthor1.getText();
        String price = bookPrice1.getText();
        String inStock = bookStatus1.getText();

        String query = "Update book set bookName = ?, author =? , price =? , inStock =?  where bookID = ?";
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, author);
        ps.setDouble(3, Double.parseDouble(price));
        ps.setBoolean(4, Boolean.parseBoolean(inStock));
        ps.setInt(5, getID(event));
        ps.executeUpdate();
        loadData(event);
    }

    public int getID(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(bookID1.getText());
        String query = "select * from book where bookID = ?";
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            bookName1.setText(rs.getString("bookName"));
            bookAuthor1.setText(rs.getString("author"));
            bookPrice1.setText(rs.getString("price"));
            bookStatus1.setText(rs.getString("inStock"));
        }
        return id;
    }
}
