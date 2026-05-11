import java.util.LinkedList;
import java.util.Scanner;

public class ProiezioniHandler {
    private LinkedList<User> proiezioniList;

    public ProiezioniHandler(LinkedList proiezioniList) {
        this.proiezioniList = proiezioniList;
    }

    public void addProiezione() {
        Proiezioni NuovaProiezione = new Proiezioni();
        Scanner sc = new Scanner(System.in);
        //Aggiunta titolo a nuova proiezione
        System.out.println("Inserire il Titolo");
        String titolo = this.stringCheck();
        //Inserimento genere(DA FINIRE)
        System.out.println("Inserire il genere inserendo il numerino assegnato");
        String genere = sc.nextLine();
            /*if(!genere.trim().isEmpty()){
            }*/
        // da aggiungere dopo aver fatto l'enum
        //Inserimento regista
        System.out.println("Inserire il regista");
        String regista  = this.stringCheck();
        //Inserimento data
        //da fare
        //Inserimento durata
        System.out.println("Inserire la durata della proiezione (in minuti)");
        int durata = this.dataCheck();
        //Inserimento etàMinima
        System.out.println("Inserire l'età minima");
        int etaMin = this.etaCheck();
        //inserimento anno d'uscita
        System.out.println("Inserire l'anno di pubblicazione del film");
        int anno = this.releaseCheck();
        //inserimento del prezzo
        System.out.println("Inserire il prezzo del film");
        float prezzo = this.priceCheck();
    }
    private String stringCheck() {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        if (!str.trim().isEmpty()) {
            System.out.println("Si prega di inserire un input valido \ninput: ");
            return stringCheck();
        }
        return str;
    }
    private int dataCheck(){{
        String str = this.stringCheck();
        try {
            int durataInt = Integer.parseInt(str);
            if (durataInt <= 0) {
                System.out.println("La durata inserita non può essere negativa, rinserire la durata");
                return dataCheck();
            }
            return durataInt;
        } catch (NumberFormatException e) {
            System.out.println("Quello che hai inserito non è un numero. Riprova");
            return dataCheck();
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
    private float  priceCheck(){
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






}