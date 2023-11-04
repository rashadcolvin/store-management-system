import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void init() {
        User.DB = new HashMap<>();

        User admin = new User("Admin", "James Paul",1, "jp@e.com", "12345678");
        admin.save();

        User manager = new User("Store Manager", "Mary George",2, "mg@e.com", "12345678");
        manager.save();

        User staff = new User("Store Staff", "John Doe",3, "jd@e.com", "12345678");
        staff.save();
    }

    public static void main(String[] args) {
        Main.init();
        Main.log("STORE MANAGEMENT SYSTEM");
        String email = "", password = "";
        boolean isLoggedIn = false;

        do {
            Main.log("Please Log in (Valid email and password required)");

            Scanner scanner = new Scanner(System.in);
            Main.log("Email: ");
            email = scanner.nextLine();
            Main.log("Password: ");
            password = scanner.nextLine();
        }
        while(!Auth.login(email, password));

        User currentUser = User.DB.get(email);

        Main.log("Welcome: " + currentUser.getName());

        boolean shouldContinue = false;

        App app = new App(currentUser);

        do {
            shouldContinue = app.run();
        }
        while(shouldContinue);

        Main.log("You have exited successfully");
    }

    public static void log(String text) {
        System.out.println(text);
    }


}
