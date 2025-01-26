import java.sql.PreparedStatement;
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

}
