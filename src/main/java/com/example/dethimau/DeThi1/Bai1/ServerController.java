package com.example.dethimau.Bai1;

import com.example.dethimau.HelloApplication;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerController {

    @FXML
    private Label serverMessage;
    @FXML
    private static Label clientMessage;

    public static class Main extends Application {
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Server.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
    }

    public void send(ActionEvent event) {
        int port = 8888;
        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket(port);
            byte[] buffer = new byte[1024];
            System.out.println("Server cho du lieu o cong  " + port);
            while(true){
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message1 = new String(packet.getData(), 0, packet.getLength());
                double usd = Double.parseDouble(message1);
                System.out.println("Nhan tin nhan tu client");
                clientMessage.setText("Client : " +usd + "$");

                InetAddress address = packet.getAddress();
                int portClient = packet.getPort();
                double vnd = usd * 23000;
                String result = String.format("Server : Kết quả là : " + vnd + "vnd");
                DatagramPacket packet2 = new DatagramPacket(result.getBytes(), result.length(), address, portClient);
                socket.send(packet2);
                serverMessage.setText(result);
                System.out.println("Gui du lieu thanh cong");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
