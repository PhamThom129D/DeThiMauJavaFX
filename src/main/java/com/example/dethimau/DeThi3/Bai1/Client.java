package com.example.dethimau.DeThi3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        int port = 1111;
        String host = "localhost";
        try{
            DatagramSocket socket = new DatagramSocket();
            double inch = 10.5;
            byte[] buf = String.valueOf(inch).getBytes();
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(buf,buf.length,address,port);
            socket.send(packet);
            System.out.println("Gửi tin nhắn thành công");

            byte[] buf2 = new byte[1024];
            DatagramPacket packet2 = new DatagramPacket(buf2,buf2.length);
            socket.receive(packet2);
            System.out.println(packet2.getLength());
//            -- ket qua ra 29 --
            System.out.println(packet2.getData());
            String received = new String(packet2.getData(),0,packet2.getLength());
            System.out.println(received);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
