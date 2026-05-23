import java.util.Scanner;

/**
 * Classe Menù, classe che farà da interfaccia per gli utenti che usufruiranno dei servizi
 */
public class Menu {
    /**
     * oggetto di tipo UserHandler
     */
    private final UserHandler uh;
    /**
     * oggetto di tipo ProiezioniHandler
     */
    private final ProiezioniHandler ph ;

    /**
     * Costruttore degli oggetti di tipo UserHandler e ProiezioniHandler
     */
    public Menu() { //costruzione oggetto classe userhandler
        this.uh = new UserHandler();
        this.ph= new ProiezioniHandler();
    }

    /**
     * Metodo che fa scegliere se si vuole registrarsi, fare come il login oppure continuare come guest
     */
    public void menuSelect() { // costruttore menu

        while (true) {
            System.out.println("Inserire il numero corrispondente alla funzione per attivarla\n1)registrarsi\n2)effettuare il login\n3)Continuare come quest ");
            int selector = this.numbCheck();
            switch (selector) {
                case 1://registrarsi
                    try {
                        this.userRegister();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                case 2://login
                    System.out.println("Inizio procedura di login");
                    this.userLogin();
                    break;
                case 3://guest
                    this.Guest();
                    break;
                default:
                    System.out.println("Qualcosa è andato storto...");
            }
        }


    }

    /**
     * Metodo che si occupa dell'inserimento di nuove proiezioni
     */
    public void Addproiezioni() {
        //inserire il genere del film
        Genres genere = this.SelezioneGenere();
        //inserire il titolo
        System.out.println("Inserire il Titolo");
        String titolo = this.stringCheck();
        //inserire il regista
        System.out.println("Inserire il  regista");
        String regista = this.stringCheck();
        //giorno e data della proiezione
        String dataProiezioni = this.dataproiezioni();
        //durata  del film
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
        this.ph.proiezionicreator(genere, titolo, regista, dataProiezioni,durata, etaMin, uscita, prezzo);
    }
//funzione x selezione genere

    /**
     * Metodo che si occupa della selezione del genere
     *
     * @return uno switch da cui si può scegliere il genere che si cerca
     */
    private Genres SelezioneGenere() {
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
                yield this.SelezioneGenere();
            }
        };
    }

    /**
     * Metodo che si occupa di controllare se il numero inserito sia valido o meno
     *
     * @param s input dell'utente
     * @return in base al numero inserito darà: una chiamata ricorsiva per valori negativi o
     * nel caso in cui il numero non rientri nel range indicato
     */
    private int numbchecker(String s) {
        try {
            int value = Integer.parseInt(s);
            if (value <= 0) {
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
     * Metodo che si occupa della registrazione di nuovi utenti alla piattaforma
     *
     * @throws Exception eccezione lanciata nel caso qualcosa andasse storto
     */
    public void userRegister() throws Exception {
        //Inserimento nome
        System.out.println("Inserire Nome");
        String nome = this.stringCheck();
        //inserimento cognome
        System.out.println("Inserire Cognome");
        String cognome = this.stringCheck();
        //inserimento username
        System.out.println("Inserire Username");
        String username = this.stringCheck();
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
     * Metodo che si occupa dei controlli su un input inserito dall'utente
     *
     * @return restituisce lla stringa inserita dall'utente nel caso non ci fossero problemi,
     * altrimenti esegue una chiamata ricorsiva
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
     * Metodo che si occupa di controllare la password inserita dall'utente
     *
     * @return restituisce la password cryptata oppure esegue una chiamata ricorsiva nel caso le password messe dall'utente non combacino
     * @throws Exception eccezione lanciata nel caso ci fossero problemi nella cryptazione
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
     * Metodo che fa scegliere il proprio ruolo all'utente
     *
     * @return restituisce il ruolo selezionato, oppure un messaggio se la scelta non corrisponde alle scelte disponibili
     */
    private Roles chooseRole() {
        System.out.println("selezionare ruolo:\n1)cliente\n2)proiezionista\n3)bibliettaio ");
        int choice = Integer.parseInt(this.stringCheck());
        switch (choice) {
            case 1:
                return Roles.CLIENTE;
            case 2:
                return Roles.PROIEZIONISTA;
            case 3:
                return Roles.BIGLIETTAIO;
            default:
                System.out.println("input non valido, riprovare");
                return null;
        }
    }

    /**
     * Metodo che durante la ricerca si occupa di fare controlli sul giorno inserito
     *
     * @return esegue delle chiamate ricorsive nel caso in cui il numero inserito sia negativo, maggiore di 31, oppure che l'input inserito non sia un numero
     * nel caso positivo restituirà il numero inserito
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
     * Metodo che durante la ricerca si occupa di fare controlli sul mese inserito
     *
     * @return esegue delle chiamate ricorsive nel caso in cui il numero inserito sia negativo, maggiore di 12, oppure che l'input inserito non sia un numero
     * nel caso positivo restituirà il numero inserito
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
                System.out.println("Il numeroinserito non può essere maggiore di 12, rinserire il numero");
                return numbcheckermesi();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbcheckermesi();
        }
    }

    /**
     * Metodo che durante la ricerca si occupa di fare controlli rispetto all'anno inserito
     *
     * @return esegue delle chiamate ricorsive nel caso in cui il numero inserito sia negativo, maggiore di 2026, oppure che l'input inserito non sia un numero
     * nel caso positivo restituirà il numero inserito
     */
    private int numbcheckeranno() {
        String str = this.stringCheck();
        try {
            int numInt = Integer.parseInt(str);
            if (numInt <= 0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbcheckeranno();
            }
            if (numInt > 2026) {
                System.out.println("Il numeroinserito non può essere maggiore di 2026, rinserire il numero");
                return numbcheckeranno();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbcheckeranno();
        }
    }

    /**
     * Metodo che richiede l'inserimento di una data
     *
     * @return restituisce la data in formato anno-mese-giorno
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
     * Metodo che si occupa di fare dei controlli sul numero messo come input
     *
     * @return esegue delle chiamate ricorsive nel caso in cui il numero inserito fosse negativo oppure non fosse un numero affatto,
     * altrimenti restituisce il valore del numero
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
     * Metodo che si occupa del login di un utente già registrato
     *
     * @throws RuntimeException eccezione lanciata nel caso il login non sia stato un successo
     */
    public void userLogin() throws RuntimeException {
        try {
           this.uh.loginUser();
        } catch (Exception e) {
            System.out.println("Login non riuscito...");
            throw new RuntimeException(e) {
            };
        }
        System.out.println("Login eseguito con successo!");
    }

    //metodo guest
    public void Guest() {

    }

    /**
     * Metodo che durante la ricerca si occupa di fare controlli sulla data inserita
     *
     * @return restituisce la data della proiezione con il formato anno-mese-giorni ore:minuti
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
     * Metodo che durante la ricerca si occupa di fare controlli sui minuti inseriti
     *
     * @return esegue chiamate ricorsive se il numero inserito è minore di 0 oppure se non è affatto un numero,
     * altrimenti restituisce il valore inserito
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
     * Metodo che durante la ricerca si occupa di fare controlli sul prezzo del biglietto inserito
     *
     * @return esegue chiamate ricorsive qualora il prezzo del biglietto inserito sia negativo oppure il numero inserito non sia valido, altrimenti restituisce il prezzo
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
     * Metodo che esegue controlli sulla durata dei film durante la ricerca
     *
     * @return esegue chiamate ricorsive nel caso la durata inserita sia negativa compreso lo zero,
     * oppure se l'input non sia affato un numero, altrimenti restituisce la durata
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
     * Metodo che durante la ricerca si occupa di fare controlli sull'età minima inserita
     *
     * @return esegue delle chiamate ricorsive qualora l'età inserita sia negativa oppure l'input non sia un numero affatto,
     * altrimenti restituisce l'età inserita nel caso sia maggiore uguale a 18
     */
    private int etaCheck(){
        String str = this.stringCheck();
        try{
            int etaMinInt = Integer.parseInt(str);
            if (etaMinInt >= 0) {
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
     * Metodo che durante la ricerca si occupa di fare controlli sulla data di rilascio inserita
     *
     * @return esegue delle chiamate ricorsive nel caso in cui il numero inserito sia inferiore all 1888, sia quando l'input inserito non sia un numero
     * nel caso positivo restituirà l'anno del film
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

}



