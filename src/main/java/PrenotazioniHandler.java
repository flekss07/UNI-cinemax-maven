import java.util.LinkedList;
import java.util.Scanner;


public class PrenotazioniHandler {
    private LinkedList<User> userList;
    private LinkedList<User> proiezioniList;

    //costruttore
    public PrenotazioniHandler (LinkedList proiezioniList, LinkedList userList){
        this.proiezioniList=proiezioniList;
        this.userList=userList;
    }

    //metodo di aggiunta prenotazione
    public void addPrenotazione(){
        Scanner sc = new Scanner(System.in);

        //placeholder

        
        System.out.println("digitare il titolo della proiezione che si vuole prenotare");
        String titoloP=sc.nextLine();


        //Prenotazioni nuovaPrenotazioni = new Prenotazioni();

    }


    //sotto metodo che fa il check della stringa
    private String stringCheck() {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        if (!str.trim().isEmpty()) {
            System.out.println("Si prega di inserire un input valido \ninput: ");
            return stringCheck();
        }
        return str;
    }
}
