import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * <h1>Classe che gestisce le operazioni relative alle proiezioni</h1>
 *
 * <p>Permette di aggiungere nuove proiezioni</p>
 *
 */
public class ProiezioniHandler {
    /**
     * Lista contenente tutte le proiezioni registrate
     */
    private LinkedList<Proiezioni> proiezioniList;

    private FileHandler fh;

    private DateTimeFormatter formatter;

    private DateTimeFormatter localDateFormatter;

    public ProiezioniHandler() {
        this.localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.fh = new FileHandler("proiezioni.csv");
        this.proiezioniList = this.fh.getProList();
    }

    //funzione che carica in lista le proiezioni
    public void proiezionicreator(Genres genere, String titolo, String regista, String data, int durata, int etaMIn, int  anno, float prezzo){
    LocalDateTime dataProiezione = this.convertDate(data);
    Proiezioni nuovaProiezione = new Proiezioni(genere, titolo, regista, dataProiezione, durata, etaMIn, anno, prezzo);
    this.proiezioniList.add(nuovaProiezione);
    this.fh.saveProList(this.proiezioniList);
    this.proiezioniList= this.fh.getProList();
    }
    private LocalDateTime convertDate(String strDate) {
        LocalDateTime projectionDate = LocalDateTime.parse(strDate, this.formatter); // fa il parse della data nel formato preimpostato
        return projectionDate;
    }

    //metodo getter della linkedlist
    public LinkedList<Proiezioni> getProiezioniList(){return this.proiezioniList;}

    public LinkedList<Proiezioni> searchProiezione(String titolo){
        LinkedList<Proiezioni> foundProj= new LinkedList<>(); // lista proiezioni trovate che rispettano i criteri di ricerca
        for(Proiezioni p : this.proiezioniList){
            if(p.getTitolo().toLowerCase().trim().contains(titolo.toLowerCase().trim())) // confronto del titolo
                foundProj.add(p); // se la trova la salva
        }
        return foundProj;
    }

    // metodo di filtro per autore del film
    public LinkedList<Proiezioni> filtroAutore(LinkedList<Proiezioni> pList, String target){
        LinkedList<Proiezioni> filteredList = new LinkedList<>();
        for (Proiezioni p:filteredList){
            if(p.getRegista().toLowerCase().trim().contains(target.toLowerCase().trim())) // se autore trovato
                filteredList.add(p); // lo aggiunge alla linkedlist di oggetti trovati
        }
        return filteredList; // ritorna la lista o vuota se non trova nulla o con i valori filtrati
    }

    // metodo di filtro per titolo del film
    public LinkedList<Proiezioni> filtroTitolo(LinkedList<Proiezioni> pList, String target){
        LinkedList<Proiezioni> filteredList = new LinkedList<>();
        for (Proiezioni p:filteredList){
            if(p.getTitolo().toLowerCase().trim().contains(target.toLowerCase().trim())) // se titolo trovato
                filteredList.add(p); // lo aggiunge alla linkedlist di oggetti trovati
        }
        return filteredList; // ritorna la lista o vuota se non trova nulla o con i valori filtrati
    }

    // metodo di filtro per genere del film
    public LinkedList<Proiezioni> filtroGenere(LinkedList<Proiezioni> pList, Genres target){
        LinkedList<Proiezioni> filteredList = new LinkedList<>();
        for (Proiezioni p:filteredList){
            if(p.getGeneri().equals(target)) // se genere trovato
                filteredList.add(p); // lo aggiunge alla linkedlist di oggetti trovati
        }
        return filteredList; // ritorna la lista o vuota se non trova nulla o con i valori filtrati
    }

    // metodo di filtro per intervallo di date del film
    public LinkedList<Proiezioni> filtroGenere(LinkedList<Proiezioni> pList, LocalDateTime start, LocalDateTime end){
        LinkedList<Proiezioni> filteredList = new LinkedList<>();
        for (Proiezioni p:filteredList){
            if(p.getData().isBefore(end) && p.getData().isAfter(start) || p.getData().isEqual(start) || p.getData().isEqual(end))  // se interv date trovato
                filteredList.add(p); // lo aggiunge alla linkedlist di oggetti trovati
        }
        return filteredList; // ritorna la lista o vuota se non trova nulla o con i valori filtrati
    }
}
