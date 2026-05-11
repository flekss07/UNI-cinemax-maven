
public class Main {
    public static void main(String[] args){
        FileHandler fh = new FileHandler("users.csv");
        UserHandler uh = new UserHandler();
        try {
            uh.addUser();
        }catch(Exception e){
            System.out.println("errore in main adduser");
            throw new RuntimeException(e);
        }

    }
}
