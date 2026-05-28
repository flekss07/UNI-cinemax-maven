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
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 * Classe che si occupa di gestire tutti i dati che verranno inseriti nei file CSV
 *
 * @author Piano Edoardo
 */
public class FileHandler {
    /**
     * <p>formatter che esegue la conversione da LocalDateTime a String</p>
     * <code>formatter</code>
     */
    private final DateTimeFormatter formatter; // formatter per convertire da LocalDateTime a string
    /**
     * <p>formatter che esegue la conversione da LocalDate a String</p>
     * <code>localDateFormatter</code>
     */
    private final DateTimeFormatter localDateFormatter;
    /**
     * <p>linkedList contenente le proiezioni caricate dal file CSV</p>
     * <code>prolist</code>
     */
    private LinkedList<Proiezioni> proList; // linkedlist
    /**
     * <p>Lista contenente gli utenti caricati dal file CSV</p>
     * <code>userList</code>
     */
    private LinkedList<User> userList;
    /**
     * <p>Lista contenente gli oggetti di tipo prenotazione caricati dal file CSV</p>
     * <code>prenList</code>
     */
    private LinkedList<Prenotazione> prenList;
    /**
     * <p>Percorso del file CSV</p>
     * <code>path</code>
     */
    private final Path path;// percorso file csv proiezioni

    private Random random; // da eliminare -----------------------

    /**
     * <p>Costruttore dei contenitori per gli oggetti proiezioni, user e prenotazioni, con relative date e orari nel file CSV corretto</p>
     * @param path percorso del file CSV
     */
    public FileHandler(String path) {
        this.proList = new LinkedList<>(); // inizializza linkedlist proiezioni
        this.userList = new LinkedList<>(); // inizializza linkedlist user
        this.prenList = new LinkedList<>(); // inizializza linkedlist prenotazioni
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.path = Paths.get("data",path); // imposta percorso file corretto


        this.random= new Random(); //random
    }

    // metodo per caricare i dati delle proiezioni da csv
    /**
     * <p>Metodo che si occupa di caricare i dati delle proiezioni sul file CSV</p>
     * @throws IOException errore durante la lettura dei dati dal file CSV
     */
    private void loadProData() throws IOException {
        BufferedReader br = Files.newBufferedReader(this.path); // crea un reader per il file csv che usa inputstream per processare il testo
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim().parse(br); // crea un parser dedicato per il csv che usa gli header come nomi delle colonne
        for (CSVRecord record : parser) // itera ogni elemento letto dal csvparser per estrarne i dati
            this.createProObj(record); //crea oggetto proiezione e lo aggiunge alla linkedlist dedicata
    }

    // sotto metodo che crea oggetti della classe proiezione e gli assegna i dati
    /**
     * <p>Metodo che si occupa di creare oggetti della classe proiezione e assegna i dati relativi</p>
     * @param record record CSV della proiezione
     */
    private void createProObj(CSVRecord record) {
        LocalDateTime date = convertDate(record.get("data_ora_proiezione")); // converte la data in formate LocalDateTime
        String titolo = record.get("titolo_film");
        Genres genere = Genres.valueOf(record.get("genere"));
        String regista = record.get("regista");
        int anno = Integer.parseInt(record.get("anno")); // converte in formato in
        int durata = Integer.parseInt(record.get("durata_minuti"));
        int etaMin = Integer.parseInt(record.get("eta_minima"));
        float prezzo = Float.parseFloat(record.get("prezzo_biglietto"));
        int posti = Integer.parseInt(record.get("posti_occupati"));
        Proiezioni p = new Proiezioni(genere, titolo, regista, date, durata, etaMin, anno, prezzo,posti); // crea oggetto proiezioni
        this.proList.add(p); // aggiunge oggetto proiezioni alla linkedlist delle proiezioni
    }

    // sotto metodo per convertire le stringhe in formato Date
    /**
     * <p>Metodo che si occupa di convertire le stringhe in formato Date</p>
     * @param strDate data in formato stringa
     * @return data convertita
     */
    private LocalDateTime convertDate(String strDate) {
        // fa il parse della data nel formato preimpostato
        return LocalDateTime.parse(strDate, this.formatter);
    }

