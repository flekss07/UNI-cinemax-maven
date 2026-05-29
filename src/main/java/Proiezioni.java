import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Classe che gestisce oggetti di tipo proiezione con tutte le informazioni principali dei film e della programmazione
 *
 * @author Merzagora Mattia Renato
 * @author Ognissanti Elia
 * @author Piano Edoardo
 * @author Scalone Lorenzo
 */
public class Proiezioni {

    /**
     * <p>Variabile enum per indicare i generi</p>
     * <code>genere</code>
     */
    private Genres genere;// da modifica in enum
    /**
     * <p>Variabile che indica i titoli dei film</p>
     * <code>titolo</code>
     */
    private String titolo;
    /**
     * <p>Variabile che indica il nome del regista</p>
     * <code>regista</code>
     */
    private String regista;
    /**
     * <p>Variabile che indica data e ora della proiezione</p>
     * <code>data</code>
     */
    private LocalDateTime data;
    /**
     * <p>Variabile che indica la durata del film in minuti</p>
     * <code>durata</code>
     */
    private int durata;
    /**
     * <p>Variabile che indica l'età minima del pubblico</p>
     * <code>etaMin</code>
     */
    private int etaMin;
    /**
     * <p>Variabile che indica l'anno di uscita del film</p>
     * <code>anno</code>
     */
    private int anno;
    /**
     * <p>Variabile che indica il prezzo del biglietto</p>
     * <code>prezzo</code>
     */
    private float prezzo;
    /**
     * <p>Variabile che indica i posti</p>
     * <code>posti</code>
     */
    private int posti;

    /**
     * <p>Costruttore completo della classe Proiezioni</p>
     *
     * @param genere genere del film
     * @param titolo titolo del film
     * @param regista regista del film
     * @param data data del film
     * @param durata durata del film
     * @param etaMin etaMin età minima consigliata/consentita
     * @param anno anno di uscita del film
     * @param prezzo prezzo del niglietto
     */
    public Proiezioni(Genres genere, String titolo, String regista, LocalDateTime data, int durata, int etaMin,int anno,float prezzo,int posti){
        this.genere = genere;
        this.titolo = titolo;
        this.regista = regista;
        this.data = data;
        this.durata = durata;
        this.etaMin = etaMin;
        this.anno = anno;
        this.prezzo = prezzo;
        this.posti = posti;
    }

    /**
     * Costruttore vuoto della classe Proiezioni
     */
    public Proiezioni(){

    }
    // getter

    /**
     * <p>Restituisce il genre del film</p>
     * @return genere del film
     */
    public Genres getGeneri() { return this.genere; }

    /**
     * <p>Restituisce il titolo del film</p>
     * @return titolo del film
     */
    public String getTitolo() { return this.titolo; }

    /**
     * <p>Restituisce il regista del film</p>
     * @return nome del regista
     */
    public String getRegista() { return this.regista; }

    /**
     * <p>Restituisce la data e l'ora della proiezione</p>
     * @return data della proiezione
     */
    public LocalDateTime getData() { return this.data; }

    /**
     * <p>Restituisce la durata del film</p>
     * @return durata in minuti
     */
    public int getDurata() { return this.durata; }

    /**
     * <p>Restituisce l'età minima consentita</p>
     * @return età minima
     */
    public int getEtaMin() { return this.etaMin; }

    /**
     * <p>Restituisce l'anno di uscita del film</p>
     * @return anno di uscita
     */
    public int getAnno(){ return this.anno; }

    /**
     * <p>Restituisce il prezzo del biglietto</p>
     * @return prezzo del biglietto
     */
    public float getPrezzo(){ return this.prezzo;}

    /**
     * <p>Restituisce i posti disponibili</p>
     * @return posti disponibili
     */
    public int getPosti(){return this.posti;}

    // setter

    /**
     * <p>Imposta il genere del film</p>
     * @param genere nuovo genere del film
     */
    public void setGeneri(Genres genere){ this.genere = genere;  }

    /**
     * <p>Imposta il titolo del film</p>
     * @param titolo nuovo titolo del film
     */
    public void setTitolo(String titolo){ this.titolo = titolo; }

    /**
     * <p>Imposta il regista del film</p>
     * @param regista nuovo regista del film
     */
    public void setRegista(String regista){ this.regista = regista; }

    /**
     *<p>Imposta la data della proiezione</p>
     * @param data nuova data e ora della proiezione
     */
    public void setData(LocalDateTime data){ this.data = data; }

    /**
     *<p>Imposta la durata del film</p>
     * @param durata nuova durata in minuti
     */
    public void setDurata(int durata){ this.durata = durata; }

    /**
     *<p>Imposta l'età minima consentita</p>
     * @param etaMin nuova età minima
     */
    public void setEtaMin(int etaMin){ this.etaMin = etaMin; }

    /**
     * <p>Imposta l'anno di uscita del film</p>
     * @param anno nuovo anno di uscita
     */
    public void setAnno(int anno){ this.anno = anno; }

    /**
     * <p>Imposta il prezzo del biglietto</p>
     * @param prezzo nuovo prezzo del biglietto
     */
    public void setPrezzo(float prezzo){ this.prezzo = prezzo; }

    /**
     * <p>Imposta i posti disponibili</p>
     * @param posti posti disponibili
     */
    public void setPosti(int posti){this.posti = posti;}
}