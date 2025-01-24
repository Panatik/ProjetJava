import java.awt.*;
import javax.swing.*;

public class Form extends JFrame {
    private DataBaseManager dbmanager; 

    public Form() {
        dbmanager = new DataBaseManager();

        JFrame frame = new JFrame("IStore");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.GRAY);

        JLabel titleLabel = new JLabel("IStore", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Style du titre
        panel.add(titleLabel, BorderLayout.NORTH);

        ImageIcon imageIcon = new ImageIcon(getClass().getResource("Skyrim-Logo.png"));
        JLabel imageLabel = new JLabel(imageIcon, JLabel.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);

        LoginFrame();
    }

    public void LoginFrame(){
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
        
        JLabel errorLabel = new JLabel("", JLabel.CENTER);
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
                errorLabel.setText("Username or Password missing");
            } else if (dbmanager.VerifyLogin(email, password)){
                errorLabel.setText("Connection réussie");
            } else {
                errorLabel.setText("Connection pas réussie");
            }
        });

        registerButton.addActionListener(e -> {
            frame.dispose();
            RegisterFrame();
        });
    }

    public void RegisterFrame(){
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

        JButton loginButton = new JButton("Login");
        gridPanel.add(loginButton);

        JLabel errorLabel = new JLabel("", JLabel.CENTER);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 20));
        errorLabel.setForeground(Color.BLACK);
        gridPanel.add(errorLabel);

        gridPanel.add(new JLabel(""));

        JButton registerButton = new JButton("Register");
        gridPanel.add(registerButton);

        panel.add(gridPanel, BorderLayout.CENTER);
        frameRegister.add(panel);
        frameRegister.setVisible(true);

        loginButton.addActionListener(e -> {
            frameRegister.dispose();
            LoginFrame();
        });

        registerButton.addActionListener(e -> {
            String email = new String(emailField.getText());
            String login = new String(usernameField.getText());
            String passwd = new String(passwordField.getPassword());

            dbmanager.AddUser(email, login, passwd);

            System.out.println(login);
        });
    }


}