    //metodo che salva i dati delle proiezioni su file
    /**
     * <p>Metodo per salvare i dati delle proiezioni su file CSV</p>
     * @throws IOException errore durante la scrittura dei dati
     */
    public void writeToProCsv() throws IOException {
        Writer writer = new FileWriter(this.path.toFile()); // crea writer per scrivere su file
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT); // crea csv printer per creare record da scrivere su file
        this.createHeader(printer);
        for (Proiezioni pro : this.proList)
            this.createProRecord(pro,printer);
        printer.flush(); // fa scrivere su file tutti i record stampati dal printer csv
        printer.close(); // chiude printer stream
        writer.close(); // chiude writer
    }

    //sotto metodo che cra un header per riscrivere il csv
    /**
     * <p>Crea gli header per riscrivere il CSV</p>
     * @param printer oggetto CSVPrinter
     * @throws IOException errore durante la scrittura sul file CSV
     */
    private void createHeader(CSVPrinter printer) throws IOException {
        printer.printRecord(
                "data_ora_proiezione",
                "titolo_film",
                "genere",
                "regista",
                "anno",
                "durata_minuti",
                "eta_minima",
                "prezzo_biglietto",
                "posti_occupati"
        );
    }

    // sotto metodo che crea un record della proiezione selezionata per stampare su csv
    /**
     * <p>Crea un record CSV di una proiezioni</p>
     * @param p proiezione da salvare
     * @param printer oggetto CSVPrinter
     * @throws IOException errore in caso di scrittura sul file CSV
     */
    private void createProRecord(Proiezioni p, CSVPrinter printer) throws IOException {
        printer.printRecord(
                p.getData().format(this.formatter),
                p.getTitolo(),
                p.getGeneri(),
                p.getRegista(),
                p.getAnno(),
                p.getDurata(),
                p.getEtaMin(),
                p.getPrezzo(),
                p.getPosti()
        );
    }

    // metodo che prende i dati dal csv degli utenti e li inserisce nella linkedlist dedicata
    /**
     * <p>Metodo che carica i dati degli utenti dal CSV e inserisce nella linkedlist</p>
     * @throws IOException errore durante la lettura dalla lista
     */
    public void loadUserData() throws IOException {
        BufferedReader br = Files.newBufferedReader(this.path); // crea un reader per il file csv che usa inputstream per processare il testo
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim().parse(br); // crea un parser dedicato per il csv che usa gli header come nomi delle colonne
        for (CSVRecord record : parser) // itera ogni elemento letto dal csvparser per estrarne i dati
            this.createUserObj(record); //crea oggetto proiezione e lo aggiunge alla linkedlist dedicata
    }

    // metodo che crea un oggetto della classe user usando i dati passati dal csv
    /**
     * <p>Metodo che crea un oggetto della classe user usando i dati del CSV</p>
     * @param record record CSV utente
     */
    private void createUserObj(CSVRecord record){
        String nome = record.get("nome");
        String cognome = record.get("cognome");
        String password = record.get("password");
        String username = record.get("username");
        LocalDate dataDiNascita =  LocalDate.parse(record.get("data_di_nascita")); // converte la data di nascita in formato date
        String indirizzo = record.get("indirizzo");
        Roles ruolo = Roles.valueOf(record.get("ruolo"));
        User u = new User(nome,cognome,password,username,dataDiNascita,indirizzo,ruolo); // crea nuovo oggetto user con i dati
        this.userList.add(u); // aggiunge user alla linkedlist dedicata
    }

    //metodo per scrivere sul csv degli user
    /**
     * <p>Metodo che scrive gli utenti sul file CSV</p>
     * @throws IOException errore in scrittura sul file CSV
     */
    public void writeToUserCsv()throws IOException {
        Writer writer = new FileWriter(this.path.toFile()); // crea writer per scrivere su file
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT); // crea csv printer per creare record da scrivere su file
        this.createUserHeader(printer);
        for (User u : this.userList)
            this.createUserRecord(u,printer);
        printer.flush(); // fa scrivere su file tutti i record stampati dal printer csv
        printer.close(); // chiude printer stream
        writer.close(); // chiude writer
    }

    //sotto metodo per creare gli header dello user.csv
    /**
     * <p>Metodo per creare gli header degli user del CSV utenti</p>
     * @param printer oggetto CSVPrinter
     * @throws IOException errore di scrittura su file CSV
     */
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
     * <p>Metodo che crea un record CSV dello user</p>
     * @param u oggetto da salvare
     * @param printer oggetto CSVPrinter
     * @throws IOException errore di scrittura su file CSV
     */
    private void createUserRecord(User u, CSVPrinter printer) throws IOException {
        printer.printRecord(
                u.getNome(),
                u.getCognome(),
                u.getPassword(),
                u.getUsername(),
                u.getDataDiNascita().format(this.localDateFormatter),
                u.getIndirizzo(),
                u.getRole()
        );
    }

    //metodo che carica i dati delle prenotazioni

    /**
     * <p>Metodo che si occupa di caricare i dati delle prenotazione sul file CSV dedicato</p>
     * @throws IOException errore lanciata se durante la scrittura qualcosa andasse storto
     */
    private void loadPrenData() throws IOException {
        BufferedReader br = Files.newBufferedReader(this.path); // crea un reader per il file csv che usa inputstream per processare il testo
        CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim().parse(br); // crea un parser dedicato per il csv che usa gli header come nomi delle colonne
        for (CSVRecord record : parser) // itera ogni elemento letto dal csvparser per estrarne i dati
            this.createPrenObj(record); //crea oggetto proiezione e lo aggiunge alla linkedlist dedicata
    }

    /**
     * <p>Metodo che si occupa di creare gli oggetti di tipo prenotazione</p>
     * @param record record preso dal file CSV
     */
    private void createPrenObj(CSVRecord record){
        String username = record.get("username");
        String titolo = record.get("titolo");
        String id = record.get("id");
        LocalDateTime date = this.convertDate(record.get("data"));
        Prenotazione p = new Prenotazione(username,titolo,date,id);
        this.prenList.add(p);
    }

    //metodo che salva le prenotazioni su file
    /**
     * <p>Metodo che si occupa di scrivere su file le prenotazioni effettuate</p>
     * @throws IOException eccezione lanciata nel caso la scrittura su file non riuscisse
     */
    private void writeToPrenCsv() throws IOException {
        Writer writer = new FileWriter(this.path.toFile()); // crea writer per scrivere su file
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT); // crea csv printer per creare record da scrivere su file
        this.newPrenHeader(printer);
        for (Prenotazione p : this.prenList)
            this.newPrenRecord(p,printer);
        printer.flush(); // fa scrivere su file tutti i record stampati dal printer csv
        printer.close(); // chiude printer stream
        writer.close(); // chiude writer
    }

    //sotto metodo che crea gli header per il csv delle prenotazioni

    /**
     * <p>Metodo che crea le intestazioni per il file delle prenotazioni</p>
     * @param printer oggetto che si occupa del stampare i record su file
     * @throws IOException eccezione lanciata nel caso la scrittura su file non andasse a buon fine
     */
    private void newPrenHeader(CSVPrinter printer) throws IOException {
        printer.printRecord(
                "id",
                "username",
                "titolo",
                "data"
        );
    }

    //sotto metodo che crea un record per le prenotazioni

    /**
     * <p>Metodo che si occupa di creare nuovi record per il fil prenotazioni</p>
     * @param pre oggetto di tipo prenotazioni
     * @param printer oggetto che si occupa del stampare i record su file
     * @throws IOException eccezione lanciata nel caso la scrittura su file non andasse a buon fine
     */
    private void newPrenRecord(Prenotazione pre, CSVPrinter printer) throws IOException {
        printer.printRecord(
                pre.getId(),
                pre.getUsername(),
                pre.getTitolo(),
                pre.getDate().format(this.formatter)
        );
    }

    //metodo che fa il get della linkedlist delle proiezioni
    /**
     * <p>Metodo che restituisce la lista delle proiezioni, se lista vuota viene caricata dal CSV</p>
     * @return lista proiezioni
     */
    public LinkedList<Proiezioni> getProList(){
        if(!this.proList.isEmpty()) // se la linkedlist è già caricata la restituisce
            return this.proList;
        else
            try {
                this.loadProData(); // se linkedlist è vuota la carica da csv
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return getProList(); // richiama la funzione per verificare e restituire i dati
    }

    //metodo che fa il salvataggio della linkedlist passata su file csv proiezioni
    /**
     * <p>Metodo che salva la liste delle proiezioni sul CSV</p>
     * @param proList lista delle proiezioni
     */
    public void saveProList(LinkedList<Proiezioni> proList){
        this.proList = proList; // aggiorna lista salvata in cache
        try {
            this.writeToProCsv(); // riscrive file proiezioni csv
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    //metodo getter della linkedlist di user
    /**
     * <p>Metodo che restituisce la lista degli utenti, se vuota carica direttamente dal CSV</p>
     * @return lista utenti
     */
    public LinkedList<User> getUserList(){
        if(!this.userList.isEmpty()) // se la linkedlist è già caricata la restituisce
            return this.userList;
        try {
            this.loadUserData();
            return this.userList;// se linkedlist è vuota la carica da csv
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // metod oper salvare la linkedlist degli user
    /**
     * <p>Metodo per salvare la linkedlist degli user</p>
     * @param userList lista utenti
     */
    public void saveUserList(LinkedList<User> userList){
        this.userList = userList; // aggiorna lista salvata in cache
        try {
            this.writeToUserCsv(); // riscrive file proiezioni csv
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    // metodo che restituisce la lista delle prenotazioni caricate da file

    /**
     * <p>Metodo che restituisce la lista delle prenotazioni caricata sul file CSV</p>
     * @return lista della prenotazioni
     */
    public LinkedList<Prenotazione>getPrenList(){
        if(!this.prenList.isEmpty()) // se la linkedlist è già caricata la restituisce
            return this.prenList;
        try {
            this.loadPrenData();
            return this.prenList;// se linkedlist è vuota la carica da csv
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //metodo che salva la lista di prenotazioni

    /**
     * <p>Metodo che si occupa di salvare le modifiche fatte alla lista delle prenotazioni</p>
     * @param prenList lista delle prenotazioni
     */
    public void savePrenList(LinkedList<Prenotazione> prenList){
        this.prenList = prenList; // aggiorna lista salvata in cache
        try {
            this.writeToPrenCsv(); // riscrive file proiezioni csv
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
