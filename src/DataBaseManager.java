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

    public void AddUser() {

        String plainPassword = "monMotDePasse123";

        // Hacher le mot de passe
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        System.out.println("Mot de passe haché : " + hashedPassword);

        // Vérifier le mot de passe
        boolean isMatch = BCrypt.checkpw(plainPassword, hashedPassword);
        System.out.println("Le mot de passe correspond : " + isMatch);
    }
}
