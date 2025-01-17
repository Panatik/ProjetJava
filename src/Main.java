import boucles.Boucles;
import boucles.MyFrame;


import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //TKT ON S4EN BALEK DE CA PAREIL POUR LA CLASSE BOUCLES
        /*int[] array1 = {1,2,3,4,5,6,7,8,9,10,11,12};

        Scanner input = new Scanner(System.in);
        System.out.print("Entrer un mois: ");

        int i = input.nextInt();

        if (i <= 12 && i >= 1) {
            System.out.println("mois :" + array1[i-1]);
        } else {
            System.out.println("mois trop grand ou trop petit");
        }



        Boucles.fizzBuzz();

        String BDD = "bd_java";
        String login  = "root";
        String url = "jdbc:mysql://localhost:3306/" + BDD;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, login, "");
            System.out.println("connected");

            // Requête
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM user";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id_user") + ", Nombre1: " + resultSet.getInt("nombre1"));
            }

            //PreparedStatement
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM user " + "WERE id_user = ? AND nombre1 = ?");
            pstmt.setInt(1,2);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Can't load the driver");
            System.exit(0);
        }
        */




        MyFrame frame = new MyFrame();
        frame.setVisible(true);
    }
}