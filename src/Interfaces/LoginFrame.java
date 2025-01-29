package Interfaces;

import Users.Admin;
import Users.Employee;
import Users.User;
import java.awt.*;
import javax.swing.*;

public class LoginFrame extends Form {
    private User currentUser;
    private JLabel errorLabel;

    public LoginFrame() {
        super();


        JFrame frame = new JFrame("IStore Login / register");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.GRAY);


        JLabel titleLabel = new JLabel("IStore", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Style du titre
        panel.add(titleLabel, BorderLayout.NORTH);


        //// Ajouter une grille au Centre
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(12, 1)); 
        gridPanel.setBackground(Color.GRAY);

        gridPanel.add(new JLabel(""));

        gridPanel.add(new JLabel("Username:", JLabel.CENTER));
        JTextField usernameField = new JTextField();
        gridPanel.add(usernameField);

        gridPanel.add(new JLabel("Password :", JLabel.CENTER));
        JPasswordField passwordField = new JPasswordField();
        gridPanel.add(passwordField);

        gridPanel.add(new JLabel(""));

        JButton loginButton = new JButton("Login");
        gridPanel.add(loginButton);
        
        errorLabel = new JLabel("", JLabel.CENTER);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 20));
        errorLabel.setForeground(Color.BLACK);
        gridPanel.add(errorLabel);

        gridPanel.add(new JLabel("")); 

        JButton registerButton = new JButton("Register");
        gridPanel.add(registerButton);
        
        panel.add(gridPanel, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);


        loginButton.addActionListener(e -> {
            String email = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
        
            if (email.isEmpty() || password.isEmpty()) {
                setErrorLabel_Login("Username or Password missing");
                return; 
            }
            if (!verify_email(email)) {
                setErrorLabel_Login("Email invalide");
                return;
            }
            currentUser = user_methods.VerifyLogin(email, password);
            System.out.println("test print");
            if (currentUser != null) {
                setErrorLabel_Login("Connexion réussie");
                System.out.println("connected");
                if (currentUser instanceof Admin) {
                    Admin admin = (Admin) currentUser;
                    admin.testdef();
                    frame.dispose();
                    //Admindashboard(admin);
                    AdminDashboard adminDashboard = new AdminDashboard(admin);
                } else if (currentUser instanceof Employee) {
                    Employee employee = (Employee) currentUser;
                    employee.testdefemployee();
                    frame.dispose();
                   //EmployeeDashboard(employee);
                   EmployeeDashboard employeeDashboard = new EmployeeDashboard(employee);
                }
            } else {
                setErrorLabel_Login("Utilisateur inconnu");

            }
        });

        

        registerButton.addActionListener(e -> {
            frame.dispose();
            RegisterFrame registerframe = new RegisterFrame();
        });
    }
    
    private void setErrorLabel_Login(String output) {
        errorLabel.setText(output);
    }


}
