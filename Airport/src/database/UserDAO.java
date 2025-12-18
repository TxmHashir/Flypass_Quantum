
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

        mockUsers.add(customer);
        mockUsers.add(pilot);
    }

    public User getUserByEncryptedKey(String key) {
        // Search through our list instead of the database
        return mockUsers.stream()
                .filter(u -> u.getEncryptedKey().equals(key))
                .findFirst()
                .orElse(null);
    }

    public boolean signUp(User user) {
        // Just add to the list for the duration of the program run
        mockUsers.add(user);
        return true;
    }
}