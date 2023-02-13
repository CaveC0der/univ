package client.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PopUp extends JDialog {
    private JPanel main;
    private JButton okButton;
    private JLabel message;

    public PopUp(String title, String msg) {
        setContentPane(main);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        setLocation(520, 220);
        setResizable(false);
        setTitle(title);

        message.setText(msg);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        okButton.addActionListener((ActionEvent event) -> dispose());

        pack();
        setVisible(true);
    }
}
