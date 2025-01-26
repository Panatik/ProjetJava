public class User {
    private String email;
    private String username;
    private String password_hash;
    private String role;
    private int user_id;
    private DataBaseManager dbmanager; 

    public User(String email, String username, String password_hash, String role, int user_id) {
        this.email = email;
        this.username = username;
        this.password_hash = password_hash;
        this.role = role;
        this.user_id = user_id;
    }

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

    





}
