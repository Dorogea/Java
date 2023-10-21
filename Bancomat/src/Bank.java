import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;

    private ArrayList<User> user;

    private ArrayList<Account> accounts;

    /**
     * Cream un obiect nou numit banca cu o lista goala pentru useri si conturi
     * name
     */
    public Bank(String name) {

        this.name = name;
        this.user = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    //Generam un ID nou pentru utilizator 
    public String getNewUserUUID() {

        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        // Facem un loop in care verificam daca ID-ul utilizatrolui este unique
        do { 

            //Generam numarul
            uuid = "";
            for (int c = 0; c < len; c++) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            //Veriricam daca este unique
            nonUnique = false;
            for (User u : this.user) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid;

    }
    
    public String getNewAccountUUID() {
    String uuid;
    Random rng = new Random();
    int len = 10;
    boolean nonUnique;

    // Generăm un UUID și verificăm dacă este unic în listă
    do {
        uuid = "";
        for (int c = 0; c < len; c++) {
            uuid += ((Integer) rng.nextInt(10)).toString();
        }

        nonUnique = false;
        for (Account a : this.accounts) {
            if (uuid.equals(a.getUUID())) {
                nonUnique = true;
                break;
            }
        }
    } while (nonUnique);

    return uuid;
}

    /**
     * firstName - prenume
     * lastName - nume
     * pin - pin-ul utilizatorului
     */
    public User addUser(String firstName, String lastName, String pin) {

        //cream un nou obiect numit User si il adaugam in lista
        User newUser = new User(firstName, lastName, pin, this);
        this.user.add(newUser);

        //cream un cont secundar si il adaugam in contul utilizatorului si al bancii
        Account newAccount = new Account("Numerar", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }

    /**
     * Asociem utilizatorul cu ID-ul si Pinul sau.
     * userId - uuid
     * pin - pin
     * @throws User.InvalidPinException
     */
    public User userLogin(String userID, String pin) throws User.InvalidPinException {

        //cautam in lista de utilizatori
        for (User u: this.user) {

            //verificam daca ID-ul este corect 
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }

        //daca nu gasim utilizatorul sau pin-ul este incorect 
        return null;

    }

    public String getName() {
        return this.name;
    }

    public void addAccount(Account newAccount) {
    }
    
}
