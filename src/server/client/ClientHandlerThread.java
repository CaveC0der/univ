package server.client;

import message.*;
import server.rowcalculator.RowCalculator;
import server.view.ServerFrame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class ClientHandlerThread extends Thread {
    public final String ID;
    private final Socket socket;
    private final ServerFrame frame;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    private boolean up = false;
    private final int THRESHOLD = 5;
    private int unrecognized = 0;

    public ClientHandlerThread(Socket socket, ServerFrame frame) {
        ID = UUID.randomUUID().toString();
        this.socket = socket;
        this.frame = frame;
        try {
            fromClient = new ObjectInputStream(socket.getInputStream());
            toClient = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        up = true;
        try {
            while (up) {
                Message msg = receiveMessage();

                if (!up) return;

                switch (msg.type) {
                    case MESSAGE -> System.out.println("Message received from client ( " + ID + " ): " + msg.data());
                    case CONNECTION -> {
                        System.out.println("ConnectionMessage received from client ( " + ID + " )");
                        sendMessage(new IDMessage(ID));
                    }
                    case CALCULATION_REQUEST -> {
                        System.out.println("CalculationRequest received from client ( " + ID + " )");
                        double[] data = ((CalculationRequest) msg).data();
                        sendMessage(new CalculationResult(RowCalculator.calculate(
                                data[0],
                                data[1],
                                data[2],
                                data[3]
                        )));
                    }
                    case SHUTDOWN -> {
                        System.out.println("ShutdownMessage received from client ( " + ID + " )");
                        shutdown();
                    }
                    default -> {
                        System.out.println("UNRECOGNIZED MessageType received from client ( " + ID + " )");
                        if (++unrecognized == THRESHOLD) shutdown();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void interrupt() {
        shutdown();
        super.interrupt();
    }

    private void shutdown() {
        up = false;
        try {
            fromClient.close();
            toClient.close();
            if (!socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.removeClientFromList(socket.getInetAddress().getHostAddress(), ID);
        System.out.println("client ( " + ID + " ) disconnected");
    }

    private boolean sendMessage(Message msg) {
        try {
            toClient.writeObject(msg);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("sendMessage(): no client found");
            return false;
        }
    }

    private Message receiveMessage() {
        try {
            return (Message) fromClient.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new Message(MessageType.ERROR, e.getMessage());
        }
    }
}
