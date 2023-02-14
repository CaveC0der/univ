package client;

import client.controller.Controller;
import client.view.RowCalculatorFrame;

public class App {
    private Controller controller;

    private RowCalculatorFrame frame;

    public App(String hostname, int port) {
        try {
            controller = new Controller(hostname, port);

            frame = new RowCalculatorFrame(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new App("localhost", 5000);
//        new App("192.168.1.159", 5000);
    }
}
