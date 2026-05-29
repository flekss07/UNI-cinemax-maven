import javax.management.relation.Role;
import java.awt.desktop.AboutEvent;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.UUID;

/**
 * Classe Menù che fa da "interfaccia" per l'intera applicazione
 * @author Merzagora Mattia Renato
 * @author Ognissanti Elia
 * @author Piano Edoardo
 * @author Scalone Lorenzo
 */
public class Menu {
    /**
     * <p>Oggetto della classe UserHandler</p>
     * <code>uh</code>
     */
    private final UserHandler uh;
    /**
     * <p>Oggetto della classe ProiezioniHandler</p>
     * <code>ph</code>
     */
    private final ProiezioniHandler ph ;
    /**
     * <p>Oggetto della classe PrenotazioneHandler</p>
     * <code>prenh</code>
     */
    private final PrenotazioniHandler prenh;
    /**
     * <p>Oggetto della classe user che salva le informazioni dell'utente quando accede con le sue credenziali</p>
     * <code>loggedUser</code>
     */
    private User loggedUser;

    /**
     * <p>Costruttore oggetti della classe userHandler</p>
     */
    public Menu() { //costruzione oggetto classe userhandler
        this.uh = new UserHandler();
        this.ph= new ProiezioniHandler();
        this.prenh= new PrenotazioniHandler();
    }

