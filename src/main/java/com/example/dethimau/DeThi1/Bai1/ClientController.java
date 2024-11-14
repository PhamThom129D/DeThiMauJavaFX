package com.example.dethimau.Bai1;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientController {

    @FXML
    private TextField usdAmountField;  // Nhập số tiền USD
    @FXML
    private Label resultLabel;         // Hiển thị kết quả VND

    public static class Main extends Application {

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Client.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Currency Converter Client");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

    // Hàm gửi số tiền USD tới Server và nhận kết quả VND
    public void sendMessage(ActionEvent event) {
        String usdAmount = usdAmountField.getText(); // Lấy số tiền USD từ TextField
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost"); // Địa chỉ server
            int serverPort = 8888;

            // Gửi số tiền USD tới Server
            DatagramPacket packet = new DatagramPacket(usdAmount.getBytes(), usdAmount.length(), serverAddress, serverPort);
            socket.send(packet);
            System.out.println("Đã gửi số tiền USD tới server: " + usdAmount);

            // Nhận kết quả chuyển đổi từ Server
            byte[] buffer = new byte[1024];
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(responsePacket);
            String responseMessage = new String(responsePacket.getData(), 0, responsePacket.getLength());

            // Hiển thị kết quả chuyển đổi (VND) trên giao diện Client
            resultLabel.setText("Kết quả: " + responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
