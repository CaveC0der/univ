package client.controller;

import common.RowCalculatorService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Controller {
    private final Registry registry;
    public final RowCalculatorService rowCalculatorService;
    public final String hostAddress;

    public Controller(String hostname, int port) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(hostname, port);
        rowCalculatorService = (RowCalculatorService) registry.lookup("RowCalculator");
        hostAddress = hostname + ':' + port;
    }
}
