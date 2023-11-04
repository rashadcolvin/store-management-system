import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private String role;
    private String name;
    private int id;
    private String email;
    private String password;

    public static HashMap<String, User> DB = new HashMap<>();

    // Constructor
    public User(String role, String name, int id, String email, String password) {
        this.role = role;
        this.name = name;
        this.id = id;
        this.email = email;
        this.password = password;

    }

    public void setRole(String role) {
        this.role = role;
    }

    public void save(){
        User.DB.put(this.email, this);
    }
    public void delete() { User.DB.remove(this.email); }
    // Getter for role
    public String getRole() {
        return role;
    }

    public String getPassword(){
        return this.password;
    }

    // Other getters and setters for name, id, email, password

    // Admin-specific methods
    public boolean canCreateStore() {
        return role.equals("Admin");
    }

    public boolean canUpdateStore() {
        return role.equals("Admin");
    }

    public boolean canDeleteStore() {
        return role.equals("Admin");
    }

    public boolean canManageRoles() {
        return role.equals("Admin");
    }

    public boolean canManageCategories() {
        return role.equals("Admin");
    }

    public boolean canGeneratePurchaseOrders() {
        return role.equals("Admin") || role.equals("Store Manager");
    }

    public boolean canManageItems(){
        return this.role.equals("Admin");
    }

    // Store Manager-specific methods
    public boolean canAddItemsToInventory() {
        return role.equals("Store Manager");
    }

    public boolean canMonitorInventory() {
        return role.equals("Store Manager");
    }

    public boolean canGenerateStorePurchaseOrders() {
        return role.equals("Store Manager");
    }

    // Store Staff-specific methods
    public boolean canViewInventory() {
        return role.equals("Store Staff");
    }

    public boolean canRequestItemAdditions() {
        return role.equals("Store Staff");
    }

    public boolean canUpdateItemQuantities() {
        return role.equals("Store Staff");
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return "{\n" +
                " name: " + this.name + "\n" +
                " email: " + this.email + "\n" +
                " password: " + this.password + "\n" +
                " role: " + this.role + "\n" +
                "}\n";
    }
}
