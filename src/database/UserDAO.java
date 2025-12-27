import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static List<User> mockUsers = new ArrayList<>();

    static {
        // Pre-populating with dummy values
        // Customer
        User customer = new User();
        customer.setName("Alice Tester");
        customer.setRole("customer");
        customer.setEncryptedKey("cust123");
        customer.setEmail("alice@example.com");
        customer.setCnic("12345-0000000-1");
        customer.setContact("+1-555-0123");
        customer.setPassportNumber("P1234567");
        customer.setCitizenship("United States");
        customer.setCountry("United States");
        customer.setCity("New York");
        customer.setPostalCode("10001");
        customer.setProfileImagePath("titleicon.png"); // Mock image

        // Pilot (Assuming Flight class exists)
        User pilot = new User();
        pilot.setName("Captain Skies");
        pilot.setRole("pilot");
        pilot.setEncryptedKey("pilot123");
        pilot.setEmail("captain@example.com");
        pilot.setCnic("12345-0000000-2");
        pilot.setContact("+1-555-0124");
        pilot.setPassportNumber("P1234568");
        pilot.setCitizenship("United States");
        pilot.setCountry("United States");
        pilot.setCity("Los Angeles");
        pilot.setPostalCode("90001");
        pilot.setSalary(150000.0);
        pilot.setProfileImagePath("titleicon.png"); // Mock image
        // The Flight class is assumed but not provided in the prompt.
        // pilot.getAssignedFlights().add(new Flight(101, "LAX", "JFK", "2025-12-20 08:00", "On Time", "Domestic"));

        // Staff (Assuming Duty class exists)
        User staff = new User();
        staff.setName("Staff Member");
        staff.setRole("staff");
        staff.setEncryptedKey("staff123");
        staff.setEmail("staff@example.com");
        staff.setCnic("12345-0000000-3");
        staff.setContact("+1-555-0125");
        staff.setPassportNumber("P1234569");
        staff.setCitizenship("United States");
        staff.setCountry("United States");
        staff.setCity("Chicago");
        staff.setPostalCode("60601");
        staff.setSalary(80000.0);
        staff.setProfileImagePath("titleicon.png"); // Mock image
        // The Duty class is assumed but not provided in the prompt.
        // staff.getAssignedDuties().add(new Duty("08:00-12:00", "LAX Terminal 1", 101));

        // Admin
        User admin = new User();
        admin.setName("Admin User");
        admin.setRole("admin");
        admin.setEncryptedKey("admin123");
        admin.setEmail("admin@example.com");
        admin.setCnic("12345-0000000-4");
        admin.setContact("+1-555-0126");
        admin.setPassportNumber("P1234570");
        admin.setCitizenship("United States");
        admin.setCountry("United States");
        admin.setCity("Washington DC");
        admin.setPostalCode("20001");
        admin.setSalary(120000.0);
        admin.setProfileImagePath("titleicon.png"); // Mock image

        // Air Hostess (Assuming Flight class exists)
        User airHostess = new User();
        airHostess.setName("Hostess Fly");
        airHostess.setRole("air_hostess");
        airHostess.setEncryptedKey("hostess123");
        airHostess.setEmail("hostess@example.com");
        airHostess.setCnic("12345-0000000-5");
        airHostess.setContact("+1-555-0127");
        airHostess.setPassportNumber("P1234571");
        airHostess.setCitizenship("United States");
        airHostess.setCountry("United States");
        airHostess.setCity("Miami");
        airHostess.setPostalCode("33101");
        airHostess.setSalary(90000.0);
        airHostess.setProfileImagePath("titleicon.png"); // Mock image
        // The Flight class is assumed but not provided in the prompt.
        // airHostess.getAssignedFlights().add(new Flight(102, "JFK", "LAX", "2025-12-21 10:00", "Delayed", "Domestic"));

        mockUsers.add(customer);
        mockUsers.add(pilot);
        mockUsers.add(staff);
        mockUsers.add(admin);
        mockUsers.add(airHostess);
        
        // Additional dummy employees for testing
        // More Pilots
        User pilot2 = new User();
        pilot2.setName("John Wright");
        pilot2.setRole("pilot");
        pilot2.setEncryptedKey("pilot456");
        pilot2.setEmail("john.wright@example.com");
        pilot2.setCnic("12345-0000000-6");
        pilot2.setContact("+1-555-0201");
        pilot2.setPassportNumber("P1234572");
        pilot2.setCitizenship("United States");
        pilot2.setCountry("United States");
        pilot2.setCity("Seattle");
        pilot2.setPostalCode("98101");
        pilot2.setSalary(160000.0);
        pilot2.setProfileImagePath("titleicon.png");
        
        User pilot3 = new User();
        pilot3.setName("Sarah Johnson");
        pilot3.setRole("pilot");
        pilot3.setEncryptedKey("pilot789");
        pilot3.setEmail("sarah.j@example.com");
        pilot3.setCnic("12345-0000000-7");
        pilot3.setContact("+1-555-0202");
        pilot3.setPassportNumber("P1234573");
        pilot3.setCitizenship("United States");
        pilot3.setCountry("United States");
        pilot3.setCity("Denver");
        pilot3.setPostalCode("80201");
        pilot3.setSalary(155000.0);
        pilot3.setProfileImagePath("titleicon.png");
        
        // More Staff
        User staff2 = new User();
        staff2.setName("Mike Davis");
        staff2.setRole("staff");
        staff2.setEncryptedKey("staff456");
        staff2.setEmail("mike.davis@example.com");
        staff2.setCnic("12345-0000000-8");
        staff2.setContact("+1-555-0203");
        staff2.setPassportNumber("P1234574");
        staff2.setCitizenship("United States");
        staff2.setCountry("United States");
        staff2.setCity("Boston");
        staff2.setPostalCode("02101");
        staff2.setSalary(85000.0);
        staff2.setProfileImagePath("titleicon.png");
        
        User staff3 = new User();
        staff3.setName("Emily Brown");
        staff3.setRole("staff");
        staff3.setEncryptedKey("staff789");
        staff3.setEmail("emily.brown@example.com");
        staff3.setCnic("12345-0000000-9");
        staff3.setContact("+1-555-0204");
        staff3.setPassportNumber("P1234575");
        staff3.setCitizenship("United States");
        staff3.setCountry("United States");
        staff3.setCity("Atlanta");
        staff3.setPostalCode("30301");
        staff3.setSalary(82000.0);
        staff3.setProfileImagePath("titleicon.png");
        
        // More Air Hostess
        User airHostess2 = new User();
        airHostess2.setName("Lisa Anderson");
        airHostess2.setRole("air_hostess");
        airHostess2.setEncryptedKey("hostess456");
        airHostess2.setEmail("lisa.a@example.com");
        airHostess2.setCnic("12345-0000000-10");
        airHostess2.setContact("+1-555-0205");
        airHostess2.setPassportNumber("P1234576");
        airHostess2.setCitizenship("United States");
        airHostess2.setCountry("United States");
        airHostess2.setCity("Phoenix");
        airHostess2.setPostalCode("85001");
        airHostess2.setSalary(95000.0);
        airHostess2.setProfileImagePath("titleicon.png");
        
        User airHostess3 = new User();
        airHostess3.setName("Jessica Miller");
        airHostess3.setRole("air_hostess");
        airHostess3.setEncryptedKey("hostess789");
        airHostess3.setEmail("jessica.m@example.com");
        airHostess3.setCnic("12345-0000000-11");
        airHostess3.setContact("+1-555-0206");
        airHostess3.setPassportNumber("P1234577");
        airHostess3.setCitizenship("United States");
        airHostess3.setCountry("United States");
        airHostess3.setCity("Las Vegas");
        airHostess3.setPostalCode("89101");
        airHostess3.setSalary(88000.0);
        airHostess3.setProfileImagePath("titleicon.png");
        
        // Add all additional employees
        mockUsers.add(pilot2);
        mockUsers.add(pilot3);
        mockUsers.add(staff2);
        mockUsers.add(staff3);
        mockUsers.add(airHostess2);
        mockUsers.add(airHostess3);
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

    public void updateUser(User user) {
        // Mock update: Remove old and add new
        mockUsers.removeIf(u -> u.getEncryptedKey().equals(user.getEncryptedKey()));
        mockUsers.add(user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(mockUsers);
    }

    public void deleteUser(User user) {
        mockUsers.remove(user);
    }
}