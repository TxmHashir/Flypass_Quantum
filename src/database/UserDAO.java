import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static List<User> mockUsers = new ArrayList<>();
    static {
        // Pre-populating with dummy values
        User customer = new User();
        customer.setName("Alice Tester");
        customer.setRole("customer");
        customer.setEncryptedKey("cust123");
        customer.setEmail("alice@example.com");
        customer.setCnic("12345-0000000-1");

        User pilot = new User();
        pilot.setName("Captain Skies");
        pilot.setRole("pilot");
        pilot.setEncryptedKey("pilot123");
        pilot.setEmail("captain@example.com");
        pilot.setCnic("12345-0000000-2");

        User staff = new User();
        staff.setName("Staff Member");
        staff.setRole("staff");
        staff.setEncryptedKey("staff123");
        staff.setEmail("staff@example.com");
        staff.setCnic("12345-0000000-3");

        User admin = new User();
        admin.setName("Admin User");
        admin.setRole("admin");
        admin.setEncryptedKey("admin123");
        admin.setEmail("admin@example.com");
        admin.setCnic("12345-0000000-4");

        mockUsers.add(customer);
        mockUsers.add(pilot);
        mockUsers.add(staff);
        mockUsers.add(admin);
    }

    public User getUserByEncryptedKey(String key) {
        return mockUsers.stream()
                .filter(u -> u.getEncryptedKey().equals(key))
                .findFirst()
                .orElse(null);
    }

    public boolean signUp(User user) {
        mockUsers.add(user);
        return true;
    }

    // Added for persistence in ApplyVisa
    public void updateUser(User user) {
        // Mock update: Remove old and add new
        mockUsers.removeIf(u -> u.getEncryptedKey().equals(user.getEncryptedKey()));
        mockUsers.add(user);
    }

    // New method for admin to get all users
    public List<User> getAllUsers() {
        return new ArrayList<>(mockUsers);
    }

    // New method to delete user
    public void deleteUser(User user) {
        mockUsers.remove(user);
    }
}