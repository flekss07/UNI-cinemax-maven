import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

/**
 * <h1>Classe che gestisce i dati degli user</h1>
 * @author Merzagora Mattia Renato
 * @author Ognissanti Elia
 * @author Piano Edoardo
 * @author Scalone Lorenzo
 */
public class UserHandler {
    /**
     * <p>LinkedList che gestisce i dati degli utenti</p>
     * <code>userList</code>
     */
    private LinkedList<User> userList;
    /**
     * <p>Oggetto di tipo FileHandler</p>
     * <code>fh</code>
     */
    private FileHandler fh;
    /**
     * <p>Oggetto della classe localDateFormatter</p>
     * <code>localDateFormatter</code>
     */
    private DateTimeFormatter localDateFormatter;

    //this.userList  = this.fh.getUserList();
    /**
     * <p>Costruttore della classe, genera gli oggetti user</p>
     */
    public UserHandler() {
        this.localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.fh = new FileHandler("users.csv");
        this.userList = this.fh.getUserList();
    }

    /*Funzione x registrare l'utente*/
    /**
     * <p>Costruttore per la registrazione di nuovi utenti</p>
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
     * <p>Metodo che converte la data da stringa a formato LocalDate</p>
     * @param bdate data di nascita dell'utente
     * @return data convertita in LocalData
     */
    private LocalDate convertBdate(String bdate){
        LocalDate bDate = LocalDate.parse(bdate,localDateFormatter);
        return bDate;
    }



    //sotto metodo che fa il check della stringa
    /**
     * <p>Metodo generico per controllare se le stringhe inserite siano valide</p>
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
     * <p>Metodo che controlla se il nome utente inserito è già stato utilizzato</p>
     *
     * @param username nome utente inserito
     * @return u se il nome utente è valido
     * @return null se il nome utente non è valido
     */
    public User checkUser(String username) {
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
     * <p>Metodo che gestisce il login degli utenti</p>
     *
     * @throws Exception errore durante il login
     */
    public User loginUser() throws Exception {
        System.out.println("Insere l'username:");
        String username = this.stringCheck();
        User u = this.checkUser(username);
        if (u!=null)
            if (passcheck(u)) return u;
         else
            System.out.println("Username non trovato, riprova");
        return null;
    }
    //Controlla la password in maniera ricorsiva
    /**
     * <p>Metodo che controlla ricorsivamente la password inserita dall'utente</p>
     *
     * @param u nome utente collegato alla password
     * @throws Exception errore durante il controllo della password
     */
    private Boolean  passcheck(User u)throws Exception{
        System.out.println("Inserire la password");
        String tmp = AESencrypt.encrypt(this.stringCheck());
            if (tmp.equals(u.getPassword())) {// da rimuovere
                return true;
            } else {
                System.out.println("Password errata, riprova");
                return false;
            }
    }
    /**
     * <p>Metodo che si occupa della criptazione della password inserite dagli utenti</p>
     *
     * @param password password inserita dall'utente (password in chiaro)
     * @return password criptata
     * @throws Exception errore durante la criptazione della password inserita
     */
    private String passencryption(String password) throws Exception {

        //encrypting della password
        return AESencrypt.encrypt(password);
    }

    // metodo di filtro per nome e cognome

    /**
     * <p>Metodo filtro tramite nome e cognome per la ricerca di una prenotazione</p>
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @return restituisce il nome utente
     */
    public String filtroNC (String nome, String cognome){
        for (User u:userList){
            if(u.getNome().toLowerCase().trim().equals(nome.toLowerCase().trim()) && u.getCognome().toLowerCase().trim().equals(cognome.toLowerCase().trim())) // se titolo trovato
                return u.getUsername();
        }
        return null;
    }
}

