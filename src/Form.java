import java.awt.*;
import javax.swing.*;

public class Form extends JFrame {
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

        //main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        //top panel
        JPanel TopPanel = new JPanel(new BorderLayout());
        TopPanel.setBackground(Color.LIGHT_GRAY);

        //titre
        JLabel titleLabel = new JLabel("Admin Panel - logged in as: " + admin.getUsername(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        TopPanel.add(titleLabel, BorderLayout.CENTER);

        //boutton disconnect
        JButton disconnect = new JButton("Disconnect");
        disconnect.setPreferredSize(new Dimension(120, 30));
        disconnect.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15));
        TopPanel.add(disconnect, BorderLayout.EAST);

        mainPanel.add(TopPanel, BorderLayout.NORTH);

        JPanel magasinPanel = new JPanel(new GridLayout(5, 2, 15, 15)); // Grid: 5 rows, 2 columns
        magasinPanel.setBackground(Color.WHITE);
        magasinPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel inventoryPanel = new JPanel(new GridLayout(5, 2, 15, 15)); // Grid: 5 rows, 2 columns
        inventoryPanel.setBackground(Color.WHITE);
        inventoryPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel userPanel = new JPanel(new GridLayout(5, 2, 15, 15)); // Grid: 5 rows, 2 columns
        userPanel.setBackground(Color.WHITE);
        userPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        //done
        JButton AddwhitelistUserButton = new JButton("ajouter employee a la whiteliste");
        JButton DisplayEmployeesButton = new JButton("afficher tous les employees");
        JButton createStoreButton = new JButton("Créer un magasin");
        JButton DisplayStores = new JButton("afficher tous les magasins");
        JButton deleteStoreButton = new JButton("Supprimer un magasin");
        JButton assignEmployeeButton = new JButton("Assigner des employés à un magasin");
        JButton deleteEmployeeButton = new JButton("supprimer un employee");
        JButton updateUserButton = new JButton("Mettre à jour un utilisateur");
        JButton DisplayInventoryItemsButton = new JButton("afficher tous les items de l'inventaire");
        JButton updateInventoryItemButton = new JButton("Mettre à jour un item de l'inventaire");
        JButton deleteInventoryItemButton = new JButton("Supprimer un item de l'inventaire");


        JButton createInventoryItemButton = new JButton("Créer un item dans l'inventaire");
        //todo

        userPanel.add(DisplayEmployeesButton); // done
        magasinPanel.add(DisplayStores);  // done
        inventoryPanel.add(DisplayInventoryItemsButton); // done

        userPanel.add(AddwhitelistUserButton);  // done
        userPanel.add(assignEmployeeButton);  // done

        userPanel.add(updateUserButton); // done
        inventoryPanel.add(updateInventoryItemButton); //done

        magasinPanel.add(createStoreButton);  // done
        inventoryPanel.add(createInventoryItemButton); 

        userPanel.add(deleteEmployeeButton);  // done
        magasinPanel.add(deleteStoreButton);  // done
        inventoryPanel.add(deleteInventoryItemButton);

        JTabbedPane tabPanel = new JTabbedPane();
        tabPanel.addTab("Utilisateurs", userPanel);
        tabPanel.addTab("Magasin", magasinPanel);
        tabPanel.addTab("Inventaire", inventoryPanel);
        mainPanel.add(tabPanel, BorderLayout.CENTER);
        //mainPanel.add(magasinPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);


        disconnect.addActionListener(e -> {
            frame.dispose();
            LoginFrame();
        });

        AddwhitelistUserButton.addActionListener(e -> {
            String email = JOptionPane.showInputDialog("Enter the email of the employee to add:");
            String output = admin.Add_employee_whitelist(email);
            JOptionPane.showMessageDialog(frame, output);
        });

