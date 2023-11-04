public class Auth {
    public static boolean login(String email, String password){

        if(!User.DB.containsKey(email)) {
            return false;
        }

        return password.compareTo(User.DB.get(email).getPassword()) == 0;
    }

    public  static void logout(){
        Main.log("Logged Out Successfully");
        App.terminate = true;
    }
}
