import javax.management.relation.Role;
import java.awt.desktop.AboutEvent;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final UserHandler uh;
    private final ProiezioniHandler ph ;
    private final PrenotazioniHandler prenh;
    private User loggedUser;

    public Menu() { //costruzione oggetto classe userhandler
        this.uh = new UserHandler();
        this.ph= new ProiezioniHandler();
        this.prenh= new PrenotazioniHandler();
    }

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

    //metodo che fa un check anti duplicati sullo username
    private String checkUsernameDupe(){
        System.out.println("Inserire Username");
        String username = this.stringCheck();
        if(this.uh.checkUser(username) == null)
            return username;
        System.out.println("username gia esistente, cambiarlo: ");
        return this.checkUsernameDupe();
    }

    private String stringCheck() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (str.trim().isEmpty()) {
            System.out.println("Si prega di inserire un input valido \ninput: ");
            return stringCheck();
        }
        return str;
    }

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

    private int numbcheckermesi() {
        String str = this.stringCheck();
        try {
            int numInt = Integer.parseInt(str);
            if (numInt <= 0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbcheckermesi();
            }
            if (numInt > 12) {
                System.out.println("Il numeroinserito non può essere maggiore di 12, rinserire il numero");
                return numbcheckermesi();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbcheckermesi();
        }
    }

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
    public void userMenu(){
        switch (this.loggedUser.getRole()){
            case CLIENTE -> this.client();
            case BIGLIETTAIO -> this.bigliettaio();
            case PROIEZIONISTA -> this.proiezionista();
            default -> System.out.println("Qualcosa è andato storto...");
        }
    }

    //metodo guest
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
    private void proiezionista(){
        boolean repeat = true;
        while (repeat){
            System.out.println("0)Esci \n1)Cercare una proiezione \n2)Aggiunta proiezione");
            int num = numbCheck();
            switch (num){
                case 0 -> repeat=false;
                case 1 -> this.cercaProPro();  //da modificare passandogli il RUOLO
                case 2 -> this.addProiezioni();
                default -> System.out.println("input non valido");
            }
        }
    }

    //metodo bigliettai
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

    private void modificaProiezione(LinkedList<Proiezioni> foundP){
        boolean repeat=true;
        System.out.println("inserire l'indice della proiezione da modificare");
        int index = checkNumIn();
        Proiezioni p =foundP.get(index);
        if(!this.cercaExPren(p))
            this.selezionaModificaPro(p,p.getTitolo(),p.getData());

    }

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

    private boolean cercaExPren(Proiezioni p){
        LinkedList<Prenotazione> prenList = this.prenh.getPrenList();
        for(Prenotazione pren:prenList){
            if(pren.getDate().equals(p.getData()) && pren.getTitolo().equals(p.getTitolo())){
                return true;
            }
        }
        return false;
    }

    private void stampaProiezioni(LinkedList <Proiezioni> proiezioniList){
        int index = 1;
        for (Proiezioni p : proiezioniList) {
            System.out.println("" + index + ")" + this.printProj(p));
            index++;
        }
    }

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
     * Metodo che restituisce una rappresentazione testuale dell'oggetto Proiezioni
     *
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

    private void effettuaPrenotazione(Proiezioni p){
        //UUID genera un ID univoco
        this.prenh.createBooking(this.loggedUser.getUsername(), p.getTitolo(), p.getData(), UUID.randomUUID().toString());
        System.out.println("prenotazione effettuata");
    }

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
    private LocalDateTime cambioData(LinkedList<Proiezioni> foundProj){
        System.out.println("inserire l'indice della data con cui si vuole sostituire la prenotazione");
        int index = this.checkNumIn();
        return foundProj.get(index-1).getData();
    }

    //stampa la lista di tutte le date possibili
    private void stampaProjDate(LinkedList<Proiezioni> foundProj){
        int index=1;
        for (Proiezioni tmp:foundProj){
            System.out.println(""+index++ +") "+ tmp.getData());
        }
    }


    //metodo che cancella le prenotazioni
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
    private float checkNumFloat(){
        float num = Float.parseFloat(this.stringCheck());
        if(num < 0){
            System.out.println("numeri < 0 non validi");
            return this.checkNumFloat();
        }
        return num;
    }
}