        DisplayEmployeesButton.addActionListener(e -> {
            try {
                // Retrieve user data from the admin class
                Object[][] data = admin.get_format_users_data(admin.getRole());
                String[] columnNames = {"ID", "Email", "Pseudo", "Role", "Store_id"};
                
                DisplayJtable(data, columnNames, "Users");
                
            } catch (Exception ex) {
                //ex.printStackTrace(); // debug print
                JOptionPane.showMessageDialog(frame, 
                    "Erreur lors de l'affichage des utilisateurs : " + ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        DisplayInventoryItemsButton.addActionListener(e -> {
            try {
                // Retrieve inventory data from the admin class
                Object[][] data = admin.get_format_items_data(admin.getRole(), 0);
                String[] columnNames = {"ID", "Name", "Price", "Quantity", "Store_id"};
                
                DisplayJtable(data, columnNames, "Inventory Items");
                
            } catch (Exception ex) {
                //ex.printStackTrace(); // debug print
                JOptionPane.showMessageDialog(frame, 
                    "Erreur lors de l'affichage des items de l'inventaire : " + ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }

        });

        deleteInventoryItemButton.addActionListener(e -> {
            String item_id_str = JOptionPane.showInputDialog("Enter the id of the item to delete");
            int item_id = Integer.parseInt(item_id_str);
            if (user_methods.is_item_id_valid(item_id)) {
                String output = admin.Delete_item(item_id);
                JOptionPane.showMessageDialog(frame, output);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid item ID. Please enter a valid item ID.");
            }

        });

        deleteEmployeeButton.addActionListener(e -> {
            String user_id_str = JOptionPane.showInputDialog("Enter the id of the user to delete");
            int user_id = Integer.parseInt(user_id_str);
            if (user_methods.is_user_id_valid(user_id)) {
                String output = admin.Delete_user(user_id);
                JOptionPane.showMessageDialog(frame, output);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid user ID. Please enter a valid user ID.");
            }
        });

        createInventoryItemButton.addActionListener(e -> {
            try {
                String item_name = JOptionPane.showInputDialog("Enter the name of the item:");
                
                float price;
                try {
                    price = Float.parseFloat(JOptionPane.showInputDialog("Enter the price of the item (1.00, 2.99, 3.99, ...):"));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid price. Please enter a valid number.");
                    return;
                }
        
                int quantity;
                try {
                    quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter the quantity of the item (1, 2, 3, ...):"));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid quantity. Please enter a valid integer.");
                    return;
                }
        
                int store_id;
                try {
                    store_id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the store for the item:"));
                    if (!user_methods.is_store_id_valid(store_id)) {
                        JOptionPane.showMessageDialog(frame, "Invalid store ID. Please enter a valid store ID.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid store ID. Please enter a valid integer.");
                    return;
                }
        
                String output = admin.Create_item(item_name, price, quantity, store_id);
                JOptionPane.showMessageDialog(frame, output);
                
            } catch (Exception ex) {
                // Si une exception inattendue se produit
                JOptionPane.showMessageDialog(frame, "An error occurred. Please try again.");
                ex.printStackTrace();
            }
        });


        createStoreButton.addActionListener(e -> {
            String store_name = JOptionPane.showInputDialog("Enter the name of the store:");
            String output = admin.Create_store(store_name);
            JOptionPane.showMessageDialog(frame, output);
        });

        updateUserButton.addActionListener(e -> {
            try {
                int user_id = Integer.parseInt(JOptionPane.showInputDialog("Enter the ID of the user to update:"));
                if (user_methods.is_user_id_valid(user_id)) {
                    int field_index = Integer.parseInt(JOptionPane.showInputDialog("Select the field to update:\n1- Email\n2- Username\n3- Password"));
                    String newValue = JOptionPane.showInputDialog("Enter the new value:");
                    if (field_index >= 1 && field_index <= 3) {
                        String output = admin.Update_user(user_id, field_index, newValue);
                        JOptionPane.showMessageDialog(frame, output);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid field index. Please enter a number between 1 and 3.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid user ID. Please enter a valid user ID.");
                }
            } catch (Exception ex) {
                ex.printStackTrace(); // debug print
            }
        });

        updateInventoryItemButton.addActionListener(e -> {
            try {
                int item_id = Integer.parseInt(JOptionPane.showInputDialog("Enter the ID of the item to update:"));
                if (user_methods.is_item_id_valid(item_id)) {
                    int field_index = Integer.parseInt(JOptionPane.showInputDialog("Select the field to update:\n1- name\n2- price\n3- quantity\n4- store_id"));
                    String newValue = JOptionPane.showInputDialog("Enter the new value:");
                    if (field_index >= 1 && field_index <= 4) {
                        String output = admin.update_item(item_id, field_index, newValue);
                        JOptionPane.showMessageDialog(frame, output);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid field index. Please enter a number between 1 and 4.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid item ID. Please enter a valid item ID.");
                }
            } catch (Exception ex) {
                ex.printStackTrace(); // debug print
            }
        });
            


        DisplayStores.addActionListener(e -> {
            try {
                Object[][] data = admin.get_format_stores_data();
                String[] columnNames = {"ID", "Nom du magasin"};
                
                DisplayJtable(data, columnNames, "Stores");
                
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
            if (user_methods.is_store_id_valid(store_id)) {
                String output = admin.Delete_Store(store_id);
                JOptionPane.showMessageDialog(frame, output);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid store ID. Please enter a valid store ID.");
            }
        });


        assignEmployeeButton.addActionListener(e -> {
            String user_id_str = JOptionPane.showInputDialog("Enter the id of the user to assign to a store");
            int user_id = Integer.parseInt(user_id_str);
            if (user_methods.is_user_id_valid(user_id)) {
                String store_id_str = JOptionPane.showInputDialog("Enter the id of the store to assign to");
                int store_id = Integer.parseInt(store_id_str);
                if (user_methods.is_store_id_valid(store_id)) {
                    String output = admin.Assign_employee_to_store(user_id, store_id);
                    JOptionPane.showMessageDialog(frame, output);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid store ID. Please enter a valid store ID.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid user ID. Please enter a valid user ID.");
            }
        });
        
    }


    public void DisplayJtable(Object[][] data, String[] columnNames, String title) {
        JTable table = new JTable(data, columnNames);
                
        // Customize table appearance (optional)
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add table to JScrollPane for scrollability
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Create a new JFrame to display the table
        JFrame tableFrame = new JFrame(title);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.setSize(600, 400);
        tableFrame.add(scrollPane, BorderLayout.CENTER);
        
        // Make the frame visible
        tableFrame.setVisible(true);
    }









}
