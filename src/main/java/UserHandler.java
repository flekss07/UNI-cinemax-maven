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
        LocalDate bDate = this.convertBdate(this.stringCheck());
        System.out.println("inserire ruolo: ");
        Roles ruolo = this.chooseRole();
        //inserimento della password
        String password = this.passencryption();
        User newUser = new User(nome, cognome, password, username, bDate, residenza, ruolo);
        this.userList.add(newUser);
    }

    // sotto metodo per convertire la data da stringa a formato LocalDate
    private LocalDate convertBdate(String bdate){
        LocalDate bDate = LocalDate.parse(bdate,localDateFormatter);
        return bDate;
    }

    //sotto metodo che chiede di seleizonare il ruolo
    private Roles chooseRole(){
        System.out.println("selezionare ruolo:\n1)cliente\n2)proiezionista\n3)bibliettaio ");
        int choice = Integer.parseInt(this.stringCheck());
        switch (choice) {
            case 1:
                return Roles.CLIENTE;
            case 2:
                return Roles.PROIEZIONISTA;
            case 3:
                return Roles.BIGLIETTAIO;
            default:
                System.out.println("input non valido, riprovare");
                return null;
        }
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
    public void loginUser() throws Exception {
        System.out.println("Insere l'username:");
        String username = this.stringCheck();
        User u = this.checkUser(username);
        if (u!=null) {
            passcheck(u);
        } else {
            System.out.println("Username non trovato, riprova");
            loginUser();
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
}