import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 * Classe responsabile della gestione dei file CVS, contenenti i dati sulle proiezioni e sugli utenti
 * <h>
 * Gestore File Utenti e Proiezioni
 * </h>
 * */
public class
FileHandler {
    /**
     * formatter che esegue la conversione da LocalDateTime a String*/
    private DateTimeFormatter formatter; // formatter per convertire da LocalDateTime a string
    /**
     * formatter che esegue la conversione da LocalDate a String*/
    private DateTimeFormatter localDateFormatter;
    /**
     * linkedList contenente le proiezioni caricate dal file CSV*/
    private LinkedList<Proiezioni> proList; // linkedlist
    /**
     * Lista contenente gli utenti caricati dal file CSV*/
    private LinkedList<User> userList;
    /**
     * Percorso del file CSV*/
    private String path;// percorso file csv proiezioni

    /**Costruttore della classe FileHandler
     *
     * @param path percorso del file CSV*/
    public FileHandler(String path) {
        this.proList = new LinkedList<>(); // inizializza linkedlist proiezioni
        this.userList = new LinkedList<>(); // inizializza linkedlist user
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.path = path;
    }

    // metodo per caricare i dati delle proiezioni da csv
    /**
     * Carica i dati delle proiezioni da un file CSV
     *
     * @param filePath nome del file CSV
     * @throws IOException errore durante la lettura del file*/
    private void loadProData(String filePath) throws IOException {
        Path path = Paths.get("data",filePath);
        BufferedReader br = Files.newBufferedReader(path); // crea un reader per il file csv che usa inputstream per processare il testo
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim().parse(br); // crea un parser dedicato per il csv che usa gli header come nomi delle colonne
        for (CSVRecord record : parser) // itera ogni elemento letto dal csvparser per estrarne i dati
            this.createProObj(record); //crea oggetto proiezione e lo aggiunge alla linkedlist dedicata
    }

    // sotto metodo che crea oggetti della classe proiezione e gli assegna i dati
    /**
     * Metodo per creare oggetti della classe proiezione seguendo i dati nel record
     *
     * @param record record CSV della proiezione*/
    private void createProObj(CSVRecord record) {
        LocalDateTime date = convertDate(record.get("data_ora_proiezione")); // converte la data in formate LocalDateTime
        String titolo = record.get("titolo_film");
        String genere = record.get("genere");
        String regista = record.get("regista");
        int anno = Integer.parseInt(record.get("anno")); // converte in formato in
        int durata = Integer.parseInt(record.get("durata_minuti"));
        int etaMin = Integer.parseInt(record.get("eta_minima"));
        float prezzo = Float.parseFloat(record.get("prezzo_biglietto"));
        Proiezioni p = new Proiezioni(genere, titolo, regista, date, durata, etaMin, anno, prezzo); // crea oggetto proiezioni
        this.proList.add(p); // aggiunge oggetto proiezioni alla linkedlist delle proiezioni
    }

    

    // sotto metodo per convertire le stringhe in formato Date
    /**
     * Metodo per convertire le stringhe in formato LocalDateTime
     *
     * @param strDate data in formato stringa
     * @return data convertita
     * */
    private LocalDateTime convertDate(String strDate) {
        LocalDateTime projectionDate = LocalDateTime.parse(strDate, this.formatter); // fa il parse della data nel formato preimpostato
        return projectionDate;
    }

    // sotto metodo per convertire la data da stringa a formato LocalDate
    private LocalDate convertBdate(String bdate){
        LocalDate bDate = LocalDate.parse(bdate,localDateFormatter);
        return bDate;
    }

    //metodo di testing per convertire i dati di un oggetto proiezione in una stringa
    /**Metodo che restituisce una rappresentazione testuale dell'oggetto Proiezioni
     *
     * @param p oggetto proiezione
     * @return stringa descrittiva*/
    private String printProj(Proiezioni p) {
        return "Proiezioni{" +
                "titolo='" + p.getTitolo() + '\'' +
                ", genere='" + p.getGeneri() + '\'' +
                ", regista='" + p.getRegista() + '\'' +
                ", data=" + p.getData() +
                ", anno=" + p.getAnno() +
                ", durata=" + p.getDurata() +
                ", etaMin=" + p.getEtaMin() +
                ", prezzo=" + p.getPrezzo() +
                '}';
    }

    //metodo di testing per stampare i dati delle proiezioni
    /**Stampa tutte le proiezioni presenti nella lista
     * */
    public void printProiezioni() {
        for (Proiezioni p : proList)
            System.out.println(this.printProj(p));
    }

    //metodo che salva i dati delle proiezioni su file
    /**Metodo per salvare i dati delle proiezioni su file CSV
     *
     * @param path percorso del file
     * @throws IOException errore durante la scrittura su file CSV*/
    public void writeToProCsv(String path) throws IOException {
        Writer writer = new FileWriter(path); // crea writer per scrivere su file
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT); // crea csv printer per creare record da scrivere su file
        this.createHeader(printer);
        for (Proiezioni pro : this.proList)
            this.createProRecord(pro,printer);
        printer.flush(); // fa scrivere su file tutti i record stampati dal printer csv
        printer.close(); // chiude printer stream
        writer.close(); // chiude writer
    }

    //sotto metodo che cra un header per riscrivere il csv
    /**Crea gli header per riscrivere il CSV
     *
     * @param printer oggetto CSVprinter
     * @throws IOException*/
    private void createHeader(CSVPrinter printer) throws IOException {
        printer.printRecord(
                "data_ora_proiezione",
                "titolo_film",
                "genere",
                "regista",
                "anno",
                "durata_minuti",
                "eta_minima",
                "prezzo_biglietto"
        );
    }

    // sotto metodo che crea un record della proiezione selezionata per stampare su csv
    /**
     * Crea un record CSV di una proiezioni
     *
     * @param p proiezione da salvare
     * @param printer oggetto CSVPrinter
     * @throws IOException errore di scrittura*/
    private void createProRecord(Proiezioni p, CSVPrinter printer) throws IOException {
        printer.printRecord(
                p.getData().format(this.formatter),
                p.getTitolo(),
                p.getGeneri(),
                p.getRegista(),
                p.getAnno(),
                p.getDurata(),
                p.getEtaMin(),
                p.getPrezzo()
        );
    }

    // metodo che prende i dati dal csv degli utenti e li inserisce nella linkedlist dedicata
    /**
     * Metodo che carica i dati degli utenti dal CSV e inserisce nella linkedlist
     *
     * @param filePath percorso del file
     * @throws IOException errore durante la lettura*/
    public void loadUserData(String filePath) throws IOException {
        Path path = Paths.get("data",filePath);
        BufferedReader br = Files.newBufferedReader(path); // crea un reader per il file csv che usa inputstream per processare il testo
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim().parse(br); // crea un parser dedicato per il csv che usa gli header come nomi delle colonne
        for (CSVRecord record : parser) // itera ogni elemento letto dal csvparser per estrarne i dati
            this.createUserObj(record); //crea oggetto proiezione e lo aggiunge alla linkedlist dedicata
    }

    // metodo che crea un oggetto della classe user usando i dati passati dal csv
    /**
     * Metodo che crea un oggetto della classe user usando i dati del CSV
     *
     * @param record record CSV utente*/
    private void createUserObj(CSVRecord record){
        String nome = record.get("nome");
        String cognome = record.get("cognome");
        String password = record.get("password");
        String username = record.get("username");
        LocalDate dataDiNascita =  LocalDate.parse(record.get("data_di_nascita")); // converte la data di nascita in formato date
        String indirizzo = record.get("indirizzo");
        Roles ruolo = Roles.CLIENTE;
        User u = new User(nome,cognome,password,username,dataDiNascita,indirizzo,ruolo); // crea nuovo oggetto user con i dati
        this.userList.add(u); // aggiunge user alla linkedlist dedicata
    }

    //metodo per scrivere sul csv degli user
    /**
     * Meotodo che scrive gli utenti sul file CSV
     *
     * @param path percorso del file
     * @throws IOException errore in scrittura*/
    public void writeToUserCsv(String path)throws IOException {
        Writer writer = new FileWriter(path); // crea writer per scrivere su file
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT); // crea csv printer per creare record da scrivere su file
        this.createUserHeader(printer);
        for (User u : this.userList)
            this.createUserRecord(u,printer);
        printer.flush(); // fa scrivere su file tutti i record stampati dal printer csv
        printer.close(); // chiude printer stream
        writer.close(); // chiude writer
    }

    //sotto metodo per creare gli header dello user.csv
    /**Metodo per creare gli header degli user del CSV utenti
     *
     * @param printer oggetto CSVPrinter
     * @throws IOException errore di scrittura*/
    private void createUserHeader(CSVPrinter printer) throws IOException {
        printer.printRecord(
                "nome",
                "cognome",
                "password",
                "username",
                "data_di_nascita",
                "indirizzo",
                "ruolo"
        );
    }

    // sotto metodo che crea un record dello user selezionato da stampare su csv
    /**
     * Metodo che crea un record CSV dello user
     *
     * @param u oggetto da salvare
     * @param printer oggetto CSVPrinter
     * @throws IOException errore di scrittura*/
    private void createUserRecord(User u, CSVPrinter printer) throws IOException {
        printer.printRecord(
                u.getNome(),
                u.getCognome(),
                u.getPassword(),
                u.getPassword(),
                u.getDataDiNascita().format(this.localDateFormatter),
                u.getIndirizzo()
        );
    }

    //metodo che fa il get della linkedlist delle proiezioni
    /**
     * Metodo che restituisce la lista delle proiezioni, se lista vuota viene caricata dal CSV
     *
     * @return lista proiezioni*/
    public LinkedList<Proiezioni> getProList(){
        if(!this.proList.isEmpty()) // se la linkedlist è già caricata la restituisce
            return this.proList;
        else
            try {
                this.loadProData(this.path); // se linkedlist è vuota la carica da csv
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return getProList(); // richiama la funzione per verificare e restituire i dati
    }

    //metodo che fa il salvataggio della linkedlist passata su file csv proiezioni
    /**
     * Metodo che salva la liste delle proiezioni sul CSV
     *
     * @param proList lista delle proiezioni*/
    public void saveProList(LinkedList<Proiezioni> proList){
        this.proList = proList; // aggiorna lista salvata in cache
        try {
            this.writeToProCsv(this.path); // riscrive file proiezioni csv
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    //metodo getter della linkedlist di user
    /**
     * Metodo che restituisce la lista degli utenti, se vuota carica direttamente dal CSV
     *
     * @return lista utenti*/
    public LinkedList getUserList(){
        if(!this.userList.isEmpty()) // se la linkedlist è già caricata la restituisce
            return this.userList;
        else
            try {
                this.loadUserData(this.path); // se linkedlist è vuota la carica da csv
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return getUserList(); // richiama la funzione per verificare e restituire i dati
    }

    // metod oper salvare la linkedlist degli user
    /**
     * Metodo per salvare la linkedlist degli user
     *
     * @param userList lista utenti*/
    public void saveUserList(LinkedList<User> userList){
        this.userList = userList; // aggiorna lista salvata in cache
        try {
            this.writeToUserCsv(this.path); // riscrive file proiezioni csv
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
