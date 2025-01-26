import java.awt.*;
import javax.swing.*;

public class Form extends JFrame {
    //private DataBaseManager dbmanager; 
    private User currentUser;
    
    private JLabel errorLabel;
    
    private final Tools tools;
    private final User user_methods;

    public Form() {
        tools = new Tools();
        user_methods = new User();
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
        if (!tools.verify_email_format(email)) {
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
            currentUser = user_methods.VerifyLogin(email, password);

            if (currentUser != null) {
                setErrorLabel("Connexion réussie");
                if (currentUser instanceof Admin) {
                    Admin admin = (Admin) currentUser;
                    admin.testdef();
                    frame.dispose();
                    Admindashboard(admin);

                    //test
                    //String output = admin.Add_employee_whitelist("test2@test.com");
                    //System.out.println(output);
                } else if (currentUser instanceof Employee) {
                    Employee employee = (Employee) currentUser;
                    employee.testdefemployee();
                }

                
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
            String output = user_methods.AddUser(email, usrname, passwd, "");
            setErrorLabel(output);
        
            if (output.equals("Utilisateur creer avec succes")) {
                Timer timer = new Timer(1000, event -> {
                    frameRegister.dispose();
                    LoginFrame();
                });
                timer.setRepeats(false); // On exécute le Timer une seule fois
                timer.start();
            }
        });
    }

    private void Admindashboard (Admin admin) {
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

        //done
        JButton AddwhitelistUserButton = new JButton("ajouter employee a la whiteliste");
        JButton DisplayEmployeesButton = new JButton("afficher tous les employees");
        JButton createStoreButton = new JButton("Créer un magasin");
        JButton DisplayStores = new JButton("afficher tous les magasins");
        JButton deleteStoreButton = new JButton("Supprimer un magasin");


        //todo
        JButton assignEmployeeButton = new JButton("Assigner des employés à un magasin");
        JButton createInventoryItemButton = new JButton("Créer un item dans l'inventaire");
        JButton deleteInventoryItemButton = new JButton("Supprimer un item de l'inventaire");
        JButton updateUserButton = new JButton("Mettre à jour un utilisateur");
        JButton deleteUserButton = new JButton("Supprimer un compte utilisateur");
        JButton manageWhitelistButton = new JButton("Gérer les emails whitelistés");

        buttonPanel.add(AddwhitelistUserButton);
        buttonPanel.add(DisplayEmployeesButton);
        buttonPanel.add(DisplayStores);
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


        AddwhitelistUserButton.addActionListener(e -> {
            String email = JOptionPane.showInputDialog("Enter the email of the employee to add:");
            String output = admin.Add_employee_whitelist(email);
            JOptionPane.showMessageDialog(frame, output);
        });

        DisplayEmployeesButton.addActionListener(e -> {
            try {
                // Retrieve user data from the admin class
                Object[][] data = admin.get_format_users_data();
                String[] columnNames = {"ID", "Email", "Pseudo", "Role", "Store_id"};
                
                DisplayJtable(data, columnNames);
                
            } catch (Exception ex) {
                //ex.printStackTrace(); // debug print
                JOptionPane.showMessageDialog(frame, 
                    "Erreur lors de l'affichage des utilisateurs : " + ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });


        createStoreButton.addActionListener(e -> {
            String store_name = JOptionPane.showInputDialog("Enter the name of the store:");
            String output = admin.Create_store(store_name);
            JOptionPane.showMessageDialog(frame, output);
        });


        DisplayStores.addActionListener(e -> {
            try {
                Object[][] data = admin.get_format_stores_data();
                String[] columnNames = {"ID", "Nom du magasin"};
                
                DisplayJtable(data, columnNames);
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, 
                    "Erreur lors de l'affichage des magasins : " + ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteStoreButton.addActionListener(e -> {
            String store_id_str = JOptionPane.showInputDialog("Enter the id of the store to delete");
            int store_id = Integer.parseInt(store_id_str);
            String output = admin.Delete_Store(store_id);
            JOptionPane.showMessageDialog(frame, output);
        });
        
    }


    public void DisplayJtable(Object[][] data, String[] columnNames) {
        JTable table = new JTable(data, columnNames);
                
        // Customize table appearance (optional)
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add table to JScrollPane for scrollability
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Create a new JFrame to display the table
        JFrame tableFrame = new JFrame("Liste des utilisateurs (role: User)");
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.setSize(600, 400);
        tableFrame.add(scrollPane, BorderLayout.CENTER);
        
        // Make the frame visible
        tableFrame.setVisible(true);
    }









}
