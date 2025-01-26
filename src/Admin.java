import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User {

    public Admin(String email, String username, String password_hash, String role, int user_id, int store_id) {
        super(email, username, password_hash, role, user_id, store_id);
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

    public String Create_item(String name, Float price, int quantity, int store_id) {
        String query = "insert into items (item_name, item_price, item_quantity, store_id) values (?, ?, ?, ?)";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            statement.setFloat(2, price);
            statement.setInt(3, quantity);
            statement.setInt(4, store_id);
            statement.executeUpdate();
            return "Article créé avec succès.";
        } catch (SQLException e) {
            return "Erreur : Problème lors de la création de l'article.";
        }
    }
    
    public String Delete_Store(int id) {
        String deleteFromTableSQL = "delete from Stores where id =?";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(deleteFromTableSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            dbmanager.reset_employee_after_store_delete(id);
            dbmanager.reset_items_after_store_delete(id);
            return "Magasin supprimé avec succès.";
        } catch (SQLException e) {
            return "Erreur : Problème lors de la suppression du magasin.";
        }
    }

    public String Delete_user(int id) {
        String deleteFromTableSQL = "delete from Users where id =?";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(deleteFromTableSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return "Utilisateur supprimé avec succès.";
        } catch (SQLException e) {
            return "Erreur : Problème lors de la suppression de l'utilisateur.";
        }
    }

    public String Delete_item(int id) {
        String deleteFromTableSQL = "delete from Items where id =?";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(deleteFromTableSQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return "Article supprimé avec succès.";
        } catch (SQLException e) {
            return "Erreur : Problème lors de la suppression de l'article.";
        }
    }

    public Object[][] get_format_stores_data() {
        String query = "select * from Stores";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            try (ResultSet result = statement.executeQuery()) {
                // Move to the last row to calculate row count
                result.last();
                int rowCount = result.getRow();
                result.beforeFirst(); // Reset cursor to the beginning
                
                Object[][] data = new Object[rowCount][2];
                
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

    public String Assign_employee_to_store(int user_id, int store_id) {
        String updateTableSQL = "update Users set store_id = (?) where id = (?)";
        System.out.println("user"+user_id);
        System.out.println("store:"+store_id);
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(updateTableSQL)) {
            statement.setInt(1, store_id);
            statement.setInt(2, user_id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                return "Utilisateur ou magasin inexistant.";
            }
            return "Utilisateur "+ user_id +" affecté au magasin "+ store_id +" avec succès.";
        } catch (SQLException ex) {
            System.err.println("Détails de l'erreur : " + ex.getMessage()); // debug print
            return "Erreur : Problème lors de l'attribution du magasin.";
        }
    }

    public String Update_user(int user_id, int field_index, String new_value) {
        String[] fields = {"email", "pseudo", "password"};
        String selected_field = fields[field_index - 1];
        if (selected_field.equals("password")) {
            new_value = tools.hash_password(new_value);
        }
        String updateTableSQL = "update Users set " + selected_field + " = (?) where id = (?)";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(updateTableSQL)) {
            statement.setString(1, new_value);
            statement.setInt(2, user_id);
            int rowsAffected = statement.executeUpdate();
            return "Utilisateur "+ user_id +" modifié avec succès.";
        } catch (SQLException ex) { 
            System.err.println("Détails de l'erreur : " + ex.getMessage()); // debug print
            return "Erreur : Problème lors de la modification de l'utilisateur.";
        }
    }

    public String update_item(int item_id, int field_index, String newvalue) {
        String[] fields = {"item_name", "item_price", "item_quantity", "store_id"};
        String selected_field = fields[field_index - 1];
        String updateTableSQL = "update Items set " + selected_field + " = (?) where id = (?)";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(updateTableSQL)) {
            switch (field_index) {
                case 1 -> 
                    statement.setString(1, newvalue);
                case 2 -> 
                    statement.setFloat(1, Float.parseFloat(newvalue));
                case 3,4 -> 
                    statement.setInt(1, Integer.parseInt(newvalue));
            }
    
            statement.setInt(2, item_id);
            statement.executeUpdate();
            return "L'élément a été mis à jour avec succès.";
        } catch (SQLException e) {
            return "Erreur : Problème lors de la mise à jour de l'élément.";
        }
    }
 

}
