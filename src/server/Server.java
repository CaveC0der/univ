package server;

import common.RowCalculatorService;
import server.service.RowCalculator;

import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void startRowCalculatorService(int port) {
        RowCalculatorService rowCalculatorService = new RowCalculator();

        try {
            var stub = (RowCalculatorService) UnicastRemoteObject.exportObject(rowCalculatorService, port + 1);
            var registry = LocateRegistry.createRegistry(port);
            registry.bind("RowCalculator", stub);
            System.out.println("bound <RowCalculatorService> on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        startRowCalculatorService(5000);
    }
}
