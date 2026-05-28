import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * <h1>Classe che gestisce le operazioni relative alle proiezioni</h1>
 *
 * <p>Permette di aggiungere nuove proiezioni</p>
 * @author Piano Edoardo
 */
public class ProiezioniHandler {
    /**
     * <p>Lista contenente tutte le proiezioni registrate</p>
     * <code>proiezioniList</code>
     */
    private LinkedList<Proiezioni> proiezioniList;
    /**
     * <p>Oggetto della classe FileHandler</p>
     * <code>fh</code>
     */
    private FileHandler fh;
    /**
     * <p>Oggetto per formattare le date</p>
     * <code>formatter</code>
     */
    private DateTimeFormatter formatter;
    /**
     * <p>Oggetto per formattare le data</p>
     * <code>localDateFormatter</code>
     */
    private DateTimeFormatter localDateFormatter;

    /**
     * <p>Costruttore della classe, crea gli oggetti di tipo proiezione</p>
     */
    public ProiezioniHandler() {
        this.localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.fh = new FileHandler("proiezioni.csv");
        this.proiezioniList = this.fh.getProList();
    }

    //funzione che carica in lista le proiezioni
    /**
     * <p>Metodo che consente di caricare in lista le proiezioni</p>
     * @param genere genere del film
     * @param titolo titolo del film
     * @param regista regista del film
     * @param data data del film
     * @param durata durata del film
     * @param etaMIn età minima consentita per vedere il film
     * @param anno anno di rilascio del film
     * @param prezzo prezzo del biglietto
     * @param posti numero posti disponibili
     */
    public void proiezionicreator(Genres genere, String titolo, String regista, String data, int durata, int etaMIn, int  anno, float prezzo,int posti){
    LocalDateTime dataProiezione = this.convertDate(data);
    Proiezioni nuovaProiezione = new Proiezioni(genere, titolo, regista, dataProiezione, durata, etaMIn, anno, prezzo, posti);
    this.proiezioniList.add(nuovaProiezione);
    this.fh.saveProList(this.proiezioniList);
    this.proiezioniList= this.fh.getProList();
    }

    /**
     * <p>Metodo restituixce la data del film</p>
     * @param strDate data del film
     * @return restituisce la data della proiezione
     */
    public LocalDateTime convertDate(String strDate) {
        LocalDateTime projectionDate = LocalDateTime.parse(strDate, this.formatter); // fa il parse della data nel formato preimpostato
        return projectionDate;
    }

    //metodo getter della linkedlist
    /**
     * <p>Metodo che restituisce la lista delle proiezioni</p>
     * @return proiezioni lista
     */
    public LinkedList<Proiezioni> getProiezioniList(){return this.proiezioniList;}

    /**
     * <p>Metodo che svolge la funzione di ricerca delle proiezioni</p>
     * @param titolo titolo del film
     * @return restituisce la linkedlist i cui film sono compatibili con quelli cercati
     */
    public LinkedList<Proiezioni> searchProiezione(String titolo){
        LinkedList<Proiezioni> foundProj= new LinkedList<>(); // lista proiezioni trovate che rispettano i criteri di ricerca
        for(Proiezioni p : this.proiezioniList){
            if(p.getTitolo().toLowerCase().trim().contains(titolo.toLowerCase().trim())) // confronto del titolo
                foundProj.add(p); // se la trova la salva
        }
        return foundProj;
    }

    // metodo di filtro per autore del film
    /**
     * <p>Metodo di filtro, durante la ricerca, dell'autore</p>
     * @param pList lista delle proiezioni
     * @param target autore ricercato
     * @return lista con i parametri filtrati
     */
    public LinkedList<Proiezioni> filtroAutore(LinkedList<Proiezioni> pList, String target){
        LinkedList<Proiezioni> filteredList = new LinkedList<>();
        for (Proiezioni p:pList){
            if(p.getRegista().toLowerCase().trim().contains(target.toLowerCase().trim())) // se autore trovato
                filteredList.add(p); // lo aggiunge alla linkedlist di oggetti trovati
        }
        return filteredList; // ritorna la lista o vuota se non trova nulla o con i valori filtrati
    }

    // metodo di filtro per titolo del film
    /**
     * <p>Metodo che si occupa di filtrare tramite il titolo</p>
     * @param pList liste delle proiezioni
     * @param target titolo ricercato
     * @return restituisce la lista contenente i parametri filtrati
     */
    public LinkedList<Proiezioni> filtroTitolo(LinkedList<Proiezioni> pList, String target){
        LinkedList<Proiezioni> filteredList = new LinkedList<>();
        for (Proiezioni p:pList){
            if(p.getTitolo().toLowerCase().trim().contains(target.toLowerCase().trim())) // se titolo trovato
                filteredList.add(p); // lo aggiunge alla linkedlist di oggetti trovati
        }
        return filteredList; // ritorna la lista o vuota se non trova nulla o con i valori filtrati
    }

