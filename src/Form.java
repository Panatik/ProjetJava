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
                    frame.dispose();
                    EmployeeDashboard(employee);
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

        JPanel magasinPanel = new JPanel(new BorderLayout());
        JPanel magasinDisplayPanel = new JPanel(new BorderLayout());
        JPanel magasinButtonPanel = new JPanel(new GridLayout(5, 2, 15, 15)); // Grid: 5 rows, 2 columns
        magasinButtonPanel.setBackground(Color.WHITE);
        magasinButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel inventoryPanel = new JPanel(new BorderLayout());
        JPanel inventoryDisplayPanel = new JPanel(new BorderLayout());
        JPanel inventoryButtonPanel = new JPanel(new GridLayout(5, 2, 15, 15)); // Grid: 5 rows, 2 columns
        inventoryButtonPanel.setBackground(Color.WHITE);
        inventoryButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel userPanel = new JPanel(new BorderLayout());
        JPanel userDisplayPanel = new JPanel(new BorderLayout());
        JPanel userButtonPanel = new JPanel(new GridLayout(5, 2, 15, 15)); // Grid: 5 rows, 2 columns
        userButtonPanel.setBackground(Color.WHITE);
        userButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        //done
        JButton AddwhitelistUserButton = new JButton("ajouter employee a la whiteliste");
        JButton createStoreButton = new JButton("Créer un magasin");
        JButton deleteStoreButton = new JButton("Supprimer un magasin");
        JButton assignEmployeeButton = new JButton("Assigner des employés à un magasin");
        JButton deleteEmployeeButton = new JButton("supprimer un employee");
        JButton updateUserButton = new JButton("Mettre à jour un utilisateur");
        JButton updateInventoryItemButton = new JButton("Mettre à jour un item de l'inventaire");
        JButton deleteInventoryItemButton = new JButton("Supprimer un item de l'inventaire");


        JButton createInventoryItemButton = new JButton("Créer un item dans l'inventaire");
        //todo


        userButtonPanel.add(AddwhitelistUserButton);  // done
        userButtonPanel.add(assignEmployeeButton);  // done

        userButtonPanel.add(updateUserButton); // done
        inventoryButtonPanel.add(updateInventoryItemButton); //done

        magasinButtonPanel.add(createStoreButton);  // done
        inventoryButtonPanel.add(createInventoryItemButton);

        userButtonPanel.add(deleteEmployeeButton);  // done
        magasinButtonPanel.add(deleteStoreButton);  // done
        inventoryButtonPanel.add(deleteInventoryItemButton);

        //DISPLAY INVENTORY
        DisplayInventoryPanelMethod(admin, inventoryDisplayPanel, frame);
        //DISPLAY USER
        DisplayUserPanelMethod(admin, userDisplayPanel, frame);
        //DISPLAY MAGASIN
        DisplayMagasinPanelMethod(admin, magasinDisplayPanel, frame);

        //USER TITLE DISPLAY PANEL
        JLabel UserDisplayTitle = new JLabel("Users Table", JLabel.CENTER);
        UserDisplayTitle.setFont(new Font("Arial", Font.BOLD, 20));
        UserDisplayTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        userDisplayPanel.add(UserDisplayTitle, BorderLayout.NORTH);


        // test display whtelist // temp
        //JLabel WhitelistDisplayTitle = new JLabel("Employee Whitelist Table", JLabel.CENTER);
        //WhitelistDisplayTitle.setFont(new Font("Arial", Font.BOLD, 20));
        //WhitelistDisplayTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        //userDisplayPanel.add(WhitelistDisplayTitle, BorderLayout.NORTH);


        //MAGASIN TITLE DISPLAY PANEL
        JLabel MagasinDisplayTitle = new JLabel("Stores Table", JLabel.CENTER);
        MagasinDisplayTitle.setFont(new Font("Arial", Font.BOLD, 20));
        MagasinDisplayTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        magasinDisplayPanel.add(MagasinDisplayTitle, BorderLayout.NORTH);
        //INVENTORY TITLE DISPLAY PANEL
        JLabel InventoryDisplayTitle = new JLabel("Inventory Table", JLabel.CENTER);
        InventoryDisplayTitle.setFont(new Font("Arial", Font.BOLD, 20));
        InventoryDisplayTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        inventoryDisplayPanel.add(InventoryDisplayTitle, BorderLayout.NORTH);

        userPanel.add(userButtonPanel);
        userPanel.add(userDisplayPanel, BorderLayout.EAST);
        inventoryPanel.add(inventoryButtonPanel);
        inventoryPanel.add(inventoryDisplayPanel, BorderLayout.EAST);
        magasinPanel.add(magasinButtonPanel);
        magasinPanel.add(magasinDisplayPanel, BorderLayout.EAST);

        JTabbedPane tabPanel = new JTabbedPane();
        tabPanel.addTab("Utilisateurs", userPanel);
        tabPanel.addTab("Magasin", magasinPanel);
        tabPanel.addTab("Inventaire", inventoryPanel);
        mainPanel.add(tabPanel, BorderLayout.CENTER);


        frame.add(mainPanel);
        frame.setVisible(true);


        disconnect.addActionListener(e -> {
            frame.dispose();
            LoginFrame();
        });

        AddwhitelistUserButton.addActionListener(e -> {
            String email = JOptionPane.showInputDialog("Enter the email of the employee to add:");
            String output = admin.Add_employee_whitelist(email);
            DisplayUserPanelMethod(admin, userDisplayPanel, frame);
            JOptionPane.showMessageDialog(frame, output);
        });

        deleteInventoryItemButton.addActionListener(e -> {
            String item_id_str = JOptionPane.showInputDialog("Enter the id of the item to delete");
            int item_id = Integer.parseInt(item_id_str);
            if (user_methods.is_item_id_valid(item_id)) {
                String output = admin.Delete_item(item_id);
                DisplayInventoryPanelMethod(admin, inventoryDisplayPanel, frame);
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
                DisplayUserPanelMethod(admin, userDisplayPanel, frame);
                JOptionPane.showMessageDialog(frame, output);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid user ID. Please enter a valid user ID.", "Error", JOptionPane.ERROR_MESSAGE);
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
                DisplayInventoryPanelMethod(admin, inventoryDisplayPanel, frame);
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
            DisplayMagasinPanelMethod(admin, magasinDisplayPanel, frame);
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
                        DisplayUserPanelMethod(admin, userDisplayPanel, frame);
                        JOptionPane.showMessageDialog(frame, output);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid field index. Please enter a number between 1 and 3.", "Invalid", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid user ID. Please enter a valid user ID.", "Error", JOptionPane.ERROR_MESSAGE);
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
                        DisplayInventoryPanelMethod(admin, inventoryDisplayPanel, frame);
                        JOptionPane.showMessageDialog(frame, output);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid field index. Please enter a number between 1 and 4.", "Invalid", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid item ID. Please enter a valid item ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace(); // debug print
            }
        });


        deleteStoreButton.addActionListener(e -> {
            String store_id_str = JOptionPane.showInputDialog("Enter the id of the store to delete");
            int store_id = Integer.parseInt(store_id_str);
            if (user_methods.is_store_id_valid(store_id)) {
                String output = admin.Delete_Store(store_id);
                DisplayMagasinPanelMethod(admin, magasinDisplayPanel, frame);
                JOptionPane.showMessageDialog(frame, output);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid store ID. Please enter a valid store ID.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    DisplayUserPanelMethod(admin, userDisplayPanel, frame);
                    JOptionPane.showMessageDialog(frame, output);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid store ID. Please enter a valid store ID.", "Invalid", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid user ID. Please enter a valid user ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void EmployeeDashboard (Employee employee) {
        JFrame frame = new JFrame("Employee Panel - IStore");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Employee Panel - logged in as: " + employee.getUsername(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 2, 15, 15)); // Grid: 5 rows, 2 columns
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton DisplayusersButton = new JButton("Voir les informations des autres utilisateur");
        JButton updateUserButton = new JButton("Mettre à jour mon compte");
        JButton deleteUserButton = new JButton("Supprimer mon compte et me deconnecter");
        JButton DisplayStoresButton = new JButton("Acceder à mon magasins");
        JButton DisplayInventoryButton = new JButton("Voir l'inventaire de mon magasins");
        JButton updateItemQuantityButton = new JButton("Modifier la quantite d'un item");

        buttonPanel.add(DisplayusersButton);
        buttonPanel.add(updateUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(DisplayStoresButton);
        buttonPanel.add(DisplayInventoryButton);
        buttonPanel.add(updateItemQuantityButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        updateUserButton.addActionListener(e -> {
            try {
                int field_index = Integer.parseInt(JOptionPane.showInputDialog("Select the field to update:\n1- Email: " +employee.getEmail()+ "\n2- Username: " +employee.getUsername()+ "\n3- Password"));
                String newValue = JOptionPane.showInputDialog("Enter the new value:");
                if (field_index >= 1 && field_index <= 3) {
                    String output = employee.Update_user(employee.getUser_id(), field_index, newValue);
                    JOptionPane.showMessageDialog(frame, output);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid field index. Please enter a number between 1 and 3.");
                }
            } catch (Exception ex) {
                ex.printStackTrace(); // debug print
            }
        });

        deleteUserButton.addActionListener(e -> {
            String output = employee.delete_self(employee.getUser_id(), employee.getEmail());
            JOptionPane.showMessageDialog(frame, output);
            frame.dispose();
            LoginFrame();
        });



        //temporary buttons // test buttons
        DisplayusersButton.addActionListener(e -> {
            JPanel testpanel = new JPanel();
            Display_Employees_user_temp(employee, testpanel, frame);
        });

        DisplayStoresButton.addActionListener(e -> {
            JPanel testpanel = new JPanel();
            Display_Store_user_temp(employee, testpanel, frame);
        });

        DisplayInventoryButton.addActionListener(e -> {
            JPanel testpanel = new JPanel();
            Display_Inventory_user_store_temp(employee, testpanel, frame);
        });

        updateItemQuantityButton.addActionListener(e -> {
            try {
                int item_id = Integer.parseInt(JOptionPane.showInputDialog("Enter the ID of the item to update:"));
                if (user_methods.is_item_id_valid(item_id)) {
                    int newValue = Integer.parseInt(JOptionPane.showInputDialog("Enter the new value:"));
                        String output = employee.update_item_quantity(item_id, newValue);
                        JOptionPane.showMessageDialog(frame, output);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid field index. Please enter a number between 1 and 4.", "Invalid", JOptionPane.INFORMATION_MESSAGE);
                    }
            } catch (Exception ex) {
                ex.printStackTrace(); // debug print
            }
        });
    }


    //temporary methods to do
    
    public void Display_Employees_user_temp(Employee employee, JPanel EmployeeDisplayPanel, JFrame frame) {
        try {
            Object[][] data = employee.get_format_users_data(employee.getRole());
            String[] columnNames = {"ID", "Username", "Email", "Role", "Store_id"};
            // Display the table in a JScrollPane
            System.out.println("Display_Employees_user_temp here");




        } catch (Exception ex) {
            //ex.printStackTrace(); // debug print
            JOptionPane.showMessageDialog(frame,
                    "Erreur lors de l'affichage des utilisateurs : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public void Display_Store_user_temp(Employee employee, JPanel EmployeeDisplayPanel, JFrame frame) {
        try {
            Object[][] data = employee.get_format_stores_data(employee);
            String[] columnNames = {"ID", "Store name"};
            // Display the table in a JScrollPane
            System.out.println("Display_Store_user_temp here");




        } catch (Exception ex) {
            //ex.printStackTrace(); // debug print
            JOptionPane.showMessageDialog(frame,
                    "Erreur lors de l'affichage des utilisateurs : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Display_Inventory_user_store_temp (Employee employee, JPanel EmployeeDisplayPanel, JFrame frame) {
        try {
            Object[][] data = employee.get_format_stores_data(employee);
            String[] columnNames = {"ID", "Name", "Price", "Quantity", "Store_id"};
            // Display the table in a JScrollPane
            System.out.println("Display_inventory_user_store_temp here");




        } catch (Exception ex) {
            //ex.printStackTrace(); // debug print
            JOptionPane.showMessageDialog(frame,
                    "Erreur lors de l'affichage des utilisateurs : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    

    public JScrollPane DisplayJtable(Object[][] data, String[] columnNames, String title) {
        JTable table = new JTable(data, columnNames);

        // Customize table appearance (optional)
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add table to JScrollPane for scrollability
        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }


    public void DisplayInventoryPanelMethod(Admin admin, JPanel inventoryDisplayPanel, JFrame frame){
        try {
            // Retrieve inventory data from the admin class
            Object[][] data = admin.get_format_items_data(admin);
            String[] columnNames = {"ID", "Name", "Price", "Quantity", "Store_id"};

            //DisplayJtable(data, columnNames, "Inventory Items");
            inventoryDisplayPanel.removeAll();
            inventoryDisplayPanel.add(DisplayJtable(data, columnNames, "Inventory Items"));
            inventoryDisplayPanel.revalidate();

        } catch (Exception ex) {
            //ex.printStackTrace(); // debug print
            JOptionPane.showMessageDialog(frame,
                    "Erreur lors de l'affichage des items de l'inventaire : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void DisplayUserPanelMethod(Admin admin, JPanel userDisplayPanel, JFrame frame){
        try {
            // Retrieve user data from the admin class
            Object[][] users_data = admin.get_format_users_data(admin.getRole());
            String[] columnNames_users = {"ID", "Email", "Pseudo", "Role", "Store_id"};

            // Retrieve white list data from the admin class
            Object[][] whitelist_data = admin.get_format_employee_whitelist_data();
            String[] columnNames_whitelist = {"ID", "Email"};

            userDisplayPanel.removeAll();
            userDisplayPanel.add(DisplayJtable(users_data, columnNames_users, "Users"));


            //afficher la table whitelist
            //userDisplayPanel.add(DisplayJtable(whitelist_data, columnNames_whitelist, "Employee WhiteList"));
            

            userDisplayPanel.revalidate();

        } catch (Exception ex) {
            //ex.printStackTrace(); // debug print
            JOptionPane.showMessageDialog(frame,
                    "Erreur lors de l'affichage des utilisateurs : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void DisplayMagasinPanelMethod(Admin admin, JPanel magasinDisplayPanel, JFrame frame){
        try {
            Object[][] data = admin.get_format_stores_data(admin);
            String[] columnNames = {"ID", "Nom du magasin"};

            magasinDisplayPanel.removeAll();
            magasinDisplayPanel.add(DisplayJtable(data, columnNames, "Stores"));
            magasinDisplayPanel.revalidate();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame,
                    "Erreur lors de l'affichage des magasins : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