    /**
     * <p>Metodo che si occupa chiedere all'utente se si vuole registrare, accedere oppure continuare come guest</p>
     */
    public void menuSelect() { //metodo che crea il menu
        boolean repeat=true;
        while (repeat) {
            System.out.println("Inserire il numero corrispondente alla funzione per attivarla\n1)registrarsi\n2)effettuare il login\n3)Continuare come quest \n4)Uscire dal programma");
            int selector = this.numbCheck();
            switch (selector) {
                case 1-> { //registrarsi
                    try {
                        this.userRegister();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> { //login
                    System.out.println("Inizio procedura di login");
                    this.userLogin();
                }
                case 3-> this.guest();
                case 4-> repeat=false; //close menu
                default-> System.out.println("Qualcosa è andato storto...");
            }
        }


    }

    /**
     * <p>Metodo che si occupa di aggiungere le proiezioni a proiezioniHandler</p>
     */
    public void addProiezioni() {
        //inserire il genere del film
        Genres genere = this.selezioneGenere();
        //inserire il titolo
        System.out.println("Inserire il Titolo");
        String titolo = this.stringCheck();
        //inserire il regista
        System.out.println("Inserire il  regista");
        String regista = this.stringCheck();
        //giorno e data della proiezione
        String dataProiezioni = this.dataproiezioni();
        //durata  del filmadd
        System.out.println("Inserire la durata della proiezione (in minuti)");
        int durata = this.duratacheck();
        //età minima x la visione del film
        System.out.println("Inserire l'età minima per la visione del film");
        int etaMin = this.etaCheck();
        //anno di pubblicazione del film
        System.out.println("Inserire l'anno di pubblicazione del film");
        int uscita = this.releaseCheck();
        //release del biglietto
        System.out.println("Inserire il prezzo del film");
        float prezzo = this.priceCheck();
        this.ph.proiezionicreator(genere, titolo, regista, dataProiezioni,durata, etaMin, uscita, prezzo,0);
    }
//funzione x selezione genere

    /**
     * <p>Metodo che fa scegliere che genere inserire per una proiezione</p>
     * @return in base alla scelta verrà inserito il genere selezionato
     */
    private Genres selezioneGenere() {
        System.out.println("Inserire il genere inserendo il numerino assegnato");
        System.out.println("ID\tGENERE");
        System.out.println("─────────────────");
        Genres[] genres = Genres.values();
        for (int i = 0; i < genres.length; i++) {
            System.out.println((i + 1) + "\t" + genres[i].toString());
        }
        String values = this.stringCheck();
        int caso = this.numbchecker(values);
        Genres genere;
        return switch (caso) {
            case 1 -> genere = Genres.Animazione;
            case 2 -> genere = Genres.Avventura;
            case 3 -> genere = Genres.Comico;
            case 4 -> genere = Genres.Commedia;
            case 5 -> genere = Genres.Documentario;
            case 6 -> genere = Genres.Drammatico;
            case 7 -> genere = Genres.Fantascienza;
            case 8 -> genere = Genres.Western;
            case 9 -> genere = Genres.Horror;
            case 10 -> genere = Genres.Thriller;
            default -> {
                System.out.println("Qualcosa è andato storto, riprova");
                yield this.selezioneGenere();
            }
        };
    }

    /**
     * <p>Metodo che si occupa di controllare se l'input numerico inserito dall'utente sia corretto</p>
     * @param s input inserito dall'utente
     * @return esegue chiamate ricorsive nel caso l'input inserito risulti essere un numero negativo, non essere all'interno del range indicato o non essere affatto un numero
     */
    private int numbchecker(String s) {
        try {
            int value = Integer.parseInt(s);
            if (value < 0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbchecker(this.stringCheck());
            } else if (value > 10) {
                System.out.print("Il numero non è nel range, inserirne un'altro");
                return numbchecker(this.stringCheck());
            }
            return value;
        } catch (RuntimeException e) {
            System.out.println("Qualcosa è andato storto riprova");

            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Metodo che si occupa della funzione di registrarsi al sito</p>
     * @throws Exception eccezione lanciata nel caso qualcosa durante la registrazione andasse storto
     */
    public void userRegister() throws Exception {
        //Inserimento nome
        System.out.println("Inserire Nome");
        String nome = this.stringCheck();
        //inserimento cognome
        System.out.println("Inserire Cognome");
        String cognome = this.stringCheck();
        //inserimento username
        String username = this.checkUsernameDupe();
        System.out.println("Inserire indirizzo di residenza");
        String residenza = this.stringCheck();
        String annoDiNascita = this.inseriredata();
        //inserimento ruolo
        Roles ruolo = this.chooseRole();
        //inserimento della password
        String password = this.passencryption();
        this.uh.addUser(nome, cognome, password, username, annoDiNascita, residenza, ruolo);

    }

    /**
     * <p>Metodo che si occupa di controllare se un nome utente sia già esistente o meno</p>
     * @return esegue una chiamata ricorsiva nel caso il nome utente sia già stato usato da un altro utente oppure restituisce il nome utente inserito
     */
    //metodo che fa un check anti duplicati sullo username
    private String checkUsernameDupe(){
        System.out.println("Inserire Username");
        String username = this.stringCheck();
        if(this.uh.checkUser(username) == null)
            return username;
        System.out.println("username non disponibile, cambiarlo: ");
        return this.checkUsernameDupe();
    }

    /**
     * <p>Metodo che si occupa controllare se la stringa inserita dall'utente sia corretto</p>
     * @return esegue una chiamata ricorsiva nel caso la stringa inserita non rispetti i criteri, nel caso positivo restituisce la stringa inserita
     */
    private String stringCheck() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (str.trim().isEmpty()) {
            System.out.println("Si prega di inserire un input valido \ninput: ");
            return stringCheck();
        }
        return str;
    }

    /**
     * <p>Metodo il quale scopo è il richiedere la password durante la procedura di registrazione /p>
     * @return se le password inserite dall'utente non sono le stesse esegue una chiamata ricorsiva per richiedere l'inserimento, nel caso positivo il metodo esegue una chiamata tramite l'istruzione <code>AESencrypt.encrypt(password)</code> una chiamata per la cryptazione della password
     * @throws Exception eccezione lanciata durante la cryptazione della password inserita
     */
    private String passencryption() throws Exception {
        System.out.println("inserire una password");
        String password = this.stringCheck();
        System.out.println("inserire nuovamente la password");
        String passcmp = this.stringCheck();
        if (!password.equals(passcmp)) {
            System.out.println("Le password non corrispondono, riprova.");
            return passencryption();
        }
        //encrypting della password
        try {
            return AESencrypt.encrypt(password);
        } catch (Exception e) {
            System.out.println("Errore nella password encrytption");
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Metodo che si occupa della scelta del ruolo da parte degli utenti durante la registrazione</p>
     * @return in base alla scelta effettuata dal cliente, assegna il ruolo associato in base alla scelta, ritorna null se l'input inserito non sia valido
     */
    private Roles chooseRole() {
        System.out.println("selezionare ruolo:\n1)cliente\n2)proiezionista\n3)bibliettaio ");
        int choice = Integer.parseInt(this.stringCheck());
        switch (choice) {
            case 1 -> {return Roles.CLIENTE;}
            case 2 -> {return Roles.PROIEZIONISTA;}
            case 3 -> {return Roles.BIGLIETTAIO;}
            default -> {
                System.out.println("input non valido, riprovare");
                return null;
            }
        }
    }

    /**
     * <p>Metodo il cui scopo è controllare se il giorno inserito dall'utente sia valido o non valido</p>
     * @return effettua tre chiamate ricorsive se il numero inserito è minore uguale a zero, maggiore di 31 o nel caso l'utente non inserisca affatto un numero
     */
    private int numbcheckergiorni() {
        String str = this.stringCheck();
        try {
            int numInt = Integer.parseInt(str);
            if (numInt <= 0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbcheckergiorni();
            }
            if (numInt > 31) {
                System.out.println("Il numero inserito non può essere maggiore di 31, rinserire il numero");
                return numbcheckergiorni();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbcheckergiorni();
        }
    }

    /**
     * <p>Metodo che si occupa di controllare se il mese inserito in numero si valido o meno</p>
     * @return esegue tre chiamate ricorsive nel caso in cui l'input inserito sia minore uguale di zero, maggiore di dodici o nel caso l'utente non inserisca affatto un numero
     */
    private int numbcheckermesi() {
        String str = this.stringCheck();
        try {
            int numInt = Integer.parseInt(str);
            if (numInt <= 0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbcheckermesi();
            }
            if (numInt > 12) {
                System.out.println("Il numero inserito non può essere maggiore di 12, rinserire il numero");
                return numbcheckermesi();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbcheckermesi();
        }
    }

    /**
     * <p>Metodo che si occupa se l'anno di nascita inserito sia valido o meno</p>
     * @return esegue tre chiamate ricorsive nel caso in cui l'input inserito sia minore uguale all'anno attuale meno 200, maggiore dell'anno odierno o nel caso l'utente non inserisca affatto un numero
     */
    private int numbcheckeranno() {
        String str = this.stringCheck();
        try {
            int numInt = Integer.parseInt(str);
            if (numInt <= Year.now().getValue()-200) {
                System.out.println("Anno non valido");
                return numbcheckeranno();
            }
            if (numInt > Year.now().getValue()) {
                System.out.println("Il numero inserito non può essere maggiore di "+ Year.now().getValue()+ ", rinserire il numero");
                return numbcheckeranno();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbcheckeranno();
        }
    }

    /**
     * <p>Metodo che controlla la data di nascita inserita dall'utente</p>
     * @return restituisce la data inserita con il formato anno-mese-giorno
     */
    private String inseriredata() {
        System.out.println("Inserire il giorno di nascita");
        int giorni = this.numbcheckergiorni();
        String valGiorni;
        if (giorni < 10) {
            valGiorni = "0" + giorni;
        } else {
            valGiorni = String.valueOf(giorni);
        }
        System.out.println("Inserire il mese di nascita(in  numeri):");
        int mesi = this.numbcheckermesi();
        String valMesi;
        if (mesi < 10) {
            valMesi = "0" + mesi;
        } else {
            valMesi = String.valueOf(mesi);
        }
        System.out.println("Inserire l'anno di nascita");
        String anno = String.valueOf(this.numbcheckeranno());
        return anno + "-" + valMesi + "-" + valGiorni;
    }

    /**
     * <p>Metodo che si di controllare se l'input inserito dall'utente sia valido</p>
     * @return esegue due chiamate ricorsive nel caso il numero inserito sia minore uguale di zero o nel caso l'input inserito non sia affatto un numero
     */
    private int numbCheck() {
        String str = this.stringCheck().trim();
        try {
            int numInt = Integer.parseInt(str);
            if (numInt <= 0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbCheck();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbCheck();
        }
    }

    /**
     * <p>Metodo che si ooccupa della funzione di login dei nuovi utenti</p>
     * @throws RuntimeException eccezione lanciata nel caso durante la procedura di login sia andato qualcosa storto
     */
    public void userLogin() throws RuntimeException {
        try {
           User user=this.uh.loginUser(); //chiedo all'utente di loggare e salva l'utente se lo trova
           if(user!=null) {
               this.loggedUser = user;
               System.out.println("Login effettuato come "+ this.loggedUser.getUsername());
               this.userMenu();
           }
           else
               System.out.println("Utente non trovato");
        } catch (Exception e) {
            System.out.println("Login non riuscito...");
            throw new RuntimeException(e) {
            };
        }
    }

    /**
     * <p>Metodo che si occupa dell'aggiunta delle proiezioni</p>
     * @return restituisce la data e l'orario della proiezione nel formato anno-mese-giorno-ore-minuti
     */
    private String dataproiezioni() {
        System.out.println("Inserire il giorno della proiezione");
        int giorni = this.numbcheckergiorni();
        String valGiorni;
        if (giorni < 10) {
            valGiorni = "0" + giorni;
        } else {
            valGiorni = String.valueOf(giorni);
        }
        System.out.println("Inserire il mese della proiezione(in  numeri):");
        int mesi = this.numbcheckermesi();
        String valMesi;
        if (mesi < 10) {
            valMesi = "0" + mesi;
        } else {
            valMesi = String.valueOf(mesi);
        }
        System.out.println("Inserire l'anno della proiezione");
        String anno = String.valueOf(this.numbcheckeranno());
        System.out.println("Inserire l'ora di  inizio del film:");
        String ore = String.valueOf(this.numbcheckore());
        String minuti = String.valueOf(this.numbcheckmin());
        return anno + "-" + valMesi + "-" + valGiorni+" "+ore+":"+minuti;

    }

    /**
     * <p>Metodo che si occupa di controllare i minuti inseriti durante la l'aggiunta delle proiezioni</p>
     * @return esegue delle chiamate ricorsive se l'input inserito è minore uguale a zero o maggiore di 60, nel caso positivo restituisce il numero di minuti
     */
    private int numbcheckmin(){
        String str = this.stringCheck();
        try{
            int value = Integer.parseInt(str);
            if(value<=0){
                System.out.println("Il numero inserito non può essere minore di 0");
                return numbcheckmin();
            }else if(value>60){
                System.out.println("Ci sono 60 minuti in un ora");
                return numbcheckmin();
            } return value;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Metodo che si occupa di controllare le ore inseriti durante la l'aggiunta delle proiezioni</p>
     * @return esegue delle chiamate ricorsive se l'input inserito è minore uguale a zero o maggiore di 24 nel caso positivo restituisce il numero di ore
     */
    private int numbcheckore(){
        String str = this.stringCheck();
        try{
            int value = Integer.parseInt(str);
            if(value<=0){
                System.out.println("Il numero inserito non può essere minore di 0");
                return numbcheckore();
            }else if(value>24){
                System.out.println("Il giorno è composto da 24 ore, reinserire");
                return numbcheckore();
            } return value;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>Metodo che si occupa di controllare il prezzo del biglietto della proiezione</p>
     * @return esegue delle chiamate ricorsive nel caso l'input inserito sia negativo o l'input non sia affatto un prezzo, nel caso positivo restituisce il prezzo del biglietto inserito
     */
    private float priceCheck() {
        String str = this.stringCheck();
        try {
            float prezzoFloat = Float.parseFloat(str);
            if (prezzoFloat < 0) {
                System.out.println("Il prezzo inserito non può essere negativo");
                return priceCheck();
            }
            return prezzoFloat;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return priceCheck();
        }


    }

    /**
     * <p>Metodo che controlla la durata della proiezione durante l'aggiunta</p>
     * @return esegue una chiamata ricorsiva nel caso in cui l'input inserito sia negativo, nel caso positivo restituisce la durata inserita
     */
    private int duratacheck(){{
        String str = this.stringCheck();
        try {
            int durataInt = Integer.parseInt(str);
            if (durataInt <= 0) {
                System.out.println("La durata inserita non può essere negativa, rinserire la durata");
                return duratacheck();
            }
            return durataInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return duratacheck();
        }
       }
    }

    /**
     * <p>Metodo che controlla l'età inserita durante l'inserimento di una proiezione</p>
     * @return esegue delle chiamate ricorsive nel caso in cui:l'input inserito non sia un numero, il numero inserito sia negativo, restituisce l'età inserita negli altri casi
     */
    private int etaCheck(){ //da ricontrollare
        try{
            int etaMinInt = this.checkNumIn();
            if (etaMinInt > 0) {
                if (etaMinInt >= 18) {
                    System.out.println("L'eta inserita supera la maggiore eta, il limite sarà impostato a 18");
                    etaMinInt = 18;
                    return etaMinInt;
                }
                return etaMinInt;
            } else {
                System.out.println("L'eta inserita non può essere negativa");
                return etaCheck();
            }
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return etaCheck();
        }
    }

    /**
     * <p>Metodo che si occupa di controllare la data di rilascio durante l'aggiunta di una proiezione</p>
     * @return esegue delle chiamate ricorsive nel caso in cui: la data rilasciata sia inferiore al 1888 o l'input inserito non sai affatto un numero, restituisce l'anno inserito
     */
    private int releaseCheck(){
        String str = this.stringCheck();
        try {
            int annoInt = Integer.parseInt(str);
            if (annoInt < 1888) {
                System.out.println("L'anno inserito non può essere inferiore al 1888, anno di uscita del primo film");
                return releaseCheck();
            } return annoInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return releaseCheck();
        }
    }

    //metodo userMenu
    /**
     * <p>Metodo che si occupa di indirizzare in base al ruolo, l' utente che ne usufruisce</p>
     */
    public void userMenu(){
        switch (this.loggedUser.getRole()){
            case CLIENTE -> this.client();
            case BIGLIETTAIO -> this.bigliettaio();
            case PROIEZIONISTA -> this.proiezionista();
            default -> System.out.println("Qualcosa è andato storto...");
        }
    }

    //metodo guest
    /**
     * <p>Metodo che simula il menu dei guest</p>
     */
    public void guest() {
        boolean repeat = true;
        while (repeat) {
            System.out.println("1)Cercare una proiezione \n2)Eseguire il LOGIN\n3)Eseguire una registrazione\n4)Torna a menu precedente");
            int num = numbCheck();
            switch (num) {
                case 1 -> {//aggiungere metodo di ricerca proiezione
                }
                case 2 -> {
                    System.out.println("Inizio procedura di login");
                    this.userLogin();
                }
                case 3 -> {
                    try {
                        this.userRegister();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case 4 -> repeat=false;
                default -> System.out.println("Qualcosa è andato storto...");
            }
        }
    }

    //metodo clienti
    /**
     * <p>Metodo che simula il menu dei clienti</p>
     */
    private void client(){
        boolean repeat = true;
        while(repeat){
            System.out.println("1)Cercare una proiezione \n2)Visualizza le mie prenotazioni \n3)Torna a menu precedente");
            int num = numbCheck();
            switch (num){
                case 1 -> {
                    this.cercaProiezioni();
                }
                case 2 -> this.visualizzaPrenotazioni();
                case 3 -> repeat=false;
                default -> System.out.println("Qualcosa è andato storto...");
            }
        }
    }

    //metodo proiezionisti
    /**
     * <p>Metodo che simula il menu dei proiezionisti</p>
     */
    private void proiezionista(){
        boolean repeat = true;
        while (repeat){
            System.out.println("0)Esci \n1)Cercare una proiezione \n2)Aggiunta proiezione");
            int num = checkNumIn();
            switch (num){
                case 0 -> repeat=false;
                case 1 -> this.cercaProPro();  //da modificare passandogli il RUOLO
                case 2 -> this.addProiezioni();
                default -> System.out.println("input non valido");
            }
        }
    }

    //metodo bigliettai
    /**
     * <p>Metodo che si occupa di simulare il menu dei bigliettai</p>
     */
    private void bigliettaio(){
        boolean repeat = true;
        while(repeat){
            System.out.println("1)Cerca una proiezione \n2)Visualizza tutte le prenotazioni");
            int num = numbCheck();
            switch (num){
                case 1 -> this.cercaProiezioni();
                case 2 -> {}//aggiungere un metodo per visualizzare ogni prenotazione effettuata, magari differenziando tutte le prenotazioni in base alla proiezione
            }
        }
    }

    /**
     * <p>Metodo che permette di cercare le proiezioni</p>
     */
    private void cercaProiezioni(){
        boolean repeat= true;
        while(repeat) {
            LinkedList<Proiezioni> proiezioniList = this.cercaProFilter();
            this.stampaProiezioni(proiezioniList);
            //un qualsiasi numero che non sia 0 o 1 continua a cercare proiezioni
            System.out.println("0)Esci \n1)Effettua una prenotazione \n2)Continua a cercare");
            int num = Integer.parseInt(this.stringCheck());
            if(num==1) {
                System.out.println("inserire l'indice della proiezioni da prenotare");
                effettuaPrenotazione(proiezioniList.get(this.numbCheck()-1));
            }
            if(num==0) repeat = false;
        }
    }

    /**
     * <p>Metodo che permette al proiezionista di cancellare e/o modificare le proiezioni</p>
     */
    private void cercaProPro(){
        boolean repeat=true;
        while(repeat){
            LinkedList<Proiezioni> proiezioniList = this.cercaProFilter();
            this.stampaProiezioni(proiezioniList);
            System.out.println("0)Esci \n1)modifica una proiezione \n2)cancella una proiezione");
            int num=numbCheck();
            switch (num){
                case 0 -> repeat=false;
                case 1 -> this.modificaProiezione(proiezioniList);
                case 2 -> {}//cancella proiezione
                default -> System.out.println("input non valido");
            }

        }
    }

    /**
     * <p>Metodo che permette la modifica di una proiezione</p>
     * @param foundP lista della proiezioni
     */
    private void modificaProiezione(LinkedList<Proiezioni> foundP){
        boolean repeat=true;
        System.out.println("inserire l'indice della proiezione da modificare");
        int index = checkNumIn();
        Proiezioni p =foundP.get(index);
        if(!this.cercaExPren(p))
            this.selezionaModificaPro(p,p.getTitolo(),p.getData());

    }

    /**
     * <p>Metodo per il proiezionista, che fa da interfaccia per le azioni disponibili</p>
     * @param p
     * @param titolo
     * @param data
     */
    private void selezionaModificaPro(Proiezioni p, String titolo, LocalDateTime data){
        boolean repeat=true;
        while (repeat){
            System.out.println("0)Esci \n1)Modifica Titolo \n2)Modifica Regista \n3)Modifica Genere \n4)Modifica Eta minima \n5)Modifica Data \n6)Modifca Prezzo \n7)Modifica Anno \n8)Modifica Durata");
            int num = this.checkNumIn();
            switch (num){
                case 0 -> repeat=false;
                case 1 -> {//modifica titolo
                    System.out.println("inserire il titolo modificato");
                    p.setTitolo(this.stringCheck());
                }
                case 2 -> {//modifica autore
                    System.out.println("inserire il regista modificato");
                    p.setRegista(this.stringCheck());
                }
                case 3 -> this.selezioneGenere(); //modifica genere
                case 4 -> {
                    System.out.println("inserire la nuova eta minima");
                    p.setEtaMin(this.etaCheck());
                }//modifica eta minima
                case 5 -> { //modifica data
                    System.out.println("inserire la nuova data");
                    p.setData(this.ph.convertDate(this.inserireData()));
                }
                case 6 -> { //modifica prezzo
                    System.out.println("inserire il nuovo prezzo");
                    p.setPrezzo(this.checkNumFloat());
                }
                case 7 -> { //modifica anno
                    System.out.println("inserire il nuovo anno");
                    p.setAnno(this.checkNumIn());
                }
                case 8 -> {
                    System.out.println("inserire la nuova durata, in minuti");
                    p.setDurata(this.checkNumIn());
                }//modifica durata
                default -> System.out.println("input non valido");
            }

        }

    }

    private void cancellaProiezione(){

    }

    /**
     * <p>Metodo per cercare le prenotazioni esistenti</p>
     * @param p oggetto di tipo prenotazione
     * @return restituisce un valore booleano in base all'esito della ricerca
     */
    private boolean cercaExPren(Proiezioni p){
        LinkedList<Prenotazione> prenList = this.prenh.getPrenList();
        for(Prenotazione pren:prenList){
            if(pren.getDate().equals(p.getData()) && pren.getTitolo().equals(p.getTitolo())){
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Metodo che restituisce a schermo la lista delle proiezioni</p>
     * @param proiezioniList lista delle proiezioni
     */
    private void stampaProiezioni(LinkedList <Proiezioni> proiezioniList){
        int index = 1;
        for (Proiezioni p : proiezioniList) {
            System.out.println("" + index + ")" + this.printProj(p));
            index++;
        }
    }

    /**
     * <p>Metodo che esegue la funzione ri cerca filtrata per la ricerca delle proiezioni</p>
     * @return restituisce la lista delle proiezioni compatibili con i filtri selezionati
     */
    private LinkedList<Proiezioni> cercaProFilter(){
        boolean repeat=true;
        LinkedList<Proiezioni> proiezioniList = this.ph.getProiezioniList();
        while (repeat){
            System.out.println("scegliere il filtro delle proiezioni: \n0)Applica i filtri selezionati \n1)Titolo \n2)Genere \n3)Data \n4)Autore \n5)Costo del biglietto");
            int num=Integer.parseInt(this.stringCheck());
            switch(num){
                case 0 -> repeat=false;
                case 1 -> {
                    System.out.println("inserire titolo: ");
                    proiezioniList=this.ph.filtroTitolo(proiezioniList,this.stringCheck());
                }
                case 2 -> {
                    System.out.println("Inserire il genere del film: ");
                    proiezioniList=this.ph.filtroGenere(proiezioniList,this.selezioneGenere());
                }
                case 3 -> {
                    System.out.println("Inserire le due date, prima quella iniziale e poi quella finale: ");
                    proiezioniList=this.ph.filtroData(proiezioniList,this.inseriredata(),this.inserireData());
                }
                case 4 -> {
                    System.out.println("Inserire l'autore del film: ");
                    proiezioniList=this.ph.filtroAutore(proiezioniList,this.stringCheck());
                }
                case 5 -> {
                    System.out.println("Inserire prezzo: ");
                    proiezioniList=this.ph.filtroPrezzo(proiezioniList, this.checkNumFloat());
                }
                default -> System.out.println("Input non valido");
            }
        }
        return proiezioniList;
    }

    /**
     * <p>Metodo che si occupa di richiedere e controllare la data inserita</p>
     * @return restituisce la data inserita nel formato: anno-mese-giorno
     */
    private String inserireData(){
        System.out.println("inserire anno: ");
        int anno = this.numbcheckeranno();
        System.out.println("inserire mese: ");
        int mese = this.numbcheckermesi();
        System.out.println("inserire giorno: ");
        int giorno = this.numbcheckergiorni();
        return anno+"-"+mese+"-"+giorno;
    }


    //metodo di testing per convertire i dati di un oggetto proiezione in una stringa
    /**
     * <p>Metodo che restituisce una rappresentazione testuale dell'oggetto Proiezioni</p>
     * @param p oggetto Proiezione
     * @return oggetto Proiezione convertito a stringa
     */
    private String printProj(Proiezioni p) {
        return " " +
                " " + p.getTitolo() +
                ", " + p.getGeneri() +
                ", " + p.getRegista() +
                ", " + p.getData() +
                ", " + p.getAnno() +
                ", " + p.getDurata() +
                ", " + p.getEtaMin() +
                ", " + p.getPrezzo();
    }

    /**
     * <p>Metodo che si occupa di effettuare la prenotazione per una proiezione</p>
     * @param p proiezione per cui si vuole fare una prenotazione
     */
    private void effettuaPrenotazione(Proiezioni p){
        //UUID genera un ID univoco
        this.prenh.createBooking(this.loggedUser.getUsername(), p.getTitolo(), p.getData(), UUID.randomUUID().toString());
        System.out.println("prenotazione effettuata");
    }

    /**
     * <p>Metodo che fa visualizzare le prenotazione all'utente</p>
     */
    private void visualizzaPrenotazioni(){
        Boolean repeat = true;
        while(repeat) {
            LinkedList<Prenotazione> foundList = this.prenh.visualizzaPrenotazioni(this.loggedUser.getUsername());
            if(foundList.isEmpty()) {
                System.out.println("non ci sono prenotazioni registrate a questo utente");
                repeat = false;
                continue;
            }
            int count = 1;
            for (Prenotazione tmp : foundList)
                System.out.println(count++ + ") " + tmp.getUsername() + ", " + tmp.getTitolo() + ", " + tmp.getDate());
            System.out.println("scegliere cosa fare:\n1)modificare una prenotazione\n2)cancellare una prenotazione\n0)uscire");
            switch (this.checkNumIn()) {
                case 1 -> this.modificaPrenotazione(foundList);
                case 2 -> this.cancellaPrenotazione(foundList);
                case 0 -> repeat = false;
                default -> System.out.println("Qualcosa è andato storto...");
            }
        }
    }

    /**
     * <p>Metodo che permette all'utente di poter cambiare la data di una prenotazione</p>
     * @param foundList lista delle prenotazioni
     */
    private void modificaPrenotazione(LinkedList<Prenotazione> foundList){
        System.out.println("inserisci indice della prenotazione da modificare");
        int index = this.checkNumIn();
        Prenotazione pToModify = foundList.get(index-1);
        if (pToModify.getDate().isAfter(LocalDateTime.now())){
            LinkedList<Proiezioni> foundProj = this.ph.afterData(this.ph.searchProiezione(pToModify.getTitolo()),LocalDateTime.now());
            stampaProjDate(foundProj);
            this.prenh.modificaPrenotazione(pToModify.getId(),this.cambioData(foundProj));
            System.out.println("Data modificata con successo");
        }
        else{
            System.out.println("la data inserita precede quella odierna, non risulta possibile modificarla");
        }
    }

    //chiede all'utente la data con cui cambiare la prenotazione
    /**
     * <p>Metodo che esegue il cambio di date delle prenotazioni</p>
     * @param foundProj lista delle proiezioni
     * @return restituisce la nuova data della prenotazione
     */
    private LocalDateTime cambioData(LinkedList<Proiezioni> foundProj){
        System.out.println("inserire l'indice della data con cui si vuole sostituire la prenotazione");
        int index = this.checkNumIn();
        return foundProj.get(index-1).getData();
    }

    //stampa la lista di tutte le date possibili
    /**
     * <p>Metodo che fa vedere all'utente tutte le possibile date per una proiezione</p>
     * @param foundProj lista delle proiezioni
     */
    private void stampaProjDate(LinkedList<Proiezioni> foundProj){
        int index=1;
        for (Proiezioni tmp:foundProj){
            System.out.println(""+index++ +") "+ tmp.getData());
        }
    }

    //metodo che cancella le prenotazioni
    /**
     * <p>Metodo che si occupa di cancellare una prenotazione</p>
     * @param foundList lista delle prenotazioni
     */
    private void cancellaPrenotazione(LinkedList<Prenotazione> foundList){
        System.out.println("inserisci indice prenotazione da cancellare");
        int index = this.checkNumIn();
        Prenotazione pToDelete = foundList.get(index-1);
        Boolean result = this.prenh.eliminaPrenotazione(pToDelete.getId()); // elimina prenotazione da file csv e lista globale
        if(result)
            System.out.println("prenotazione rimossa con successo");
        else
            System.out.println("errore nel cancellamento della prenotazione: \nnon trovata o data della proiezione successiva a quella odierna,\nriprovare");
    }

    //sotto metodo che prende in input un intero e lo controla
    /**
     * <p>Metodo che controllare i numeri in input</p>
     * @return restituisce il numero inserito se non ci sono problemi, altrimenti esegue una chiamata ricorsiva
     */
    //sotto metodo che prende in input un intero e lo controlla
    private int checkNumIn(){
        int num = Integer.parseInt(this.stringCheck());
        if(num < 0){
            System.out.println("numeri < 0 non validi");
            return this.checkNumIn();
        }
        return num;
    }

    //sotto metodo che prende in input un float e lo controlla
    /**
     * <p>Metodo che controlla i prezzi del biglietto</p>
     * @return esegue una chiamata ricorsiva nel caso in cui il numero siamo minore di zero, restituisce il numero inserito nel caso l'input sia valido
     */
    private float checkNumFloat(){
        float num = Float.parseFloat(this.stringCheck());
        if(num < 0){
            System.out.println("numeri < 0 non validi");
            return this.checkNumFloat();
        }
        return num;
    }
}



