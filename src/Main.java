
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
        */

        DataBaseManager dbManager = new DataBaseManager();
        dbManager.CreateTables();
        dbManager.AddUser();


        //login & register page
        Form frame = new Form();
    }
}