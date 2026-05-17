import java.util.Scanner;

public class Menu {
    private final UserHandler uh;
    private final ProiezioniHandler ph ;

    public Menu() { //costruzione oggetto classe userhandler e proiezionehandler
        this.uh = new UserHandler();
        this.ph= new ProiezioniHandler();
    }

    public void menuSelect() { // costruttore menu

        while (true) {
            System.out.println("Inserire il numero corrispondente alla funzione x attivarla\n1)registrarsi\n2)effettuare il login\n3)Continuare come quest ");
            int selector = this.numbCheck();
            switch (selector) {
                //registrarsi
                case 1 -> {//registrarsi
                    try {
                        this.userRegister();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                // login
                 case 2-> {
                    System.out.println("Inizio procedura di login");
                     User user = null;
                     try {
                         user = this.uh.loginUser();
                     } catch (Exception e) {
                         throw new RuntimeException(e);
                     }
                     this.menuRuoli(user);
                }
                //guest
                case 3-> {
                    this.Guest();
                }
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
        //Soltanto per interfaccia grafica
        for (int i = 0; i < genres.length; i++) {
            System.out.println((i + 1) + "\t" + genres[i].toString());
        }
        
        int caso = this.numbchecker();
        Genres genere;
        // SI USA LE ARROW PERCHE DA JAVA 14 E' AGGIORNATO COSI RENDENDOLE PIU FACILE DA USARE
        return switch (caso) {
            case 1 -> genere = Genres.Animazione;
            case 2 -> genere = Genres.Avventura;
            case 3 -> genere = Genres.Comico;
            case 4 -> genere = Genres.Commedia;
            case 5 -> genere = Genres.Documentario;
            case 6 -> genere = Genres.Drammatico;
            case 7 -> genere = Genres.Fantascienza;
            case 8 -> genere = Genres.Fiabesco;
            case 9 -> genere = Genres.Horror;
            case 10 -> genere = Genres.Thriller;
            default -> {
                System.out.println("Qualcosa è andato storto, riprova");
                yield genere= this.SelezioneGenere();
            }
        };
    }

    //controllo validità input (al momento solo in classe genere)
    private int numbchecker() {
        String s = this.stringCheck();
        try {
            int value = Integer.parseInt(s);
            if (value <= 0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbchecker();
            } else if (value > 10) {
                System.out.print("Il numero non è nel range, inserirne un'altro");
                return numbchecker();
            }
            return value;
        } catch (RuntimeException e) {
            System.out.println("Qualcosa è andato storto riprova");
            return numbchecker();
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
        System.out.println("Inserire Username");
        String username = this.stringCheck();
        System.out.println("Inserire indirizzo di residenza");
        String indirizzo = this.stringCheck();
        String annoDiNascita = this.inseriredata();
        //inserimento ruolo
        Roles ruolo = this.chooseRole();
        //inserimento della password
        String password = this.passencryption();
        this.uh.addUser(nome, cognome, password, username, annoDiNascita, indirizzo, ruolo);

    }

    //controlla che la stringa non sia vuota o non sia null
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
        //controlla che entrambe le password inserite siano uguali
        if (!password.equals(passcmp)) {
            System.out.println("Le password non corrispondono, riprova.");
            return passencryption();
        }
        //encrypting della password
        return AESencrypt.encrypt(password);
    }

    //scelta dei ruoli
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

    //controlla che i giorni della data inserita abbia valori accettabili
    private int numbcheckergiorni() {
        String str = this.stringCheck();
        try {
            int numInt = Integer.parseInt(str);
            if (numInt <= 0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbcheckergiorni();
            }
            if (numInt > 31) {
                System.out.println("Il numeroinserito non può essere maggiore di 31, rinserire il numero");
                return numbcheckergiorni();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbcheckergiorni();
        }
    }

    //controlla che i mesi della data inserita abbiano valori accettabili
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

    //controlla che gli anni della data inseita abbiano valori accettabili
    private int numbcheckeranno() {
        String str = this.stringCheck();
        try {
            int numInt = Integer.parseInt(str);
            if (numInt <= 0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbcheckeranno();
            }
            if (numInt >= 2018) {
                System.out.println("Il numeroinserito non può essere maggiore di 2018, rinserire il numero");
                return numbcheckeranno();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbcheckeranno();
        }
    }

    //metodo di inserimento della data (chiama i metodi di check dei giorni, mesi e anni)
    private String inseriredata() {
        System.out.println("Inserire il giorno di nascita");
        int giorni = this.numbcheckergiorni();
        //formatazione dei giorni (es. 2 ---> 02)
        String valGiorni;
        if (giorni < 10) {
            valGiorni = "0" + giorni;
        } else {
            valGiorni = String.valueOf(giorni);
        }
        System.out.println("Inserire il mese di nascita(in  numeri):");
        int mesi = this.numbcheckermesi();
        //formattazione dei mesi (es. febbraio = 2 ---> 02)
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
/*
    NUMB CHECK NON CHIAMATO
    private int numbCheck() {
        String str = this.stringCheck();
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
*/
    //metodo guest
    public void Guest() {

    }

    private String dataproiezioni() {
        System.out.println("Inserire il giorno della proiezione");
        int giorni = this.numbcheckergiorni();
        //formattazione dei giorni
        String valGiorni;
        if (giorni < 10) {
            valGiorni = "0" + giorni;
        } else {
            valGiorni = String.valueOf(giorni);
        }
        System.out.println("Inserire il mese della proiezione(in  numeri):");
        int mesi = this.numbcheckermesi();
        //formattazione dei mesi
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

    //controllo della validità dei minuti inseriti
    private int numbcheckmin(){
        String str = this.stringCheck();
        try{
            int value = Integer.parseInt(str);
            if(value<=0){
                System.out.println("Il numero inserito non può essere minore di 0");
                return numbcheckmin();
            }else if(value>60){
                System.out.println("Ci sono 60 minuti in un ora, inserire un numero compreso tra 0 e 59");
                return numbcheckmin();
            } return value;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    //controllo della validità delle ore inserite
    private int numbcheckore(){
        String str = this.stringCheck();
        try{
            int value = Integer.parseInt(str);
            if(value<=0){
                System.out.println("Il numero inserito non può essere minore di 0");
                return numbcheckore();
            }else if(value>24){
                System.out.println("Il giorno è composto da 24 ore, inserire un numero compreso tra 0 e 23");
                return numbcheckore();
            } return value;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    //controllo della validità del prezzo inserito
    private float priceCheck() {
        String str = this.stringCheck();
        try {
            float prezzoFloat = Float.parseFloat(str);
            if (prezzoFloat <= 0) {
                System.out.println("Il prezzo inserito non può essere negativo");
                return priceCheck();
            }
            return prezzoFloat;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return priceCheck();
        }


    }

    //controllo della validità della durata della proiezione inserita
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

    //controllo dell'età minima per la visione della proiezione
    
    private int etaCheck(){
        String str = this.stringCheck();
        try{
            int etaMinInt = Integer.parseInt(str);
            //da ricontrollare
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

    //controllo della data di uscita del film
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

    //impostazione dei ruoli nel menu
    public void menuRuoli(User user){
        switch(user.getRole()){
            case CLIENTE ->  this.menuCliente(user);
            case BIGLIETTAIO ->  this.menuBigliettaio(user);
            case PROIEZIONISTA -> this.menuProiezionisti(user);
            default -> {
                System.out.println("Ruolo inesistente...");
                this.menuRuoli(user);
            }
        }
    }
    //menu del cliente
    public void menuCliente(User user){
        int scelta = 7;
        switch(scelta){
            case 1 -> this.ph.cercaProiezione();
            case 2 -> this.ph.visualizzaProiezione();
            case 3 -> this.creaPrenotazione();
            case 4 -> this.visualizzaPrenotazione();
            case 5 -> this.modificaPrenotazione();
            case 6 -> this.eliminaPrenotazione();
            case 7 ->
            default ->
        }

    }
    //menu del bigliettaio
    public void menuBigliettaio(User user){

    }
    //menu del proiezionista
    public void menuProiezionisti(User user){

    }
    public String creaPrenotazione(){}
    public void visualizzaPrenotazione(){}
    public Proiezioni modificaPrenotazione(){}
    public String eliminaPrenotazione(){}
}



