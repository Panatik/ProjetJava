public class Employee extends User {

    public Employee(String email, String username, String password_hash, String role, int user_id, int store_id) {
        super(email, username, password_hash, role, user_id, store_id);
    }

    public void testdefemployee() {
        System.out.println("Hello from employee");
    }
}
