package server.view;

import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ServerFrame extends JFrame {
    private final Server server;

    private JPanel main;

    private JPanel buttons;
    private JButton shutdownButton;

    private JPanel info;
    private JLabel socketAdress;

    private JScrollPane clientsScrollPane;
    private final DefaultListModel<String> clients;
    private JList<String> clientsList;

    public ServerFrame(Server server) {
        this.server = server;
        this.socketAdress.setText(server.getLocalSocketAddress());
        setTitle("Row Calculator Server");
        clients = new DefaultListModel<>();
        clientsList.setModel(clients);
        clientsList.setBackground(new Color(238, 238, 238, 0));
        setContentPane(main);
        setLocation(1200, 200);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        clientsScrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

        shutdownButton.addActionListener((ActionEvent event) -> {
            server.shutdown();
            System.exit(0);
        });

        pack();
        setVisible(true);
    }

    public synchronized void addClientToList(String hostAddress, String ID) {
        clients.addElement(hostAddress + " | " + ID);
    }

    public synchronized void removeClientFromList(String hostAddress, String ID) {
        clients.removeElement(hostAddress + " | " + ID);
        repaint();
    }
}
