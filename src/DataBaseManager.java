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

    public void add_admins () {
        System.out.println("add admins account");
        AddUser("clement@fadelogidal.fr", "admin1", "test", "admin");
        AddUser("mathias@fadelogidal.fr", "admin2", "test", "admin");
    }

    public String hash_password(String password) {
        // Hache le mot de passe
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("Mot de passe haché : " + hashedPassword);
        return hashedPassword;
    }

    public boolean verify_hash_password(String passwd, String hashedPassword) {
        // Technique pour vérifier le mot de passe
        boolean isMatch = BCrypt.checkpw(passwd, hashedPassword);
        System.out.println("Le mot de passe correspond : " + isMatch);
        return isMatch;
    }

    public boolean verify_email_format(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public String  AddUser(String email, String login, String passwd, String role) {
        String output;
        String hashedPassword = hash_password(passwd);
        String addToTableSQL= """
            insert into Users (email, pseudo, password, role) values (?, ?, ?, ?);
        """;

        if (role.isEmpty()) {
            role = "user";
        }

        try (PreparedStatement statement = this.connection.prepareStatement(addToTableSQL)) {
            //On remplace les ? par les paramètres (1,2,3... sont des index)
            statement.setString(1, email);
            statement.setString(2, login);
            statement.setString(3, hashedPassword);
            statement.setString(4, role);
            

            statement.executeUpdate();
            output = "Utilisateur creer avec succes";

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicata") ||  e.getMessage().contains("users.email")) {
                output = "Cette adresse email est déjà utilisée.";
            } else {
                output = "Erreur : Problème lors de l'ajout de l'utilisateur.";
                System.out.println(e.getMessage());
            }
        }
        System.out.println(output);
        return output;
    }

    public boolean VerifyLogin(String email, String passwd) {
        boolean isGood = false;
        String QuerySQL = "SELECT email, password FROM Users WHERE email = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(QuerySQL)) {
            statement.setString(1, email);
            try (ResultSet isMatch = statement.executeQuery()){
                //isMatch.next() permet d'aller à la ligne dans les résultats de la query (si la query retourne rien ça renvoie false)
                //BCrypt.machin ça permet de voir si le mdp et sa version haché correspondent ou pas
                if (isMatch.next() && BCrypt.checkpw(passwd, isMatch.getString("password"))){
                    isGood = true;
                } 
            }
        } catch (SQLException e) {
            System.err.println("Erreur : Problème lors de la vérification du login");
            e.printStackTrace();
        }

        return isGood;
    }
}
