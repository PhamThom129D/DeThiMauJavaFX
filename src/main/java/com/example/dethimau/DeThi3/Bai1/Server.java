package com.example.dethimau.DeThi3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) {
        int port = 1111;
        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket(port);
            System.out.println("Server chờ kết nối ở cổng : " + port);

            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String data = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Số nhận được là  : " +data);
            double inch = 0;
            try {
                inch = Double.parseDouble(data);
            }catch (NumberFormatException e){
                String errorMessage = "Không phải số";
                sendMessage(errorMessage,packet, socket);
            }
            double cm = inch * 2.54;
            String sendMessage = "Kết quả chuyển đổi là : " +cm;
            System.out.println(sendMessage);
            sendMessage(sendMessage,packet,socket);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void sendMessage(String message ,DatagramPacket packet, DatagramSocket socket) throws IOException {
        InetAddress address = packet.getAddress();
        int portClient = packet.getPort();
        DatagramPacket packet2 = new DatagramPacket(message.getBytes(), message.getBytes().length, address, portClient);
        socket.send(packet2);
        System.out.println("Gửi dữ liệu thành công");
    }
}
