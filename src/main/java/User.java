import java.time.LocalDate;

/**
 * <h1>Classe che si occupa della creazione degli oggetti utente</h1>
 */
public class User {

    /**
     * Parametro nome
     */
    private String nome;

    /**
     * Parametro cognome
     */
    private String cognome;

    /**
     * Parametro password
     */
    private String password;

    /**
     * Parametro nome utente
     */
    private String username;

    /**
     * Parametro data di nascita
     */
    private LocalDate dataDiNascita;

    /**
     * Parametro indirizzo
     */
    private String indirizzo;

    /**
     * Parametro ruolo
     */
    private Roles ruolo;

    /**
     * Costruttore che si occupa della creazione degli utenti
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
     * Metodo getter per ottenere il nome
     *
     * @return
     */
    public String getNome(){ return this.nome; }

    /**
     * Metodo getter per ottenere il cognome
     *
     * @return
     */
    public String getCognome(){ return this.cognome; }

    /**
     * Metodo getter per ottenere la password
     *
     * @return
     */
    public String getPassword(){ return this.password; }

    /**
     * Metodo getter per ottenere il nome utente
     *
     * @return
     */
    public String getUsername(){ return this.username; }

    /**
     * Metodo getter per ottenere la data di nascita
     *
     * @return
     */
    public LocalDate getDataDiNascita() { return this.dataDiNascita; }

    /**
     * Metodo getter per ottenere l'indirizzo
     *
     * @return
     */
    public String getIndirizzo() { return this.indirizzo; }

    /**
     * Metodo getter per ottenere il ruolo
     *
     * @return
     */
    public Roles getRole(){ return this.ruolo; }

    //setter

    /**
     * Metodo setter per modificare il nome
     *
     * @param nome
     */
    public void setNome(String nome){ this.nome = nome; }

    /**
     * Metodo setter per modificare il cognome
     *
     * @param cognome
     */
    public void setCognome(String cognome){ this.cognome = cognome; }

    /**
     * Metodo setter per modificare la password
     *
     * @param password
     */
    public void setPassword(String password){ this.password = password; }

    /**
     * Metodo setter per modificare il nome utente
     *
     * @param username
     */
    public void setUsername(String username){ this.username = username; }

    /**
     * Metodo setter per modificare la data di nascita
     *
     * @param dataDiNascita
     */
    public void setDataDiNascita(LocalDate dataDiNascita){ this.dataDiNascita = dataDiNascita; }

    /**
     * Metodo setter per modificare l'indirizzo
     *
     * @param indirizzo
     */
    public void setIndirizzo(String indirizzo){ this.indirizzo = indirizzo; }
}
