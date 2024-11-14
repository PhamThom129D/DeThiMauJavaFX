package com.example.dethimau.DeThi2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 2234;
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(host);
            int number = 10;
            byte[] buffer = String.valueOf(number).getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
            System.out.println("Gửi tin nhắn thành công");

            byte[] buf = new byte[2024];
            DatagramPacket packet2 = new DatagramPacket(buf, buf.length);
            socket.receive(packet2);
            String received = new String(packet2.getData(), 0, packet2.getLength());
            System.out.println(received);
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
