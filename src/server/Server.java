package server;

import message.*;
import server.rowcalculator.RowCalculator;
import server.view.ServerFrame;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;

public class Server {
    private DatagramSocket serverSocket;
    private ServerFrame frame;
    private boolean up = false;


    private byte[] buffer = new byte[578];
    private InetAddress clientAddress;
    private int clientPort;
    private String clientID;

    public void up(String hostname, int port) {
        try {
            serverSocket = new DatagramSocket(new InetSocketAddress(hostname, port));

            frame = new ServerFrame(this);

            System.out.println("server: " + serverSocket.getLocalSocketAddress().toString());

            up = true;
            while (up) {
                Message msg = receiveMessage();

                if (clientID == null) clientID = UUID.randomUUID().toString();

                switch (msg.type) {
                    case MESSAGE ->
                            System.out.println("Message received from client ( " + clientID + " ): " + msg.data());
                    case CONNECTION -> {
                        System.out.println("ConnectionMessage received from client ( " + clientID + " )");
                        frame.addClientToList(clientAddress.getHostAddress(), clientID);
                        sendMessage(new IDMessage(clientID), clientAddress, clientPort);
                    }
                    case CALCULATION_REQUEST -> {
                        System.out.println("CalculationRequest received from client ( " + clientID + " )");
                        double[] data = ((CalculationRequest) msg).data();
                        sendMessage(new CalculationResult(clientID, RowCalculator.calculate(
                                data[0],
                                data[1],
                                data[2],
                                data[3]
                        )), clientAddress, clientPort);
                    }
                    case SHUTDOWN -> {
                        System.out.println("ShutdownMessage received from client ( " + clientID + " )");
                        frame.removeClientFromList(clientAddress.getHostAddress(), clientID);
                    }
                    default -> System.out.println("UNRECOGNIZED MessageType received from client ( " + clientID + " )");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public String getLocalSocketAddress() {
        return serverSocket.getLocalSocketAddress().toString();
    }

    public void shutdown() {
        up = false;
        serverSocket.close();
    }

    private boolean sendMessage(Message msg, InetAddress address, int port) {
        try {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(msg);
            oos.flush();

            byte[] buffer = bos.toByteArray();

            serverSocket.send(new DatagramPacket(buffer, buffer.length, address, port));

            oos.close();
            bos.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Message receiveMessage() {
        if (!up || serverSocket.isClosed()) return null;
        try {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            serverSocket.receive(packet);

            clientAddress = packet.getAddress();
            clientPort = packet.getPort();

            final ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
            final ObjectInputStream ois = new ObjectInputStream(bis);

            Message msg = (Message) ois.readObject();

            ois.close();
            bis.close();

            clientID = msg.ID;

            return msg;
        } catch (IOException | ClassNotFoundException e) {
            return new Message(MessageType.ERROR, null, e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();

//        server.up("localhost", 5000);
        server.up("192.168.1.159", 5000);
    }
}
