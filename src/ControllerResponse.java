import java.util.List;

public class ControllerResponse<T> {
    public boolean status = true;
    public String message = "Successfully perform operation";
    public List<T> data;

    public ControllerResponse(boolean status, String message, List<T> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
