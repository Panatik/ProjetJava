import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Employee extends User {

    public Employee(String email, String username, String password_hash, String role, int user_id, int store_id) {
        super(email, username, password_hash, role, user_id, store_id);
    }

    public void testdefemployee() {
        System.out.println("Hello from employee");
    }

    public String delete_self(int id, String email) {
        String deleteTableSQL = "DELETE FROM Users WHERE id =?";
        String output;
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(deleteTableSQL)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected != 0) {
                dbmanager.delete_self_whitelist(email);
                return "Utilisateur supprimer avec succes\nVous allez etre deconnecte";
            } else {
                return "Utilisateur inexistant.";
            }
        } catch (SQLException e) {
            System.err.println("Error while deleting user: " + e.getMessage());
            return "Erreur lors de la suppression de l'utilisateur.";
        }
    }

    public String update_item_quantity(int item_id, int newvalue) {
        String updateTableSQL = "update Items set item_quantity = (?) where id = (?)";
        try (PreparedStatement statement = dbmanager.getConnection().prepareStatement(updateTableSQL)) {
            statement.setInt(1, newvalue);
            statement.setInt(2, item_id);
            statement.executeUpdate();
            return "L'élément a été mis à jour avec succès.";
        } catch (SQLException e) {
            return "Erreur : Problème lors de la mise à jour de l'élément.";
        }
    }

}
