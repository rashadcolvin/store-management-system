import java.util.Scanner;

public class App {
    User currentUser;
    static boolean terminate = false;

    public App(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean run() {
        Main.log("\n");
        UserController uc = new UserController();
        uc.showMenu(this.currentUser);
        String input = this.getInput();
        uc.processInput(this.currentUser, input);
        return !App.terminate;
    }

    public String getInput(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
