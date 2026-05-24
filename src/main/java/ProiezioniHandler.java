import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
}






