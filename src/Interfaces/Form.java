package Interfaces;

import DatabaseTools.Tools;
import Users.*;
import java.awt.*;
import javax.swing.*;

public class Form extends JFrame {

    //private JLabel errorLabel;
    protected final Tools tools;
    protected final User user_methods;

    {
        tools = new Tools();
        user_methods = new User();
    }

    public Form() {
        super();
    }    
    
    //    //JFrame frame = new JFrame("IStore");
    //    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    //frame.setSize(500, 500);
    //    //frame.setLocationRelativeTo(null);
    //    //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
////
    //    //JPanel panel = new JPanel();
    //    //panel.setLayout(new BorderLayout());
    //    //panel.setBackground(Color.GRAY);
////
    //    //JLabel titleLabel = new JLabel("IStore", JLabel.CENTER);
    //    //titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Style du titre
    //    //panel.add(titleLabel, BorderLayout.NORTH);
////
    //    //ImageIcon imageIcon = new ImageIcon(getClass().getResource("ressources/Skyrim-Logo.png"));
    //    //JLabel imageLabel = new JLabel(imageIcon, JLabel.CENTER);
    //    //panel.add(imageLabel, BorderLayout.CENTER);
////
    //    //frame.add(panel);
    //    //frame.setVisible(true);
//


    public boolean  verify_email(String email) {
        if (!tools.verify_email_format(email)) {
            System.out.println("Email format not valid");
            return false;
        }
        return true;
    }

    public JScrollPane DisplayJtable(Object[][] data, String[] columnNames) {
        JTable table = new JTable(data, columnNames);

        // Customize table appearance (optional)
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add table to JScrollPane for scrollability
        JScrollPane scrollPane = new JScrollPane(table);
        return scrollPane;
    }






    //temporary methods to do
    // Employee methods
    public void Display_Employees_user_temp(Employee employee, JPanel DisplayPanel, JFrame frame) {
        try {
            Object[][] data = employee.get_format_users_data(employee.getRole());
            String[] columnNames = {"ID", "Username", "Email", "Role", "Store_id"};
            // Display the table in a JScrollPane
            //System.out.println("Display_Employees_user_temp here");

            //USER TITLE DISPLAY PANEL
            JLabel UserDisplayTitle = new JLabel("Stores: " + employee.get_store_name(employee), JLabel.CENTER);
            UserDisplayTitle.setFont(new Font("Arial", Font.BOLD, 20));
            UserDisplayTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

            DisplayPanel.removeAll();
            DisplayPanel.add(UserDisplayTitle, BorderLayout.NORTH);
            DisplayPanel.add(DisplayJtable(data, columnNames), BorderLayout.SOUTH);
            DisplayPanel.revalidate();


        } catch (Exception ex) {
            //ex.printStackTrace(); // debug print
            JOptionPane.showMessageDialog(frame,
                    "Erreur lors de l'affichage des utilisateurs : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Display_Inventory_user_store_temp (Employee employee, JPanel DisplayPanel, JFrame frame) {
        try {
            Object[][] data = employee.get_format_items_data(employee);
            String[] columnNames = {"ID", "Name", "Price", "Quantity", "Store_id"};

            Object[][] data2 = employee.get_format_users_data(employee.getRole());
            String[] columnNames2 = {"ID", "Username", "Email", "Role", "Store_id"};
            // Display the table in a JScrollPane
            //System.out.println("Display_inventory_user_store_temp here");

            //USER TITLE DISPLAY PANEL
            JLabel UserDisplayTitle = new JLabel("Stores: " + employee.get_store_name(employee), JLabel.CENTER);
            UserDisplayTitle.setFont(new Font("Arial", Font.BOLD, 20));
            UserDisplayTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

            DisplayPanel.removeAll();
            DisplayPanel.add(UserDisplayTitle, BorderLayout.NORTH);
            DisplayPanel.add(DisplayJtable(data, columnNames), BorderLayout.CENTER);
            DisplayPanel.add(DisplayJtable(data2, columnNames2), BorderLayout.SOUTH);
            DisplayPanel.revalidate();


        } catch (Exception ex) {
            //ex.printStackTrace(); // debug print
            JOptionPane.showMessageDialog(frame,
                    "Erreur lors de l'affichage des utilisateurs : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    


    // Admin methods
    public void DisplayInventoryPanelMethod(Admin admin, JPanel inventoryDisplayPanel, JFrame frame){
        try {
            // Retrieve inventory data from the admin class
            Object[][] data = admin.get_format_items_data(admin);
            String[] columnNames = {"ID", "Name", "Price", "Quantity", "Store_id"};

            //INVENTORY TITLE DISPLAY PANEL
            JLabel InventoryDisplayTitle = new JLabel("Inventory Table", JLabel.CENTER);
            InventoryDisplayTitle.setFont(new Font("Arial", Font.BOLD, 20));
            InventoryDisplayTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

            inventoryDisplayPanel.removeAll();
            inventoryDisplayPanel.add(InventoryDisplayTitle, BorderLayout.NORTH);
            inventoryDisplayPanel.add(DisplayJtable(data, columnNames));
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

            //USER TITLE DISPLAY PANEL
            JLabel UserDisplayTitle = new JLabel("Users & Whitelist Tables", JLabel.CENTER);
            UserDisplayTitle.setFont(new Font("Arial", Font.BOLD, 20));
            UserDisplayTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

            userDisplayPanel.removeAll();
            userDisplayPanel.add(UserDisplayTitle, BorderLayout.NORTH);
            userDisplayPanel.add(DisplayJtable(users_data, columnNames_users), BorderLayout.SOUTH);
            userDisplayPanel.add(DisplayJtable(whitelist_data, columnNames_whitelist), BorderLayout.CENTER);
            userDisplayPanel.revalidate();

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

            //MAGASIN TITLE DISPLAY PANEL
            JLabel MagasinDisplayTitle = new JLabel("Stores Table", JLabel.CENTER);
            MagasinDisplayTitle.setFont(new Font("Arial", Font.BOLD, 20));
            MagasinDisplayTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

            magasinDisplayPanel.removeAll();
            magasinDisplayPanel.add(MagasinDisplayTitle, BorderLayout.NORTH);
            magasinDisplayPanel.add(DisplayJtable(data, columnNames));
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
