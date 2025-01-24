import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class DataBaseManager {
    private static final String BDD = "bd_java"; // Nom de la base de données
    private static final String LOGIN = "root"; // Nom d'utilisateur de la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/" + BDD; // URL de connexion

    private Connection connection;

    public DataBaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            this.connection = DriverManager.getConnection(URL, LOGIN, "");
            System.out.println("Connexion établie avec succès.");
        
        } catch (Exception e) {
            System.err.println("Erreur : Problème de connexion à la base de données.");
            e.printStackTrace();
        }
    }

    public void CreateTables() {
        String createTableSQL = """
            create table if not exists Users (
                id int auto_increment primary key,
                email varchar(100) not null unique,
                pseudo varchar(20) not null,
                password varchar(255) not null,
                role enum('admin', 'user', 'employee') not null default 'user'
            );
        """;
        try (Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Table User creer avec succes.");
        } catch (SQLException e) {
            System.err.println("Erreur : Problème lors de la création de la table User.");
            e.printStackTrace();
        }
    }

    public void AddUser(String email, String login, String passwd) {

        // Hacher le mot de passe
        String hashedPassword = BCrypt.hashpw(passwd, BCrypt.gensalt());
        System.out.println("Mot de passe haché : " + hashedPassword);

        //Query SQL avec des ? qui vont servir pour entrer les paramètres
        String addToTableSQL= """
            insert into Users (email, pseudo, password) values (?, ?, ?);
        """;

        try (PreparedStatement statement = this.connection.prepareStatement(addToTableSQL)) {
            //On remplace les ? par les paramètres (1,2,3... sont des index)
            statement.setString(1, email);
            statement.setString(2, login);
            statement.setString(3, hashedPassword);

            statement.executeUpdate();
            System.out.println("Utilisateur creer avec succes.");
        } catch (SQLException e) {
            System.err.println("Erreur : Problème lors de la création de l'utilisateur.");
            e.printStackTrace();
        }

        // Technique pour vérifier le mot de passe
        //boolean isMatch = BCrypt.checkpw(passwd, hashedPassword);
        //System.out.println("Le mot de passe correspond : " + isMatch);
    }

    public boolean VerifyLogin(String email, String passwd) {
        //booleen pour si l'utilisateur a entrer les bons identifiants
        boolean isGood = false;

        //same
        String QuerySQL = "SELECT email, password FROM Users WHERE email = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(QuerySQL)) {
            //same
            statement.setString(1, email);

            try (ResultSet isMatch = statement.executeQuery()){
                //isMatch.next() permet d'aller à la ligne dans les résultats de la query (si la query retourne rien ça renvoie false)
                //BCrypt.machin ça permet de voir si le mdp et sa version haché correspondent ou pas
                if (isMatch.next() && BCrypt.checkpw(passwd, isMatch.getString("password"))){
                    System.out.println("Login réussi");
                    isGood = true;
                } else {
                    System.out.println("Login pas réussi");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur : Problème lors de la vérification du login");
            e.printStackTrace();
        }

        return isGood;
    }
}
