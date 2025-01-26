import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class DataBaseManager {
    private static final String BDD = "bd_java"; // Nom de la base de données
    private static final String LOGIN = "root"; // Nom d'utilisateur de la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/" + BDD; // URL de connexion
    private Tools tools;

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
        tools = new Tools();
    }

    public Connection getConnection() {
        return connection;
    }

    public void CreateTables() {
        String createUsersTableSQL = """
            create table if not exists Users (
                id int auto_increment primary key,
                email varchar(100) not null unique,
                pseudo varchar(20) not null,
                password varchar(255) not null,
                role enum('admin', 'user') not null default 'user',
                store_id int default null,
                foreign key (store_id) references Stores(id)
            );
        """;
    
        // SQL statement for creating the Employee_whitelist table
        String createEmployeeWhitelistTableSQL = """
            create table if not exists Employee_whitelist (
                email varchar(100) not null unique
            );
        """;
    
        // SQL statement for creating the Stores table
        String createStoresTableSQL = """
            create table if not exists Stores (
                id int auto_increment primary key,
                store_name varchar(100) not null unique
            );
        """;

    
        try (Statement statement = this.connection.createStatement()) {
            // Création de la table Users
            statement.executeUpdate(createUsersTableSQL);
            System.out.println("Table Users créée avec succès.");
    
            // Création de la table Employee_whitelist
            statement.executeUpdate(createEmployeeWhitelistTableSQL);
            System.out.println("Table Employee_whitelist créée avec succès.");

            statement.executeUpdate(createStoresTableSQL);
            System.out.println("Table Stores créée avec succès.");


        } catch (SQLException e) {
            System.err.println("Erreur : Problème lors de la création des tables.");
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) {
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace(); //debug print
            return null;
        }
    }

    public void Update_employee_after_store_delete(int id) {
        String updateTableSQL = "UPDATE Users SET store_id = 0 WHERE store_id=?";
        try (PreparedStatement statement = this.connection.prepareStatement(updateTableSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating Users table: " + e.getMessage());
        }
    }

    //public void add_admins () {
    //    System.out.println("add admins account");
    //    AddUser("clement@fadelogidal.fr", "Clement", "test", "admin");
    //    AddUser("mathias@fadelogidal.fr", "Mathias", "test", "admin");
    //}

    //public String hash_password(String password) {
    //    // Hache le mot de passe
    //    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    //    System.out.println("Mot de passe haché : " + hashedPassword);
    //    return hashedPassword;
    //}
//
    //public boolean verify_hash_password(String passwd, String hashedPassword) {
    //    // Technique pour vérifier le mot de passe
    //    boolean isMatch = BCrypt.checkpw(passwd, hashedPassword);
    //    System.out.println("Le mot de passe correspond : " + isMatch);
    //    return isMatch;
    //}
//
    //public boolean verify_email_format(String email) {
    //    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    //    return email.matches(emailRegex);
    //}

    //public String  AddUser(String email, String login, String passwd, String role) {
    //    String output;
    //    String hashedPassword = tools.hash_password(passwd);
    //    String addToTableSQL= """
    //        insert into Users (email, pseudo, password, role) values (?, ?, ?, ?);
    //    """;
//
    //    if (role.isEmpty()) {
    //        role = "user";
    //    }
//
    //    try (PreparedStatement statement = this.connection.prepareStatement(addToTableSQL)) {
    //        //On remplace les ? par les paramètres (1,2,3... sont des index)
    //        statement.setString(1, email);
    //        statement.setString(2, login);
    //        statement.setString(3, hashedPassword);
    //        statement.setString(4, role);
    //        
//
    //        statement.executeUpdate();
    //        output = "Utilisateur creer avec succes";
//
    //    } catch (SQLException e) {
    //        if (e.getMessage().contains("Duplicata") ||  e.getMessage().contains("users.email")) {
    //            output = "Cette adresse email existe dejà";
    //        } else {
    //            output = "Erreur : Problème lors de l'ajout de l'utilisateur.";
    //            System.out.println(e.getMessage());
    //        }
    //    }
    //    System.out.println(output);
    //    return output;
    //}

    //public User VerifyLogin(String email, String passwd) {
    //    String role;
    //    String QuerySQL = "SELECT * FROM Users WHERE email = ?";
//
    //    try (PreparedStatement statement = this.connection.prepareStatement(QuerySQL)) {
    //        statement.setString(1, email);
    //        try (ResultSet datasSet = statement.executeQuery()){
    //            //datasSet.next() permet d'aller à la ligne dans les résultats de la query (si la query retourne rien ça renvoie false)
    //            //BCrypt.machin ça permet de voir si le mdp et sa version haché correspondent ou pas
    //            if (datasSet.next() && BCrypt.checkpw(passwd, datasSet.getString("password"))){
    //                role = datasSet.getString("role");
    //                    return new User(
    //                        datasSet.getString("email"),
    //                        datasSet.getString("pseudo"),
    //                        datasSet.getString("password"),
    //                        datasSet.getString("role"),
    //                        datasSet.getInt("id")
    //                    );
    //                }
    //            } 
    //    } catch (SQLException e) {
    //        System.err.println("Erreur : Problème lors de la vérification du login");
    //        //e.printStackTrace(); debug print
    //    }
    //    return null;
    //}

    //public String Add_employee_whitelist(String email) {
    //    String addToTableSQL = "INSERT INTO Employee_whitelist (email) VALUES (?)";
    //    String output;
    //
    //    if (tools.verify_email_format(email)) {
    //        try (PreparedStatement statement = this.connection.prepareStatement(addToTableSQL)) {
    //            statement.setString(1, email);
    //            statement.executeUpdate();
    //            output = "Email ajouté à la whitelist avec succès.";
    //            return output;
    //        } catch (SQLException e) {
    //            if (e.getMessage().contains("duplicate") || e.getMessage().contains("email")) {
    //                output = "Cette adresse email existe déjà.";
    //            } else {
    //                output = "Erreur : Problème lors de l'ajout de l'utilisateur.";
    //                //System.err.println("Détails de l'erreur : " + e.getMessage()); debug print
    //            }
    //        }
    //    } else {
    //        output = "Format d'email invalide. Veuillez réessayer.";
    //    }
    //
    //    return output;
    //}



}
