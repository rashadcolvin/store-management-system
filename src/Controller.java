import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Controller<T> {
    public ControllerResponse<T> respond(boolean status, String message, List<T> data) {
        return new ControllerResponse<>(status, message, data);
    }

    @SafeVarargs
    public final List<T> data(T... args) {
        return new ArrayList<>(Arrays.asList(args));
    }

    public String input(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
