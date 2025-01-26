import org.mindrot.jbcrypt.BCrypt;

public class Tools {

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
    
}
