package client.controller;

import message.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Controller {
    private final DatagramSocket socket;
    private final InetAddress address;
    private final int port;
    public final String ID;

    public Controller(String hostname, int port) throws IOException {
        socket = new DatagramSocket();
        address = InetAddress.getByName(hostname);
        this.port = port;
        sendMessage(new ConnectionMessage(null));
        ID = (String) receiveMessage().data();
    }

    public void shutdown() {
        sendMessage(new ShutdownMessage(ID));
        socket.close();
    }

    public boolean sendCalculationRequest(double[] data) {
        try {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(new CalculationRequest(ID, data));
            oos.flush();

            byte[] buffer = bos.toByteArray();

            socket.send(new DatagramPacket(buffer, buffer.length, address, port));

            oos.close();
            bos.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Double receiveCalculationResult() {
        try {
            byte[] buffer = new byte[578];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);

            final ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
            final ObjectInputStream ois = new ObjectInputStream(bis);

            CalculationResult result = (CalculationResult) ois.readObject();

            ois.close();
            bis.close();

            return result.data();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean sendMessage(Message msg) {
        try {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(msg);
            oos.flush();

            byte[] buffer = bos.toByteArray();

            socket.send(new DatagramPacket(buffer, buffer.length, address, port));

            oos.close();
            bos.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Message receiveMessage() {
        try {
            byte[] buffer = new byte[578];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);

            final ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
            final ObjectInputStream ois = new ObjectInputStream(bis);

            Message msg = (Message) ois.readObject();

            ois.close();
            bis.close();

            return msg;
        } catch (IOException | ClassNotFoundException e) {
            return new Message(MessageType.ERROR, null, e.getMessage());
        }
    }

    public String getHostAddress() {
        return address.getHostAddress();
    }
}