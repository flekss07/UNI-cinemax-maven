import java.time.LocalDateTime;

public class Prenotazione {
    private String username;
    private String titolo;
    private LocalDateTime date;
    private String id;

    public Prenotazione(String username, String titolo, LocalDateTime date,String id) {
        this.username = username;
        this.titolo = titolo;
        this.date = date;
        this.id = id;
    }

    public String getUsername(){ return this.username; }
    public String getTitolo(){ return this.titolo; }
    public LocalDateTime getDate(){ return this.date; }
    public String getId(){ return this.id; }

    //setter
    public void setTitolo(String titolo){this.titolo = titolo;}
    public void setDate(LocalDateTime date){this.date = date;}
    public void setUsername(String username){this.username = username;}
}
