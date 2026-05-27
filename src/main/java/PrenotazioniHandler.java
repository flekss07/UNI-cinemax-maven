import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Classe che si occupa di gestire le proiezioni degli utenti
 */
public class PrenotazioniHandler {
    /**
     * LinkedList che contiene le prenotazioni degli utenti
     */
    private LinkedList<Prenotazione> prenList;
    /**
     * oggetto di FileHandler
     */
    private FileHandler fh;

    /**
     * Costruttore della classe
     */
    public PrenotazioniHandler(){
        this.fh = new FileHandler("prenotazioni.csv");
        this.prenList = this.fh.getPrenList();
    }

    //metodo che crea una nuova prenotazione

    /**
     * Metodo che si occupa di creare una prenotazione
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
    }

    //metodo di ricerca delle prenotazioni di uno user che ritorna una linkedlist con le prenotazioni trovate

    /**
     * Metodo di ricerca delle prenotazioni
     *
     * @param username nome utente
     * @return LinkedList contenente le prenotazioni trovate
     */
    public LinkedList<Prenotazione> visualizzaPrenotazioni(String username){
        LinkedList<Prenotazione> foundList = new LinkedList<>(); // lista prenotazioni trovate
        for(Prenotazione p: this.prenList)
            if(p.getUsername().equals(username)) foundList.add(p);
        return foundList; // ritorna una lista vuota se non trova prenotazioni associate
    }

    //metodo di cancellamento di una prenotazione

    /**
     * Metodo che si occupa della cancellazione delle prenotazioni
     *
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

    //metodo che modifica una prenotazione

    /**
     * Metodo che si occupa della modifica della prenotazione
     *
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
}
