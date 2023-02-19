package client.view;

import client.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.Arrays;

public class RowCalculatorFrame extends JFrame {
    private JPanel main;
    private JButton submitButton;
    private JPanel inputs;
    private JPanel labels;
    private JPanel fields;
    private JTextField aField;
    private JTextField bField;
    private JTextField xField;
    private JTextField yField;
    private JPanel buttons;
    private JPanel info;
    private JLabel hostAddress;

    private final Controller controller;

    public RowCalculatorFrame(Controller ctrl) {
        super("Row Calculator");
        controller = ctrl;

        setContentPane(main);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(500, 200);
        setResizable(false);

        hostAddress.setText("Host address: " + controller.hostAddress);

        submitButton.addActionListener((ActionEvent event) -> {
            String[] dataStrings = new String[]{aField.getText(), bField.getText(), xField.getText(), yField.getText()};
            if (Arrays.stream(dataStrings).anyMatch(String::isEmpty)) {
                new PopUp("Error!", "One or more fields are empty!");
                return;
            }
            double[] data = Arrays.stream(dataStrings).mapToDouble(Double::parseDouble).toArray();
            try {
                new PopUp(
                        "Calculation Result",
                        String.valueOf(controller.rowCalculatorService.calculate(data[0], data[1], data[2], data[3]))
                );
            } catch (RemoteException e) {
                new PopUp("Error!", "Something went wrong while sending message to the server.");
            }
        });

        pack();
        setVisible(true);
    }
}
