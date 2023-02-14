package server;

import server.client.ClientHandlerThread;
import server.view.ServerFrame;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private ServerFrame frame;
    private ArrayList<Thread> threads;
    private boolean up = false;

    public void up(String hostname, int port) {
        threads = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(
                    port,
                    50, // default value, check ServerSocket.java
                    new InetSocketAddress(hostname, port).getAddress()
            );

            frame = new ServerFrame(this);

            System.out.println("server: " + serverSocket.getLocalSocketAddress().toString());

            up = true;
            while (up) {
                Socket clientSocket = serverSocket.accept();

                ClientHandlerThread client = new ClientHandlerThread(clientSocket, frame);
                frame.addClientToList(clientSocket.getInetAddress().getHostAddress(), client.ID);

                System.out.println("client ( "
                        + clientSocket.getInetAddress().getHostAddress() + " / " + client.ID
                        + " ) connected");

                client.start();
                threads.add(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public String getLocalSocketAddress() {
        return serverSocket.getLocalSocketAddress().toString();
    }


    public synchronized void shutdown() {
        up = false;
        threads.forEach((Thread thread) -> {
            if (thread.isAlive()) thread.interrupt();
        });
    }

    public static void main(String[] args) {
        Server server = new Server();

        server.up("localhost", 5000);
//        server.up("192.168.1.159", 5000);
    }
}
