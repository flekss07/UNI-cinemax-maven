import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.UUID;

public class Main {


    static void main(String[] args) throws IOException {
        Menu menu = new Menu();
        try {
            menu.menuSelect();
        }catch(Exception e){
            System.out.println("errore in main adduser");
            throw new RuntimeException(e);
        }

    }
}
