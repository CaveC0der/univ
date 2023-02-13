package client.controller;

import message.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Controller {
    private final Socket socket;
    private final ObjectOutputStream toServer;
    private final ObjectInputStream fromServer;
    public final String ID;

    public Controller(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        toServer = new ObjectOutputStream(socket.getOutputStream());
        fromServer = new ObjectInputStream(socket.getInputStream());
        toServer.writeObject(new ConnectionMessage());
        ID = ((IDMessage) fromServer.readObject()).data();
    }

    public void shutdown() {
        try {
            toServer.writeObject(new ShutdownMessage());
            fromServer.close();
            toServer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendCalculationRequest(double[] data) {
        try {
            toServer.writeObject(new CalculationRequest(data));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Double receiveCalculationResult() {
        try {
            return ((CalculationResult) fromServer.readObject()).data();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getHostAddress() {
        return socket.getInetAddress().getHostAddress();
    }
}
