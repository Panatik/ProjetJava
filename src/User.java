import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private String email;
    private String username;
    private String password_hash;
    private String role;
    private int user_id;

    protected final  DataBaseManager dbmanager; 
    protected final Tools tools;

    //initialization des class abstraites dans les 2 constructeurs
    {
       dbmanager = new DataBaseManager();
       tools = new Tools();
    }

    public User(String email, String username, String password_hash, String role, int user_id) {
        this.email = email;
        this.username = username;
        this.password_hash = password_hash;
        this.role = role;
        this.user_id = user_id;
    }

    public User() {}

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public String getRole() {
        return role;
    }

    public int getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", username=" + username + ", password_hash=" + password_hash + ", role=" + role
                + ", user_id=" + user_id + "]";
    }

    public void add_admins () {
        AddUser("clement@fadelogidal.fr", "Clement", "test", "admin");
        AddUser("mathias@fadelogidal.fr", "Mathias", "test", "admin");
    }


    public User VerifyLogin(String email, String passwd) {
        String QuerySQL = "SELECT * FROM Users WHERE email = ?";

        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(QuerySQL)) {
            statement.setString(1, email);
            try (ResultSet datasSet = statement.executeQuery()){
                //datasSet.next() permet d'aller à la ligne dans les résultats de la query (si la query retourne rien ça renvoie false)
                //BCrypt.machin ça permet de voir si le mdp et sa version haché correspondent ou pas
                if (datasSet.next() && BCrypt.checkpw(passwd, datasSet.getString("password"))) {
                    String role = datasSet.getString("role");
                    if (role.equals("admin")) {
                        return new Admin(
                            datasSet.getString("email"),
                            datasSet.getString("pseudo"),
                            datasSet.getString("password"),
                            datasSet.getString("role"),
                            datasSet.getInt("id")
                        );
                    } else if (role.equals("user")) {
                        return new Employee(
                            datasSet.getString("email"),
                            datasSet.getString("pseudo"),
                            datasSet.getString("password"),
                            datasSet.getString("role"),
                            datasSet.getInt("id")
                        );
                    }
                }
            } 
        } catch (SQLException e) {
            System.err.println("Erreur : Problème lors de la vérification du login");
            //e.printStackTrace(); debug print
        }
        return null;
    }

    public String AddUser(String email, String login, String passwd, String role) {
        String output;
        String hashedPassword = tools.hash_password(passwd);
        String addToTableSQL= """
            insert into Users (email, pseudo, password, role, store_id) values (?, ?, ?, ?, ?);
        """;

        if (role.isEmpty()) {
            role = "user";
        }
        
        
        if (is_email_whitelisted(email) || role.equals("admin")) {
            try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(addToTableSQL)) {
                statement.setString(1, email);
                statement.setString(2, login);
                statement.setString(3, hashedPassword);
                statement.setString(4, role);
                statement.setInt(5, 0);   // 0 == no store affected to user

                statement.executeUpdate();
                output = "Utilisateur creer avec succes";
            } catch (SQLException e) {
                if (e.getMessage().contains("Duplicata") ||  e.getMessage().contains("users.email")) {
                    output = "Cette adresse email existe dejà";
                } else {
                    output = "Erreur : Problème lors de l'ajout de l'utilisateur.";
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(output);
        } else {
            output = "not whitelisted";
        }
        return output;
    }

    public boolean is_email_whitelisted(String email) {
        String QuerySQL = "SELECT * FROM Employee_whitelist WHERE email = ?";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(QuerySQL)) {
            statement.setString(1, email);
            try (ResultSet datasSet = statement.executeQuery()){
                return datasSet.next();
            } catch (SQLException e) {
                System.err.println("Erreur : Problème lors de la vérification de l'adresse email");
                //e.printStackTrace(); debug print
            }
        } catch (Exception e) {
            System.err.println("Erreur : Problème lors de la connexion à la base de données");
            //e.printStackTrace(); debug print
        }
        return false;
    }


    public Object[][] get_format_users_data() {
        String query = "SELECT id, email, pseudo, role, store_id FROM Users WHERE role = 'user'";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            try (ResultSet result = statement.executeQuery()) {
                // Move to the last row to calculate row count
                result.last();
                int rowCount = result.getRow();
                result.beforeFirst(); // Reset cursor to the beginning
                
                // Create a 2D array to store the data
                Object[][] data = new Object[rowCount][5];
                
                // Populate the array with data from the ResultSet
                int i = 0;
                while (result.next()) {
                    data[i][0] = result.getInt("id");
                    data[i][1] = result.getString("email");
                    data[i][2] = result.getString("pseudo");
                    data[i][3] = result.getString("role");
                    data[i][4] = result.getInt("store_id"); // Assuming the store_id is stored in the 'id' column as an example
                    i++;
                }
                
                return data; // Return the filled 2D array
            } catch (SQLException e) {
                System.err.println("Error while processing ResultSet: " + e.getMessage());
                return new Object[0][0]; // Return an empty array in case of an error
            }
        } catch (SQLException e) {
            System.err.println("Error while preparing statement or connecting to database: " + e.getMessage());
            return new Object[0][0]; // Return an empty array in case of an error
        }
    }



}
