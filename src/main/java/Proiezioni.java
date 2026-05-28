import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Classe che gestisce oggetti di tipo proiezione con tutte le informazioni principali dei film e della programmazione
 */
public class Proiezioni {

    /**
     * variabile enum per indicare i generi
     */
    private Genres genere;// da modifica in enum
    /**
     * variabile che indica i titoli dei film
     */
    private String titolo;
    /**
     * variabile che indica il nome del regista
     */
    private String regista;
    /**
     * variabile che indica data e ora della proiezione
     */
    private LocalDateTime data;
    /**
     * variabile che indica la durata del film in minuti
     */
    private int durata;
    /**
     * variabile che indica l'età minima del pubblico
     */
    private int etaMin;
    /**
     * variabile che indica l'anno di uscita del film
     */
    private int anno;
    /**
     * variabile che indica il prezzo del biglietto
     */
    private float prezzo;

    private int posti;

    /**
     * Costruttore completo della classe Proiezioni
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
     * Costruttore vuoto della classe Proiezioni.
     */
    public Proiezioni(){

    }
    // getter

    /**
     * Restituisce il genre del film
     *
     * @return genere del film
     */
    public Genres getGeneri() { return this.genere; }

    /**
     * Restituisce il titolo del film
     *
     * @return titolo del film
     */
    public String getTitolo() { return this.titolo; }

    /**
     * Resituisce il regista del film
     *
     * @return nome del regista
     */
    public String getRegista() { return this.regista; }

    /**
     * Restituisce la data e l'ora della proiezione
     *
     * @return data della proiezione
     */
    public LocalDateTime getData() { return this.data; }

    /**
     * Restituisce la durata del film
     *
     * @return durata in minuti
     */
    public int getDurata() { return this.durata; }

    /**
     * Restituisce l'età minima consentita
     *
     * @return età minima
     */
    public int getEtaMin() { return this.etaMin; }

    /**
     * Restituisce l'anno di uscita del film
     *
     * @return anno di uscita
     */
    public int getAnno(){ return this.anno; }

    /**
     * Restituisce il prezzo del biglietto
     *
     * @return prezzo del biglietto
     */
    public float getPrezzo(){ return this.prezzo;}

    public int getPosti(){return this.posti;}

    // setter

    /**
     * Imposta il genere del film
     *
     * @param genere nuovo genere del film
     */
    public void setGeneri(Genres genere){ this.genere = genere;  }

    /**
     * Imposta il titolo del film
     *
     * @param titolo nuovo titolo del film
     */
    public void setTitolo(String titolo){ this.titolo = titolo; }

    /**
     * Imposta il regista del film
     *
     * @param regista nuovo regista del film
     */
    public void setRegista(String regista){ this.regista = regista; }

    /**
     *Imposta la data della proiezione
     *
     * @param data nuova data e ora della proiezione
     */
    public void setData(LocalDateTime data){ this.data = data; }

    /**
     *Imposta la durata del film
     *
     * @param durata nuova durata in minuti
     */
    public void setDurata(int durata){ this.durata = durata; }

    /**
     *Imposta l'età minima consentita
     *
     * @param etaMin nuova età minima
     */
    public void setEtaMin(int etaMin){ this.etaMin = etaMin; }

    /**
     *Imposta l'anno di uscita del film
     *
     * @param anno nuovo anno di uscita
     */
    public void setAnno(int anno){ this.anno = anno; }

    /**
     *Imposta il prezzo del biglietto
     *
     * @param prezzo nuovo prezzo del biglietto
     */
    public void setPrezzo(float prezzo){ this.prezzo = prezzo; }


    public void setPosti(int posti){this.posti = posti;}
}