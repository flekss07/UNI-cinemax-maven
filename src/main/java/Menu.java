import java.awt.desktop.AboutEvent;
import java.time.Year;
import java.util.Scanner;

public class Menu {
    private final UserHandler uh;
    private final ProiezioniHandler ph ;
    private User loggedUser;

    public Menu() { //costruzione oggetto classe userhandler
        this.uh = new UserHandler();
        this.ph= new ProiezioniHandler();
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
                case 3->  this.Guest();
                case 4-> repeat=false; //close menu
                default-> System.out.println("Qualcosa è andato storto...");
            }
        }


    }

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
                System.out.println("Anno di nascita non valido");
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
           if(user!=null)
               loggedUser=user;
           else
               System.out.println("Utente non trovato");
        } catch (Exception e) {
            System.out.println("Login non riuscito...");
            throw new RuntimeException(e) {
            };
        }
        System.out.println("Login effettuato come "+ loggedUser.getUsername());
    }

    //metodo guest
    public void Guest() {

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



