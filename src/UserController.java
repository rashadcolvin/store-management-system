import java.util.Map;
import java.util.Scanner;

public class UserController extends Controller<User> {
    StoreController storeController = new StoreController();
    CategoryController categoryController = new CategoryController();

    PurchaseOrderController orderController = new PurchaseOrderController();

    ItemController itemController = new ItemController();
    public void showMenu(User user) {
        switch (user.getRole()) {
            case "Admin":
                this.showAdminMenu();
                break;
            case "Store Manager":
                this.showManagerMenu();
                break;
            case "Store Staff":
                this.showStaffMenu();
                break;
        }

    }

    private void showAdminMenu() {
        Main.log("===========================================================");
        Main.log("| Menu: (Enter a number)      11. Generate Purchase Order |");
        Main.log("| A. Search Items             12. View All Orders         |");
        Main.log("| 1. Create Store             13. View Order              |");
        Main.log("| 2. Update Store             14. View Users              |");
        Main.log("| 3. Delete Store             15. Change User Role        |");
        Main.log("| 4. View Store               16. Create Item             |");
        Main.log("| 5. View All Stores          17. Update Item             |");
        Main.log("| 6. Create Category          18. Delete Item             |");
        Main.log("| 7. Update Category          19. View Item               |");
        Main.log("| 8. Delete Category          20. View all Items          |");
        Main.log("| 9. View All Categories      21. Logout                  |");
        Main.log("| 10. View Category            B. Search Store            |");
        Main.log("===========================================================");
    }

    private void showManagerMenu() {
        Main.log("|=============================|");
        Main.log("| Menu: (Enter a number)      |");
        Main.log("| A. Search Items             |");
        Main.log("| B. Search Stores            |");
        Main.log("| 1. Update Inventory         |");
        Main.log("| 2. Check Store Inventory    |");
        Main.log("| 3. Generate Purchase Order  |");
        Main.log("| 4. View Purchase Orders     |");
        Main.log("| 5. Logout                   |");
        Main.log("|=============================|");
    }

    private void showStaffMenu() {
        Main.log("|============================|");
        Main.log("| Menu: (Enter a number)     |");
        Main.log("| 1. Check Inventory         |");
        Main.log("| 2. Request Item Addition   |");
        Main.log("| 3. Update Item Stock       |");
        Main.log("| 4. Logout                  |");
        Main.log("|============================|");
    }

    public void processInput(User user, String input) {
        switch (user.getRole()) {
            case "Admin":
                this.processAdminInput(user, input);
                break;
            case "Store Manager":
                this.processManagerInput(user, input);
                break;
            case "Store Staff":
                this.processStaffInput(user, input);
                break;
        }
    }

