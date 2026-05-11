import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class UserHandler {
    private LinkedList<User> userList;
    private FileHandler fh;

    //this.userList  = this.fh.getUserList();
    public UserHandler() {
        this.fh = new FileHandler();
        this.userList = new LinkedList<>();  //= this.fh.getUserList();
    }

    /*Funzione x registrare l'utente*/
    public void addUser() throws Exception {
        Scanner s = new Scanner(System.in);
        //ruolo
        System.out.println("scegliere il ruolo");
        //Inserimento nome
        System.out.println("Inserire Nome");
        String nome = this.stringCheck();
        //inserimento cognome
        System.out.println("Inserire Cognome");
        String cognome = this.stringCheck();
        //inserimento username
        System.out.println("Inserire Username");
        String username = this.stringCheck();
        System.out.println("Inserire indirizzo di residenza");
        String residenza = this.stringCheck();
        //da inserire gestione della data
        System.out.println("inserire la data di nascita con formato gioni/mesi/anni");
        String bDate = this.stringCheck();
        System.out.println("inserire ruolo: ");
        String ruolo = this.stringCheck();
        //inserimento della password
        String password = this.passencryption();
        User newUser = new User(nome, cognome, password, username, bDate, residenza, ruolo);
        this.userList.add(newUser);

    }
    
//funzione di encryption x la password, più check
    private String passencryption() throws Exception {
        System.out.println("inserire una password");
        String password = this.stringCheck();
        System.out.println("inserire nuovamente la password");
        String passcmp = this.stringCheck();
        if (!password.equals(passcmp)) {
            System.out.println("Le password non corrispondono, riprova.");
            return passencryption();
        }
        //encrypting della password
        AESencrypt crypted = new AESencrypt();
        return AESencrypt.encrypt(password);
    }

    //sotto metodo che fa il check della stringa
    private String stringCheck() {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        if (!str.trim().isEmpty()) {
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
    public void usernameChecker() throws Exception {
        System.out.println("Insere l'username:");
        String username = this.stringCheck();
        User u = this.checkUser(username);
        if (u!=null) {
            passcheck(u);
        } else {
            System.out.println("Username non trovato, riprova");
            usernameChecker();
        }
    }
    //Controlla la password in maniera ricorsiva
    private void  passcheck(User u)throws Exception{
     System.out.println("Inserire la password");
    String passcmp = this.stringCheck();
            if (passcmp.equals(u.getPassword())) {
        System.out.println("Login effettuato con successo");
    } else {
        System.out.println("Password errata, riprova");
        passcheck(u);

            }
}
public void logUser() throws Exception {
        System.out.println("Inserire 1 per continuare col login, Inserire 2 per tornare all'inizio");
        int value = numbChecker();
        switch(value){
            case 1:
                this.usernameChecker();
                break ;
            case 2:
                //this.menu();
                //break;

            default:
                System.out.println("Il  numero inserito non è in range, inserire 1 o 2 grazie");
                this.logUser();
        }

    }
private int numbChecker(){
        String s = this.stringCheck(); //controllo stringa
        try {
        int n = Integer.parseInt(s);
        if (n <= 0) {
            System.out.println("Il numero inserito non può essere negativo");
            return this.numbChecker();
        } else {
            return n;
        }
    } catch (NumberFormatException e) {
        System.out.println("Quello che hai inserito non è un numero. Riprova");
        return numbChecker();
    }
}


}