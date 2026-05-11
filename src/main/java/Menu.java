public class Menu {
    private UserHandler uh;

    public Menu(){ //costruzione oggetto classe userhandler
        this.uh = new UserHandler();
    }

    public void MenuSelect() { // costruttore menu
        int selector = 3;
        while(){
            switch(selector){
                case 1://registrarsi
                    this.userRegister();
                case 2://login
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

    //metodo register
    public String userRegister() throws RuntimeException {
        try {
            this.uh.addUser();
        } catch (Exception e) {
            throw new RuntimeException(e){
                System.out.println("Registrazione non riuscita...");
            };
        }
        System.out.println("Registato/a con successo!");
    }

    //metodo login
    public String userLogin() throws RuntimeException {
        try {
            this.uh.loginUser();
        } catch (Exception e) {
            throw new RuntimeException(e){
                System.out.println("Login non riuscito...");
            };
        }
        System.out.println("Login eseguito con successo!");
    }

    //metodo guest
    public void Guest(){

    }
}
