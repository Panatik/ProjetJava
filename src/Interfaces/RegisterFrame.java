package Interfaces;

import java.awt.*;
import javax.swing.*;

public class RegisterFrame extends Form{
    private JLabel errorLabel;

    public RegisterFrame() {
        super();
        JFrame frameRegister = new JFrame("IStore Register");
        frameRegister.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameRegister.setSize(400, 600);
        frameRegister.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.GRAY);


        JLabel titleLabel = new JLabel("IStore Register", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Style du titre
        panel.add(titleLabel, BorderLayout.NORTH);

        //// Ajouter une grille au Centre
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(12, 1));
        gridPanel.setBackground(Color.GRAY);

        gridPanel.add(new JLabel(""));

        gridPanel.add(new JLabel("Email:", JLabel.CENTER));
        JTextField emailField = new JTextField();
        gridPanel.add(emailField);

        gridPanel.add(new JLabel("Username:", JLabel.CENTER));
        JTextField usernameField = new JTextField();
        gridPanel.add(usernameField);

        gridPanel.add(new JLabel("Password :", JLabel.CENTER));
        JPasswordField passwordField = new JPasswordField();
        gridPanel.add(passwordField);

        gridPanel.add(new JLabel(""));

        errorLabel = new JLabel("", JLabel.CENTER);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 20));
        errorLabel.setForeground(Color.BLACK);
        gridPanel.add(errorLabel);

        gridPanel.add(new JLabel(""));

        JButton registerButton = new JButton("Register");
        gridPanel.add(registerButton);

        panel.add(gridPanel, BorderLayout.CENTER);
        frameRegister.add(panel);
        frameRegister.setVisible(true);

        registerButton.addActionListener(e -> {
            String email = emailField.getText();
            String usrname = usernameField.getText();
            String passwd = String.valueOf(passwordField.getPassword());
        
            // Vérifier l'email avant de continuer
            if (!verify_email(email)) {
                setErrorLabel_register("Email invalide");
                return; // Arrêter l'exécution si l'email est invalide
            }
        
            // Ajouter l'utilisateur dans la base de données
            String output = user_methods.AddUser(email, usrname, passwd, "");
            setErrorLabel_register(output);
        
            if (output.equals("Utilisateur creer avec succes")) {
                Timer timer = new Timer(1000, event -> {
                    frameRegister.dispose();
                    LoginFrame frame = new LoginFrame();
                });
                timer.setRepeats(false); // On exécute le Timer une seule fois
                timer.start();
            }
        });
    }

    
    public void setErrorLabel_register(String output) {
        errorLabel.setText(output);
    }

}
