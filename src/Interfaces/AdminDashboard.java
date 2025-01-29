package Interfaces;

import Users.Admin;
import java.awt.*;
import javax.swing.*;

public class AdminDashboard extends Form {
    
    public AdminDashboard(Admin admin) {
        super();

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

        JButton AddwhitelistUserButton = new JButton("ajouter employee a la whiteliste");
        JButton createStoreButton = new JButton("Créer un magasin");
        JButton deleteStoreButton = new JButton("Supprimer un magasin");
        JButton assignEmployeeButton = new JButton("Assigner des employés à un magasin");
        JButton deleteEmployeeButton = new JButton("supprimer un employee");
        JButton updateUserButton = new JButton("Mettre à jour un utilisateur");
        JButton updateInventoryItemButton = new JButton("Mettre à jour un item de l'inventaire");
        JButton deleteInventoryItemButton = new JButton("Supprimer un item de l'inventaire");
        JButton createInventoryItemButton = new JButton("Créer un item dans l'inventaire");


        userButtonPanel.add(AddwhitelistUserButton);  
        userButtonPanel.add(assignEmployeeButton);  

        userButtonPanel.add(updateUserButton); 
        inventoryButtonPanel.add(updateInventoryItemButton); 

        magasinButtonPanel.add(createStoreButton);  
        inventoryButtonPanel.add(createInventoryItemButton);

        userButtonPanel.add(deleteEmployeeButton);  
        magasinButtonPanel.add(deleteStoreButton);  
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
            LoginFrame Loginframe = new LoginFrame();
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
}

