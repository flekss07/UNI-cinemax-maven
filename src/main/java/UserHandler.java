import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * <h1>Classe che gestisce i dati degli user</h1>
 *
 */
public class UserHandler {
    /**
     * LinkedList che gestisce i dati degli utenti
     */
    private LinkedList<User> userList;
    /**
     * Oggetto di tipo FileHandler
     */
    private FileHandler fh;
    /**
     *
     */
    private DateTimeFormatter localDateFormatter;

    //this.userList  = this.fh.getUserList();
    /**
     *
     */
    public UserHandler() {
        this.localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.fh = new FileHandler("users.csv");
        this.userList = this.fh.getUserList();
    }

    /*Funzione x registrare l'utente*/
    /**
     * Costruttore per la registrazione di nuovi utenti
     *
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param password password non ancora cifrata
     * @param username username inserito dall'utente
     * @param data data di nascita
     * @param residenza residenza dell'utente
     * @param ruolo ruolo dell'utente
     * @throws Exception possibile errore nella creazione dell'utente
     */
    public void addUser(String nome, String cognome,  String password, String  username, String  data, String residenza, Roles ruolo) throws Exception {
        LocalDate bDate = this.convertBdate(data);
        User newUser = new User(nome, cognome, password, username, bDate, residenza, ruolo);
        this.userList.add(newUser);
        fh.saveUserList(this.userList); // salva modifiche
        this.userList = this.fh.getUserList(); // aggiorna lista corrente di user
    }


    // sotto metodo per convertire la data da stringa a formato LocalDate
    /**
     * Metodo che converte la data da stringa a formato LocalDate
     * @param bdate data di nascita dell'utente
     * @return data convertita in LocalData
     */
    private LocalDate convertBdate(String bdate){
        LocalDate bDate = LocalDate.parse(bdate,localDateFormatter);
        return bDate;
    }



    //sotto metodo che fa il check della stringa
    /**
     * Metodo generico per controllare se le stringhe inserite siano valide
     *
     * @return stringa inserita se valida
     */
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
    /**
     * Metodo che controlla se il nome utente inserito è già stato utilizzato
     *
     * @param username nome utente inserito
     * @return u se il nome utente è valido
     * @return null se il nome utente non è valido
     */
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
    /**
     * Metodo che gestisce il login degli utenti
     *
     * @throws Exception errore durante il login
     */
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
    /**
     * Metodo che controlla ricorsivamente la password inserita dall'utente
     *
     * @param u nome utente collegato alla password
     * @throws Exception errore durante il controllo della password
     */
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
    /**
     * Metodo che si occupa della criptazione della password inserite dagli utenti
     *
     * @param password password inserita dall'utente (password in chiaro)
     * @return password criptata
     * @throws Exception errore durante la criptazione della password inserita
     */
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