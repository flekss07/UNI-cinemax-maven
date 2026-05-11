import java.time.LocalDate;

public class User {
    private String nome;
    private String cognome;
    private String password;
    private String username;
    private LocalDate dataDiNascita;
    private String indirizzo;
    private Roles ruolo;
    public User(String nome, String cognome, String password, String username,LocalDate dataDiNascita,String indirizzo, Roles ruolo){
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.username = username;
        this.dataDiNascita = dataDiNascita;
        this.indirizzo = indirizzo;
        this.ruolo = ruolo;
    }
    
    // getter

    public String getNome(){ return this.nome; }
    public String getCognome(){ return this.cognome; }
    public String getPassword(){ return this.password; }
    public String getUsername(){ return this.username; }
    public LocalDate getDataDiNascita() { return this.dataDiNascita; }
    public String getIndirizzo() { return this.indirizzo; }
    public Roles getRole(){ return this.ruolo; }

    //setter

    public void setNome(String nome){ this.nome = nome; }
    public void setCognome(String cognome){ this.cognome = cognome; }
    public void setPassword(String password){ this.password = password; }
    public void setUsername(String username){ this.username = username; }
    public void setDataDiNascita(LocalDate dataDiNascita){ this.dataDiNascita = dataDiNascita; }
    public void setIndirizzo(String indirizzo){ this.indirizzo = indirizzo; }
}
