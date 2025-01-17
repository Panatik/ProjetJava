package boucles;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    public MyFrame(){
        setTitle("Mathias & Clément Project"); // Titre de la fenêtre
        setSize(400, 300);            // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Assure la fermeture de l'application

        JPanel panel = new JPanel();
        panel.add(new JButton("My button"));
        panel.add(new JTextField("Default"));
        panel.add(new JCheckBox("Dragon Lore", true));
        panel.add(new JRadioButton("NIHIL", false));
        panel.add(new JProgressBar(0,100));
        // add more elements if you like
        this.setContentPane(panel);
    }
}
