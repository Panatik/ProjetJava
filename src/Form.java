import java.awt.*;
import javax.swing.*;

public class Form extends JFrame {
    private DataBaseManager dbmanager; 
    private User currentUser;
    private JLabel errorLabel;

    public Form() {
        dbmanager = new DataBaseManager();

        //JFrame frame = new JFrame("IStore");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(500, 500);
        //frame.setLocationRelativeTo(null);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//
        //JPanel panel = new JPanel();
        //panel.setLayout(new BorderLayout());
        //panel.setBackground(Color.GRAY);
//
        //JLabel titleLabel = new JLabel("IStore", JLabel.CENTER);
        //titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Style du titre
        //panel.add(titleLabel, BorderLayout.NORTH);
//
        //ImageIcon imageIcon = new ImageIcon(getClass().getResource("ressources/Skyrim-Logo.png"));
        //JLabel imageLabel = new JLabel(imageIcon, JLabel.CENTER);
        //panel.add(imageLabel, BorderLayout.CENTER);
//
        //frame.add(panel);
        //frame.setVisible(true);

        LoginFrame();
    }

    public void setErrorLabel(String output) {
            errorLabel.setText(output);
    }

    public boolean  verify_email(String email) {
        if (!dbmanager.verify_email_format(email)) {
            setErrorLabel("Email invalide");
            System.out.println("Email format not valid");
            return false;
        }
        return true;
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
                setErrorLabel("Username or Password missing");
                return; 
            }
            if (!verify_email(email)) {
                setErrorLabel("Email invalide");
                return;
            }
            currentUser = dbmanager.VerifyLogin(email, password);

            if (currentUser != null) {
                setErrorLabel("Connexion réussie");
                if ("user".equals(currentUser.getRole())) {
                    frame.dispose();
                    //Userdashboard(currentUser);
                } else if ("admin".equals(currentUser.getRole())) {
                    frame.dispose();
                    String output = dbmanager.Add_employee_whitelist("test@gmail.com");
                    System.out.println(output);
                    Admindashboard(currentUser);

                }
                //if (currentUser instanceof Admin) {
                //    Admin admin = (Admin) currentUser;
                //    admin.testdefadmin();
                //    frame.dispose();
                //    //Admindashboard(admin);
                //    this.dbmanager.Add_employee_whitelist("test@gmail.com");
                //} else if (currentUser instanceof Employee) {
                //    Employee employee = (Employee) currentUser;
                //    employee.testdefemployee();
                //}

                
            } else {
                setErrorLabel("Utilisateur inconnu");
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
                setErrorLabel("Email invalide");
                return; // Arrêter l'exécution si l'email est invalide
            }
        
            // Ajouter l'utilisateur dans la base de données
            String output = dbmanager.AddUser(email, usrname, passwd, "");
            setErrorLabel(output);
        
            if (output.equals("Utilisateur créé avec succès")) {
                Timer timer = new Timer(1000, event -> {
                    frameRegister.dispose();
                    LoginFrame();
                });
                timer.setRepeats(false); // On exécute le Timer une seule fois
                timer.start();
            }
        });
    }

    private void Admindashboard (User admin) {
        JFrame frame = new JFrame("Admin Panel - IStore");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Admin Panel - logged in as: " + admin.getUsername(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 2, 15, 15)); // Grid: 5 rows, 2 columns
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton createUserButton = new JButton("ajouter employee a la whiteliste");
        JButton createStoreButton = new JButton("Créer un magasin");
        JButton deleteStoreButton = new JButton("Supprimer un magasin");
        JButton assignEmployeeButton = new JButton("Assigner des employés à un magasin");
        JButton createInventoryItemButton = new JButton("Créer un item dans l'inventaire");
        JButton deleteInventoryItemButton = new JButton("Supprimer un item de l'inventaire");
        JButton updateUserButton = new JButton("Mettre à jour un utilisateur");
        JButton deleteUserButton = new JButton("Supprimer un compte utilisateur");
        JButton manageWhitelistButton = new JButton("Gérer les emails whitelistés");

        buttonPanel.add(createUserButton);
        buttonPanel.add(createStoreButton);
        buttonPanel.add(deleteStoreButton);
        buttonPanel.add(assignEmployeeButton);
        buttonPanel.add(createInventoryItemButton);
        buttonPanel.add(deleteInventoryItemButton);
        buttonPanel.add(updateUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(manageWhitelistButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }









}
