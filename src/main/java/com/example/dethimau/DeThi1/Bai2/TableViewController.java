package com.example.dethimau.DeThi1.Bai2;

import com.example.dethimau.DeThi1.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    @FXML
    private TableView<Book> product;
    @FXML
    private TableColumn<Book, Integer> idBook;
    @FXML
    private TableColumn<Book, String> nameBook;
    @FXML
    private TableColumn<Book, String> authorBook;
    @FXML
    private TableColumn<Book, Integer> priceBook;
    @FXML
    private TableColumn<Book, Boolean> statusBook;
    @FXML
    private TableColumn<Book, Void> action;
    @FXML
    private TextField keySearch;


    public void initialize() throws SQLException {
        getDataFromDB();
    }
    public void getDataFromDB() throws SQLException {
        String query = "select * from book";
        Connection connection = DatabaseConnection.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("bookID");
                String name = resultSet.getString("bookName");
                String author = resultSet.getString("author");
                int price = resultSet.getInt("price");
                boolean stock = resultSet.getBoolean("inStock");

                Book book = new Book(id, name, author, price, stock);
                product.getItems().add(book);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        setColumn();
    }

    private void setColumn() {
        idBook.setCellValueFactory(new PropertyValueFactory<>("idBook"));
        nameBook.setCellValueFactory(new PropertyValueFactory<>("nameBook"));
        authorBook.setCellValueFactory(new PropertyValueFactory<>("authorBook"));
        priceBook.setCellValueFactory(new PropertyValueFactory<>("priceBook"));
        statusBook.setCellValueFactory(new PropertyValueFactory<>("statusBook"));
        action.setCellFactory(col -> new TableCell<Book, Void>(){
            private final Button updateButton = new Button("Cập nhật ");
            private final Button deleteButton = new Button("Xoá sản phẩm");
            {
                updateButton.setOnAction(e -> {
                    Book book = product.getItems().get(getIndex());
                    if(showAlert("Bạn muốn cập nhật sản phẩm này ?")){
                        try {
                            updateBook(book.getIdBook());
                            reload();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    };
                });
                deleteButton.setOnAction(e -> {
                    Book book = product.getItems().get(getIndex());
                    if(showAlert("Bạn chắc chắn muốn xóa sản phẩm này ?")){
                        try {
                            product.getItems().remove(getIndex());
                            deleteProduct(book.getIdBook());
                            reload();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                }else {
                    HBox hbox = new HBox();
                    hbox.getChildren().addAll(updateButton, deleteButton);
                    setGraphic(hbox);
                }
            }
        });

    }

    private void deleteProduct(int idBook) throws SQLException {
        String query = "delete from book where idBook = ?";
         Connection conn = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = conn.prepareStatement(query);
         preparedStatement.setInt(1, idBook);
         preparedStatement.executeUpdate();
    }

    private void updateBook(int bookID) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateProduct.fxml"));
            Parent root = loader.load();
            UpdateProductController controller = loader.getController();
            controller.setProduct(bookID);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


        String query = "Update set bookName = ? , author = ? , price = ? , inStock = ? where bookID = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, nameBook.getText());
        preparedStatement.setString(2, authorBook.getText());
        preparedStatement.setString(3,priceBook.getText());
        preparedStatement.setString(4,statusBook.getText());
        preparedStatement.setInt(5,bookID);
        preparedStatement.executeUpdate();
    }

    public boolean showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().get() == ButtonType.OK;
    }
    public void reload() throws SQLException {
        product.getItems().clear();
        initialize();

    }

    public void search(ActionEvent event) throws SQLException {
        String key = keySearch.getText();
        String query = "select * from book where bookName like ?";
        Connection connection = DatabaseConnection.getConnection();
        product.getItems().clear();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,"%"+key+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("bookID");
                String name = resultSet.getString("bookName");
                String author = resultSet.getString("author");
                int price = resultSet.getInt("price");
                boolean stock = resultSet.getBoolean("inStock");

                Book book = new Book(id, name, author, price, stock);
                product.getItems().add(book);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        setColumn();
    }
}

