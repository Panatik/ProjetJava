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
                id int auto_increment primary key,
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

        String createItemsTableSQL = """
            create table if not exists items (
                id int auto_increment primary key,
                item_name varchar(100) not null unique,
                item_price float not null check (item_price >= 0), 
                item_quantity int not null check (item_quantity >= 0),
                store_id int not null,
                foreign key (store_id) references stores(id) on delete cascade
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

            statement.executeUpdate(createItemsTableSQL);
            System.out.println("Table Items créée avec succès.");

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

    public void reset_employee_after_store_delete(int id) {
        String updateTableSQL = "UPDATE Users SET store_id = 0 WHERE store_id=?";
        try (PreparedStatement statement = this.connection.prepareStatement(updateTableSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating Users table: " + e.getMessage());
        }
    }

    public void reset_items_after_store_delete(int id) {
        String updateTableSQL = "UPDATE Items set store_id = 0 WHERE store_id=?";
        try (PreparedStatement statement = this.connection.prepareStatement(updateTableSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating Users table: " + e.getMessage());
        }
    }

    public void delete_self_whitelist(String email) {
        String deleteFromTableSQL = "delete from Employee_whitelist where email =?";
        try (PreparedStatement statement = this.getConnection().prepareStatement(deleteFromTableSQL)) {
            statement.setString(1, email);
            statement.executeUpdate();
            System.out.println("Email supprimé de la whitelist avec succès.");
        } catch (SQLException e) {
            System.err.println("Error while deleting email from whitelist: " + e.getMessage());
        }
    }

}
