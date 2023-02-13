package client.view;

import client.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private JLabel ID;

    private final Controller controller;

    public RowCalculatorFrame(Controller ctrl) {
        super("Row Calculator");
        controller = ctrl;

        setContentPane(main);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(500, 200);
        setResizable(false);

        hostAddress.setText("Host address: " + controller.getHostAddress());
        ID.setText("ID: " + controller.ID);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.shutdown();
                super.windowClosing(e);
            }
        });

        submitButton.addActionListener((ActionEvent event) -> {
            String[] dataStrings = new String[]{aField.getText(), bField.getText(), xField.getText(), yField.getText()};
            if (Arrays.stream(dataStrings).anyMatch(String::isEmpty)) {
                new PopUp("Error!", "One or more fields are empty!");
                return;
            }
            boolean messageStatus = controller.sendCalculationRequest(
                    Arrays
                            .stream(dataStrings)
                            .mapToDouble(Double::parseDouble)
                            .toArray()
            );
            if (messageStatus)
                new PopUp("Calculation Result", controller.receiveCalculationResult().toString());
            else
                new PopUp("Error!", "Something went wrong while sending message to the server.");
        });

        pack();
        setVisible(true);
    }
}
