import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User {

    public Admin(String email, String username, String password_hash, String role, int user_id) {
        super(email, username, password_hash, role, user_id);
    }

    public void testdef() {
        System.out.println("Hello from Admin");
    }

    public String Add_employee_whitelist(String email) {
        String addToTableSQL = "INSERT INTO Employee_whitelist (email) VALUES (?)";
        String output;
    
        if (tools.verify_email_format(email)) {
            try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(addToTableSQL)) {
                statement.setString(1, email);
                statement.executeUpdate();
                output = "Email ajouté à la whitelist avec succès.";
                return output;
            } catch (SQLException e) {
                if (e.getMessage().contains("duplicate") || e.getMessage().contains("email")) {
                    output = "Cette adresse email existe déjà.";
                } else {
                    output = "Erreur : Problème lors de l'ajout de l'utilisateur.";
                    //System.err.println("Détails de l'erreur : " + e.getMessage()); debug print
                }
            }
        } else {
            output = "Format d'email invalide. Veuillez réessayer.";
        }
    
        return output;
    }


    public String Create_store(String storeName) {
        String addToTableSQL = "INSERT INTO Stores (store_name) VALUES (?)";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(addToTableSQL)) {
            statement.setString(1, storeName);
            statement.executeUpdate();
            return "Magasin créé avec succès.";
        } catch (SQLException e) {
            return "Erreur : Problème lors de la création du magasin.";
        }
    }
    
    public String Delete_Store(int id) {
        String deleteFromTableSQL = "DELETE FROM Stores WHERE id =?";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(deleteFromTableSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            dbmanager.Update_employee_after_store_delete(id);
            return "Magasin supprimé avec succès.";
        } catch (SQLException e) {
            return "Erreur : Problème lors de la suppression du magasin.";
        }
    }





    public Object[][] get_format_stores_data() {
        String query = "SELECT * FROM Stores";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            try (ResultSet result = statement.executeQuery()) {
                // Move to the last row to calculate row count
                result.last();
                int rowCount = result.getRow();
                result.beforeFirst(); // Reset cursor to the beginning
                
                // Create a 2D array to store the data
                Object[][] data = new Object[rowCount][2];
                
                // Populate the array with data from the ResultSet
                int i = 0;
                while (result.next()) {
                    data[i][0] = result.getInt("id");
                    data[i][1] = result.getString("store_name");
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
