package Interfaces;

import Users.Employee;
import java.awt.*;
import javax.swing.*;

public class EmployeeDashboard extends Form {

    public EmployeeDashboard(Employee employee) {
        super();

        JFrame frame = new JFrame("Employee Panel - IStore");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Employee Panel - logged in as: " + employee.getUsername(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        //menu bar
        JMenuBar menu = new JMenuBar();
        JMenu FirstButton = new JMenu("Account");
        JMenuItem DisconnectItem = new JMenuItem("Disconnect");
        JMenuItem ExitItem = new JMenuItem("Exit");
        FirstButton.add(DisconnectItem);
        FirstButton.add(ExitItem);
        menu.add(FirstButton);
        frame.setJMenuBar(menu);

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

        DisconnectItem.addActionListener(e -> {
            frame.dispose();
            LoginFrame Loginframe = new LoginFrame();
        });

        ExitItem.addActionListener(e -> {
            frame.dispose();
        });


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
            LoginFrame loginFrame = new LoginFrame();
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
    }
}
