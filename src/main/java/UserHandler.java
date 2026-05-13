import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

public class UserHandler {
    private LinkedList<User> userList;
    private FileHandler fh;
    private DateTimeFormatter localDateFormatter;

    //this.userList  = this.fh.getUserList();
    public UserHandler() {
        this.localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.fh = new FileHandler("users.csv");
        this.userList = this.fh.getUserList();
    }

    /*Funzione x registrare l'utente*/
    public void addUser(String nome, String cognome,  String password, String  username, String  data, String residenza, Roles ruolo) throws Exception {
        LocalDate bDate = this.convertBdate(data);
        User newUser = new User(nome, cognome, password, username, bDate, residenza, ruolo);
        this.userList.add(newUser);
        fh.saveUserList(this.userList); // salva modifiche
        this.userList = this.fh.getUserList(); // aggiorna lista corrente di user
    }


    // sotto metodo per convertire la data da stringa a formato LocalDate
    private LocalDate convertBdate(String bdate){
        LocalDate bDate = LocalDate.parse(bdate,localDateFormatter);
        return bDate;
    }



    //sotto metodo che fa il check della stringa
    private String stringCheck() {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        if (str.trim().isEmpty()) {
            System.out.println("Si prega di inserire un input valido \ninput: ");
            return stringCheck();
        }
        return str;
    }


    /* funzione per controllare se l'utente esiste già*/
    private User checkUser(String username) {
        //this.userList  = this.fh.getUserList();
        for (User u : this.userList) {
            if (u.getUsername().equals(username.trim())) {
                return u;
            }
        }
        return null;
    }
//Esiste username e passa al controllo password
    public User loginUser() throws Exception {
        System.out.println("Inserire l'username:");
        String username = this.stringCheck();
        User u = this.checkUser(username);
        if (u!=null) {
            passcheck(u);
            return u;
        } else {
            System.out.println("Username non trovato, riprova");
            return null;
        }
    }
    //Controlla la password in maniera ricorsiva
    private void  passcheck(User u)throws Exception{
     System.out.println("Inserire la password");
    String passcmp = this.stringCheck();
    String tmp = this.passencryption(passcmp);
            if (tmp.equals(u.getPassword())) {
        System.out.println("Login effettuato con successo");
    } else {
        System.out.println("Password errata, riprova");
        passcheck(u);

            }

    }
    private String passencryption(String password) throws Exception {
        System.out.println("inserire nuovamente la password");
        String passcmp = this.stringCheck();
        if (!password.equals(passcmp)) {
            System.out.println("Le password non corrispondono, riprova.");
            return passencryption(password);
        }
        //encrypting della password

        return AESencrypt.encrypt(password);
    }
}