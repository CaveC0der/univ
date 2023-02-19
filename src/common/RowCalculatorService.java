package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RowCalculatorService extends Remote {
    double calculate(double a, double b, double x, double y) throws RemoteException;
}
