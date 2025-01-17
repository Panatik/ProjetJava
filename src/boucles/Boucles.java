package boucles;

import java.util.Random;
import java.util.Scanner;

public class Boucles {
    public static void nbrMyst(){
        Random random = new Random();
        int nbrMyst = random.nextInt(0, 101);
        Scanner scanner = new Scanner(System.in);
        int nbrUser = 0;

        while (nbrUser != nbrMyst){
            nbrUser = scanner.nextInt();
            if ( nbrUser < nbrMyst){
                System.out.println("Entrer un nbre plus grand");
            } else if (nbrMyst < nbrUser) {
                System.out.println("Entrer un nbre plus petit");
            }
        }
        System.out.println("GG");
        scanner.close();
    }

    public static void fizzBuzz(){
        for (int i = 1; i <= 100; i++){
            if(i % 10 == 9){
                System.out.println(i);
            } else if (i % 3 == 0 && i % 5 == 0){
                System.out.print("fizzbuzz ");
            } else if (i % 3 == 0){
                System.out.print("fizz ");
            } else if (i % 5 == 0){
                System.out.print("buzz ");
            } else {
                System.out.print(i + " ");
            }
        }
    }
}
