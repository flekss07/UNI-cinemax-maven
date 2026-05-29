import java.time.LocalDateTime;

/**
 * Classe degli oggetti Prenotazione
 *
 * @author Piano Edoardo
 */
public class Prenotazione {
    /**
     * <p>Parametro nome utente</p>
     * <code>username</code>
     */
    private String username;
    /**
     * <p>Parametro titolo</p>
     * <code>titolo</code>
     */
    private String titolo;
    /**
     * <p>Parametro date</p>
     * <code>date</code>
     */
    private LocalDateTime date;
    /**
     * <p>Parametro id</p>
     * <code>id</code>
     */
    private String id;

    /**
     * <p>Costruttore degli oggetti di tipo prenotazione</p>
     * @param username nome utente del cliente
     * @param titolo titolo del film
     * @param date data della prenotazione
     * @param id id della prenotazione
     */
    public Prenotazione(String username, String titolo, LocalDateTime date,String id) {
        this.username = username;
        this.titolo = titolo;
        this.date = date;
        this.id = id;
    }

    /**
     * <p>Metodo che restituisce il nome utente</p>
     * @return restituisce il nome utente
     */
    public String getUsername(){ return this.username; }

    /**
     * <p>Metodo che restituisce il titolo del film</p>
     * @return restituisce il titolo
     */
    public String getTitolo(){ return this.titolo; }

    /**
     * <p>Metodo che restituisce la data della prenotazione</p>
     * @return restituisce la data della prenotazione
     */
    public LocalDateTime getDate(){ return this.date; }

    /**
     * <p>Metodo che restituisce l'id della prenotazione</p>
     * @return restituisce l'id della prenotazione
     */
    public String getId(){ return this.id; }

    //setter

    /**
     * <p>Metodo che permette di settare il titolo dei film</p>
     * @param titolo titolo dle film
     */
    public void setTitolo(String titolo){this.titolo = titolo;}

    /**
     * <p>Metodo che permette di settare la data della prenotazione</p>
     * @param date data della prenotazione
     */
    public void setDate(LocalDateTime date){this.date = date;}

    /**
     * <p>Metodo che permette di settare il nome utente</p>
     * @param username nome utente collegato alla prenotazione
     */
    public void setUsername(String username){this.username = username;}
}
