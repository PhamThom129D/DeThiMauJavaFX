package com.example.dethimau.DeThi2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 2234;
        try {
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("Server chờ kết nối ở cổng : " + port);

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            InetAddress address = packet.getAddress();
            int portClient = packet.getPort();
            int number = 0;
            try {
                number = Integer.parseInt(received);
            } catch (NumberFormatException e) {
                String errorMessage = "Khong phai so nguyen ";
                sendMessage(errorMessage, address, portClient, socket);
            }
            if (number > 0) {
                String sendMessage = " ";
                if (isPrimeNumber(number)) {
                    sendMessage = number + " là số nguyên tố tai sao .";
                } else {
                    sendMessage = number + "không phải số nguyên tố .";
                }
                sendMessage(sendMessage, address, portClient, socket);
            } else {
                String errorMessage = "Khong phai so nguyen duong ";
                sendMessage(errorMessage, address, portClient, socket);
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(String sendMessage, InetAddress address, int portClient, DatagramSocket socket) throws IOException {
        DatagramPacket packet1 = new DatagramPacket(sendMessage.getBytes(), sendMessage.getBytes().length, address, portClient);
        socket.send(packet1);
        System.out.println("Gửi tin nhắn thành công");
    }

    public static boolean isPrimeNumber(int n) {
        if (n < 2) {
            return false;
        }
        int squareRoot = (int) Math.sqrt(n);
        for (int i = 2; i <= squareRoot; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

}
