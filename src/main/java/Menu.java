import java.util.Scanner;

public class Menu {
    private final UserHandler uh;

    public Menu() { //costruzione oggetto classe userhandler
        this.uh = new UserHandler();
    }

    public void MenuSelect() { // costruttore menu

        while (true) {
            System.out.println("Inserire il numero corrispondente alla funzione x attivarla\n1)registrarsi\neffettuare il login\n3)Continuare come quest ");
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



    public  void userRegister() throws Exception {
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

        return AESencrypt.encrypt(password);
    }
    private Roles chooseRole(){
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
    private int numbcheckergiorni(){
        String str = this.stringCheck();
        try {
            int numInt = Integer.parseInt(str);
            if (numInt <= 0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbcheckergiorni();
            }
            if(numInt >31){
                System.out.println("Il numeroinserito non può essere maggiore di 31, rinserire il numero");
                return numbcheckergiorni();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbcheckergiorni();
        }
    }
    private int  numbcheckermesi(){
        String str = this.stringCheck();
        try {
            int numInt = Integer.parseInt(str);
            if (numInt <= 0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbcheckermesi();
            }
            if(numInt >12){
                System.out.println("Il numeroinserito non può essere maggiore di 12, rinserire il numero");
                return numbcheckermesi();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbcheckermesi();
        }
    }
    private int numbcheckeranno(){
        String str = this.stringCheck();
        try {
            int numInt = Integer.parseInt(str);
            if (numInt <=0) {
                System.out.println("Il numero inserito non può essere negativo, rinserire il numero");
                return numbcheckeranno();
            }
            if(numInt >2026){
                System.out.println("Il numeroinserito non può essere maggiore di 2026, rinserire il numero");
                return numbcheckeranno();
            }
            return numInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return numbcheckeranno();
        }
    }
    private String inseriredata(){
        System.out.println("Inserire il giorno di nascita");
        int giorni = this.numbcheckergiorni();
        String valGiorni;
        if(giorni<10){
            valGiorni = "0"+giorni;
        }else {
            valGiorni = String.valueOf(giorni);
        }
        System.out.println("Inserire il mese di nascita(in  numeri):");
        int mesi = this.numbcheckermesi();
        String valMesi;
        if(mesi<10){
             valMesi = "0"+mesi;
        }else{
            valMesi = String.valueOf(mesi);
        }
        System.out.println("Inserire l'anno di nascita");
        String anno = String.valueOf(this.numbcheckeranno());
        return anno+"-"+valMesi+"-"+valGiorni;
    }
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
    public void userLogin() throws RuntimeException {
        try {
            this.uh.loginUser();
        } catch (Exception e) {
            System.out.println("Login non riuscito...");
            throw new RuntimeException(e){
            };
        }
        System.out.println("Login eseguito con successo!");
    }

    //metodo guest
    public void Guest(){

    }
}



