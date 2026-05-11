import java.time.LocalDateTime;

public class Proiezioni {
    private String genere; // da modifica in enum
    private String titolo;
    private String regista;
    private LocalDateTime data;
    private int durata;
    private int etaMin;
    private int anno;
    private float prezzo;

    public Proiezioni(String genere, String titolo, String regista, LocalDateTime data, int durata, int etaMin,int anno,float prezzo){
        this.genere = genere;
        this.titolo = titolo;
        this.regista = regista;
        this.data = data;
        this.durata = durata;
        this.etaMin = etaMin;
        this.anno = anno;
        this.prezzo = prezzo;
    }

    public Proiezioni(){

    }
    // getter

    public String getGeneri() { return this.genere; }

    public String getTitolo() { return this.titolo; }

    public String getRegista() { return this.regista; }

    public LocalDateTime getData() { return this.data; }

    public int getDurata() { return this.durata; }

    public int getEtaMin() { return this.etaMin; }

    public int getAnno(){ return this.anno; }

    public float getPrezzo(){ return this.prezzo;}
    // setter

    public void setGeneri(String genere){ this.genere = genere; }

    public void setTitolo(String titolo){ this.titolo = titolo; }

    public void setRegista(String regista){ this.regista = regista; }

    public void setData(LocalDateTime data){ this.data = data; }

    public void setDurata(int durata){ this.durata = durata; }

    public void setEtaMin(int etaMin){ this.etaMin = etaMin; }

    public void setAnno(int anno){ this.anno = anno; }

    public void setPrezzo(float prezzo){ this.prezzo = prezzo; }
}