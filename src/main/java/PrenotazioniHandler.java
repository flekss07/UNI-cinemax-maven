import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Classe che si occupa di gestire le proiezioni degli utenti
 *
 * @author Piano Edoardo
 */
public class PrenotazioniHandler {
    /**
     * <p>LinkedList che contiene le prenotazioni degli utenti</p>
     * <code>prenList</code>
     */
    private LinkedList<Prenotazione> prenList;
    /**
     * <p>Oggetto di FileHandler</p>
     * <code>fh</code>
     */
    private FileHandler fh;
    /**
     * <p>formatter per convertire la data</p>
     * <code>formatter</code>
     */
    private DateTimeFormatter formatter;

    /**
     * <p>Costruttore degli oggetti prenotazione</p>
     */
    public PrenotazioniHandler(){
        this.fh = new FileHandler("prenotazioni.csv");
        this.prenList = this.fh.getPrenList();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    //metodo che crea una nuova prenotazione
    /**
     * <p>Metodo che si occupa di creare una prenotazione</p>
     *
     * @param username username dell'utente
     * @param titolo titolo del film
     * @param date data del film
     * @param id id generato dal sistema
     */
    public void createBooking(String username, String titolo, LocalDateTime date, String id){
        Prenotazione p = new Prenotazione(username,titolo, date,id);
        this.prenList.add(p);
        this.fh.savePrenList(this.prenList);
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    //metodo di ricerca delle prenotazioni di uno user che ritorna una linkedlist con le prenotazioni trovate

    /**
     * <p>Metodo di ricerca delle prenotazioni</p>
     * @param username nome utente
     * @return LinkedList contenente le prenotazioni trovate
     */
    public LinkedList<Prenotazione> visualizzaPrenotazioni(String username){
        LinkedList<Prenotazione> foundList = new LinkedList<>(); // lista prenotazioni trovate
        for(Prenotazione p: this.prenList)
            if(p.getUsername().equals(username)) foundList.add(p);
        return foundList; // ritorna una lista vuota se non trova prenotazioni associate
    }

    //metodo per visualizzare tutte le prenotazioni effettuate, unica per il BIGLIETTAIO
    public LinkedList<Prenotazione> visualizzaTuttePrenotazioni(){
        LinkedList<Prenotazione> foundList = new LinkedList<>(); // lista prenotazioni trovate
        for(Prenotazione p: this.prenList)
            foundList.add(p);
        return foundList; // ritorna una lista vuota se non trova prenotazioni associate
    }

    //metodo di cancellamento di una prenotazione

    /**
     * <p>Metodo che si occupa della cancellazione delle prenotazioni</p>
     * @param id id generato automatico
     * @return conferma la riuscita o meno della rimozione della prenotazione
     */
    public boolean eliminaPrenotazione(String id){
        Iterator<Prenotazione> prenIt = this.prenList.iterator();// crea un iteratore della lista per poterla modificare mentre viene iterata (non si puo fare con foreach)
        while (prenIt.hasNext()){// continua a iterare la lista fino a che non arriva alla fine o non viene trovato un oggetto prenotazione
            Prenotazione p = prenIt.next(); // prende oggetto successivo per check
            if(p.getId().equals(id) && p.getDate().isBefore(LocalDateTime.now())) {
                prenIt.remove();
                this.fh.savePrenList(this.prenList); // salva cambiamento
                return true; // conferma rimozione
            }
        }
        return false; // conferma di non aver trovato la prenotazione da rimuovere
    }


    /**
     * <p>Metodo che si occupa della modifica della prenotazione</p>
     * @param id id generato, al momento della prenotazione
     * @param date data della prenotazione
     * @return conferma la riuscita della modifica o meno della prenotazione
     */
    public boolean modificaPrenotazione(String id, LocalDateTime date) {
        if (!date.isAfter(LocalDateTime.now()))
            return false; // guard colse che fa uscire dal metodo se la data inserita è precedente a quella attuale
        Iterator<Prenotazione> prenIt = this.prenList.iterator();// crea un iteratore della lista per poterla modificare mentre viene iterata (non si puo fare con foreach)
        while (prenIt.hasNext()) {// continua a iterare la lista fino a che non arriva alla fine o non viene trovato un oggetto prenotazione
            Prenotazione p = prenIt.next(); // prende oggetto successivo per check
            if (p.getId().equals(id) && p.getDate().isAfter(LocalDateTime.now())) {
                p.setDate(date); // modifica la data della prenotazione
                this.fh.savePrenList(this.prenList); // salva cambiamento
                return true; // conferma modifica
            }
        }
        return false; // conferma di non aver trovato la prenotazione da modificare o che la data di quella trovata è precedente a quella attuale
    }

    //metodo getter della linkedlist
    /**
     * <p>Metodo che restituisce la lista delle prenotazioni</p>
     * @return lista delle prenotazioni
     */
    public LinkedList<Prenotazione> getPrenList(){return this.prenList;}

    //metodo che restituisce una prenotazione per codice
    public Prenotazione getPrenByid(String id){
        for(Prenotazione p : this.prenList)
            if (p.getId().equals(id.trim())) return p;
        return null;
    }

    //metodo che cerca una prenotazione in base al titolo
    public LinkedList<Prenotazione> searchPrenByTitle(String titolo){
        LinkedList<Prenotazione> foundList = new LinkedList<>(); // lista prenotazioni trovate
        for(Prenotazione p: this.prenList)
            if(p.getTitolo().contains(titolo.trim().toLowerCase())) foundList.add(p);
        return foundList; // ritorna una lista vuota se non trova prenotazioni associate
    }

    // metodo di filtro per intervallo di date del film
    public LinkedList<Prenotazione> filtroData(String start, String end){
        LocalDateTime convStart = LocalDateTime.parse(start + " 00:00:00",this.formatter);
        LocalDateTime convEnd = LocalDateTime.parse(end+ " 00:00:00",this.formatter);
        LinkedList<Prenotazione> filteredList = new LinkedList<>();
        for (Prenotazione p:this.prenList){
            if(p.getDate().isBefore(convEnd) && p.getDate().isAfter(convStart) || p.getDate().isEqual(convStart) || p.getDate().isEqual(convEnd))  // se interv date trovato
                filteredList.add(p); // lo aggiunge alla linkedlist di oggetti trovati
        }
        return filteredList; // ritorna la lista o vuota se non trova nulla o con i valori filtrati
    }
}
