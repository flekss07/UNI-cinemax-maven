import java.time.LocalDate;

/**
 * <h1>Classe che si occupa della creazione degli oggetti utente</h1>
 * @author Piano Edoardo
 */
public class User {

    /**
     * <p>Parametro nome</p>
     * <code>nome</code>
     */
    private String nome;

    /**
     * <p>Parametro cognome</p>
     * <code>cognome</code>
     */
    private String cognome;

    /**
     * <p>Parametro password</p>
     * <code><password/code>
     */
    private String password;

    /**
     * <p>Parametro nome utente</p>
     * <code>username</code>
     */
    private String username;

    /**
     * <p>Parametro data di nascita</p>
     * <code>dataDiNascita</code>
     */
    private LocalDate dataDiNascita;

    /**
     * <p>Parametro indirizzo</p>
     * <code>indirizzo</code>
     */
    private String indirizzo;

    /**
     * <p>Parametro ruolo</p>
     * <code>ruolo</code>
     */
    private Roles ruolo;

    /**
     * <p>Costruttore che si occupa della creazione degli utenti</p>
     *
     * @param nome nome inserito dall'utente
     * @param cognome cognome inserito dall'utente
     * @param password password inserito dall'utente
     * @param username nome utente inserito dall'utente
     * @param dataDiNascita data di nascita dall'utente
     * @param indirizzo indirizzo inserito dall'utente
     * @param ruolo ruolo inserito dall'utente
     */
    public User(String nome, String cognome, String password, String username,LocalDate dataDiNascita,String indirizzo, Roles ruolo){
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.username = username;
        this.dataDiNascita = dataDiNascita;
        this.indirizzo = indirizzo;
        this.ruolo = ruolo;
    }
    
    // getter

    /**
     * <p>Metodo getter per ottenere il nome</p>
     *
     * @return restituisce il nome inserito
     */
    public String getNome(){ return this.nome; }

    /**
     * <p>Metodo getter per ottenere il cognome</p>
     *
     * @return restituisce il cognome inserito
     */
    public String getCognome(){ return this.cognome; }

    /**
     * <p>Metodo getter per ottenere la password</p>
     *
     * @return restituisce la password
     */
    public String getPassword(){ return this.password; }

    /**
     * <p>Metodo getter per ottenere il nome utente</p>
     *
     * @return restituisce il nome utente
     */
    public String getUsername(){ return this.username; }

    /**
     * <p>Metodo getter per ottenere la data di nascita</p>
     *
     * @return restituisce la data di nascita
     */
    public LocalDate getDataDiNascita() { return this.dataDiNascita; }

    /**
     * <p>Metodo getter per ottenere l'indirizzo</p>
     *
     * @return restituisce l'indirizzo
     */
    public String getIndirizzo() { return this.indirizzo; }

    /**
     * <p>Metodo getter per ottenere il ruolo</p>
     *
     * @return restituisce il ruolo
     */
    public Roles getRole(){ return this.ruolo; }

    //setter

    /**
     * <p>Metodo setter per modificare il nome</p>
     *
     * @param nome nome inserito dall'utente
     */
    public void setNome(String nome){ this.nome = nome; }

    /**
     * <p>Metodo setter per modificare il cognome</p>
     *
     * @param cognome cognome inserito dall'utente
     */
    public void setCognome(String cognome){ this.cognome = cognome; }

    /**
     * <p>Metodo setter per modificare la password</p>
     *
     * @param password password inserita dall'utente
     */
    public void setPassword(String password){ this.password = password; }

    /**
     * <p>Metodo setter per modificare il nome utente</p>
     *
     * @param username nome utente inserito dall'utente
     */
    public void setUsername(String username){ this.username = username; }

    /**
     * <p>Metodo setter per modificare la data di nascita</p>
     *
     * @param dataDiNascita data di nascita inserita dall'utente
     */
    public void setDataDiNascita(LocalDate dataDiNascita){ this.dataDiNascita = dataDiNascita; }

    /**
     * <p>Metodo setter per modificare l'indirizzo</p>
     *
     * @param indirizzo indirizzo inserito dall'utente
     */
    public void setIndirizzo(String indirizzo){ this.indirizzo = indirizzo; }
}