    // metodo di filtro per genere del film
    /**
     * <p>Metodo che si occupa di ricercare tramite genere del film</p>
     * @param pList lista delle proiezioni
     * @param target genere ricercato
     * @return restituisce la lista filtrata
     */
    public LinkedList<Proiezioni> filtroGenere(LinkedList<Proiezioni> pList, Genres target){
        LinkedList<Proiezioni> filteredList = new LinkedList<>();
        for (Proiezioni p:pList){
            if(p.getGeneri().equals(target)) // se genere trovato
                filteredList.add(p); // lo aggiunge alla linkedlist di oggetti trovati
        }
        return filteredList; // ritorna la lista o vuota se non trova nulla o con i valori filtrati
    }

    // metodo di filtro per intervallo di date del film
    /**
     * <p>Metodo che si occupa di filtrare le proiezioni in base alle date</p>
     * @param pList lista delle proiezioni
     * @param start inizio orario proiezione
     * @param end orario di proiezione finita
     * @return restituisce la lista delle proiezioni filtrate
     */
    public LinkedList<Proiezioni> filtroData(LinkedList<Proiezioni> pList, String start, String end){
        LocalDateTime convStart = this.convertDate(start);
        LocalDateTime convEnd = this.convertDate(end);
        LinkedList<Proiezioni> filteredList = new LinkedList<>();
        for (Proiezioni p:pList){
            if(p.getData().isBefore(convEnd) && p.getData().isAfter(convStart) || p.getData().isEqual(convStart) || p.getData().isEqual(convEnd))  // se interv date trovato
                filteredList.add(p); // lo aggiunge alla linkedlist di oggetti trovati
        }
        return filteredList; // ritorna la lista o vuota se non trova nulla o con i valori filtrati
    }

    // metodo di filtro per modifica prenotazioni
    /**
     * <p>Metodo di filtro per la modifica delle prenotazioni</p>
     * @param pList lista delle prenotazioni
     * @param start data della proiezione
     * @return restituisce la lista filtrata
     */
    public LinkedList<Proiezioni> afterData(LinkedList<Proiezioni> pList, LocalDateTime start){
        LinkedList<Proiezioni> filteredList = new LinkedList<>();
        for (Proiezioni p:pList){
            if(p.getData().isAfter(start))  // se interv date trovato
                filteredList.add(p); // lo aggiunge alla linkedlist di oggetti trovati
        }
        return filteredList; // ritorna la lista o vuota se non trova nulla o con i valori filtrati
    }

    /**
     * <p>Metodo che si occupa della funzione di filtro per prezzo nella ricerca dei film</p>
     * @param pList lista delle proiezioni
     * @param prezzo prezzo del biglietto inserito dall'utente
     * @return restituisce la lista con i film in base al filtro inserito
     */
    public LinkedList<Proiezioni> filtroPrezzo(LinkedList<Proiezioni> pList, float prezzo){
        LinkedList<Proiezioni> filteredList = new LinkedList<>();
        for (Proiezioni p:pList){
            if(p.getPrezzo()==prezzo) // se titolo trovato
                filteredList.add(p); // lo aggiunge alla linkedlist di oggetti trovati
        }
        return filteredList; // ritorna la lista o vuota se non trova nulla o con i valori filtrati
    }

    /**
     * <p>Metodo che permette la modifica delle proiezioni</p>
     * @param titolo titolo del film
     * @param date data del film
     * @param pr proiezione
     * @return restituisce un booleano nel caso in cui la modifica sia riuscita o meno
     */
    public boolean modificaProj(String titolo, LocalDateTime date,Proiezioni pr) {
        Iterator<Proiezioni> proIt = this.proiezioniList.iterator();// crea un iteratore della lista per poterla modificare mentre viene iterata (non si puo fare con foreach)
        while (proIt.hasNext()) {// continua a iterare la lista fino a che non arriva alla fine o non viene trovato un oggetto prenotazione
            Proiezioni p = proIt.next(); // prende oggetto successivo per check
            if (p.getData().equals(date) && p.getTitolo().equals(titolo)) {
                p.setTitolo(pr.getTitolo());
                p.setData(pr.getData());
                p.setPrezzo(pr.getPrezzo());
                p.setAnno(pr.getAnno());
                p.setGeneri(pr.getGeneri());
                p.setDurata(pr.getDurata());
                p.setRegista(pr.getRegista());
                p.setEtaMin(pr.getEtaMin());
                this.fh.saveProList(this.proiezioniList);
                return true; // conferma modifica
            }
        }
        return false; // conferma di non aver trovato la prenotazione da modificare o che la data di quella trovata è precedente a quella attuale
    }
}