    private void processAdminInput(User user, String input) {
        Scanner sc = new Scanner(System.in);
        switch (input) {
            case "A":
                Main.log(itemController.search(user).message);
                break;
            case "B":
                Main.log(storeController.search(user).message);
                break;
            case "1": {

                Main.log("Creating a new Store");
                Main.log("=====================");
                Main.log("Enter Store Name: ");
                String name = sc.nextLine();
                Main.log("Enter Store Location: ");
                String location = sc.nextLine();
                Main.log("Enter Store Contact Information: ");
                String contactInfo = sc.nextLine();
                Main.log("Enter Store Type: ");
                String type = sc.nextLine();
                Main.log("Enter Store Opening Date: ");
                String openingDate = sc.nextLine();

                ControllerResponse<Store> response = storeController.create(user, name, location, contactInfo, type, openingDate);

                Main.log(response.message);

                if (response.status) {
                    Main.log("Store Information:");
                    Main.log(Store.DB.get(name) + "");
                }

                break;
            }
            case "2": {

                Main.log("Updating store");
                Main.log("=====================");

                Main.log("Enter Store Name: ");
                String oldName = sc.nextLine();

                ControllerResponse<Store> response = storeController.view(user, oldName);

                if (!response.status) {
                    Main.log(response.message);
                    break;
                }

                Main.log("Enter New Store Name: ");
                String name = sc.nextLine();

                response = storeController.update(user, oldName, name);

                Main.log(response.message);

                if (response.status) {
                    Main.log("New Store Information:");
                    Main.log(Store.DB.get(name) + "");
                }
                break;
            }
            case "3": {

                Main.log("Deleting Store");
                Main.log("=====================");

                Main.log("Enter Store Name: ");
                String name = sc.nextLine();

                ControllerResponse<Store> response = storeController.delete(user, name);
                Main.log(response.message);
                break;
            }
            case "4": {

                Main.log("View Store");
                Main.log("=====================");

                Main.log("Enter Store Name: ");
                String name = sc.nextLine();

                storeController.view(user, name);
                break;
            }
            case "5": {

                Main.log("Viewing All Stores");
                Main.log("=====================");

                storeController.viewAll(user);

                break;
            }
            case "6": {
                Main.log("Creating new Category");
                Main.log("======================");
                Main.log("Enter Category Name: ");
                String name = sc.nextLine();
                ControllerResponse<Category> response = categoryController.create(user, name);
                Main.log(response.message);
                break;
            }

            case "7": {
                // Update Category
                Main.log("Updating Category");
                Main.log("=====================");
                Main.log("Enter Current Category Name: ");
                String oldName = sc.nextLine();
                Main.log("Enter New Category Name: ");
                String newName = sc.nextLine();
                ControllerResponse<Category> response = categoryController.update(user, oldName, newName);
                Main.log(response.message);
                break;
            }

            case "8": {
                // Delete Category
                Main.log("Deleting item category");
                Main.log("=====================");
                Main.log("Enter Category Name: ");
                String name = sc.nextLine();
                Main.log(categoryController.delete(user, name).message);
                break;
            }

            case "9": {
                // View Categories
                Main.log("Viewing all Categories");
                Main.log("======================");
                categoryController.viewAll(user);
                break;
            }

            case "10": {
                Main.log("View Category");
                Main.log("=====================");

                Main.log("Enter Category Name: ");
                String name = sc.nextLine();

                categoryController.view(user, name);

                break;
            }

            case "11": {

                Main.log("Create Purchase Order");
                Main.log("=====================");
                Main.log(orderController.create(user).message);

                break;
            }
            case "12": {

                Main.log("View Purchase Order");
                Main.log("=====================");
                Main.log("Enter order number: ");
                String number = this.input();
                Main.log(orderController.view(user, number).message);

                break;
            }
            case "13": {

                Main.log("View All Purchase orders");
                Main.log("========================");
                orderController.viewAll(user);

                break;
            }

            case "14": {

                Main.log("View All Users");
                Main.log("========================");
                this.viewAll(user);

                break;
            }

            case "15": {

                Main.log("Change User Role");
                Main.log("========================");
                Main.log("Enter User Email: ");
                String email = this.input();
                Main.log("Enter new Role: ");
                String role = this.input();
                Main.log(this.update(user, email, role).message);

                break;
            }
            case "16":
                Main.log(itemController.create(user).message);
                break;
            case "17":
                Main.log(itemController.update(user).message);
                break;
            case "18":
                Main.log(itemController.delete(user).message);
                break;
            case "19":
                Main.log("Enter item name: ");
                String itemName = this.input();
                Main.log(itemController.view(user, itemName).message);
                break;
            case "20":
                itemController.viewAll(user);
                break;
            case "21":
                Auth.logout();
                break;
            default: {
                Main.log("Please select a Invalid option");
            }
        }
    }

    private void processManagerInput(User user, String input) {
        switch (input) {
            case "A":
                Main.log(itemController.search(user).message);
                break;
            case "B":
                Main.log(storeController.search(user).message);
                break;
            case "1": {
                Main.log(storeController.addItemToStore(user).message);
                break;
            }
            case "2": {
                Main.log(orderController.create(user).message);
                break;
            }
            case "4":
                Auth.logout();
                break;
            default:
                Main.log("Please select an Invalid option");
        }
    }

    private void processStaffInput(User user, String input) {
        switch (input) {
            case "1": {

                Scanner sc = new Scanner(System.in);
                Main.log("Check Inventory");
                Main.log("=====================");

                break;
            }
            case "2": {

                Scanner sc = new Scanner(System.in);
                Main.log("Request Item Addition");
                Main.log("=====================");

                break;
            }
            case "3": {

                Scanner sc = new Scanner(System.in);
                Main.log("Update Item Stock");
                Main.log("=====================");

                break;
            }
            case "4":
                Auth.logout(); // Logout option
                break;
            default:
                Main.log("Please select an Invalid option");
        }
    }

    public ControllerResponse<User> viewAll(User user) {
        if(!user.canManageRoles()) {
            return this.respond(false, "403: Unauthorized to manage users", this.data());
        }

        Main.log("Listing Users");
        Main.log("===================");
        for(Map.Entry<String, User> userEntry: User.DB.entrySet()) {
            Main.log(userEntry.getValue() + "");
        }
        Main.log("");

        return  this.respond(true, "Listed users", this.data());
    }

    public ControllerResponse<User> update(User user, String email, String role) {
        if(!user.canManageRoles()) {
            return this.respond(false, "403: Unauthorized to manage users", this.data());
        }

        if(!User.DB.containsKey(email)) {
            return this.respond(false, "404: User not found", this.data());
        }

        Main.log("Updating Roles");
        Main.log("===================");
        User u = User.DB.get(email);
        u.delete();
        u.setRole(role);
        u.save();
        Main.log("");
        return this.respond(true, "Updated user role to: " + role, this.data(u));
    }

}
