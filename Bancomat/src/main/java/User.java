import java.util.ArrayList;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    //Preumele utilizatorului
    private String firstName;

    //Id-ul de autentificare la utilizatorului
    private String uuid;

    //Stocarea PIN-ului in format MD5
    private byte[] pinHash;

    //Lista de conturi
    private ArrayList<Account> accounts;

    /**Cream un utilizator nou
     * firstName - Nume
     * lastName - Prenume
     * pin - pin-ul contului
     * Banca - banca la care utilizatorul este client
     */
    public User(String firstName, String lastName, String pin, Bank theBank) {

        //Numele utilizatorului
        this.firstName = firstName;
        //Pin-ul utilizatorului
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: Unable to create user. Please try again later.");
        }

        //Generam un ID nou pentru utilizator
        this.uuid = theBank.getNewUserUUID();

        //Cream o lista goala de conturi
        this.accounts = new ArrayList<Account>();

        //General un log
        System.out.printf("Utilizator nou %s, %s cu ID-ul %s a fost creat.\n", lastName, firstName, this.uuid);

    }

    public class InvalidPinException extends Exception {
        public InvalidPinException(String message) {
            super(message);
        }
    }

    //Adaugam in cont pentru utilizator
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public String getUUID() {
        return this.uuid;
    }

    /**
     * Verificam daca pinul utilizatorului se potriveste
     * aPin verificam pin-ul
     */
    public boolean validatePin(String aPin) throws InvalidPinException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidPinException("Error: Unable to validate pin. Please try again later.");
        }
    }

    public static int promptForAccountNumber(Scanner sc, int numAccounts) {
    int accountNumber;
    do {
        System.out.printf("Enter the number (1-%d) of the account: ", numAccounts);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid account number.");
            sc.next();
        }
        accountNumber = sc.nextInt() - 1;
    } while (accountNumber < 0 || accountNumber >= numAccounts);
    return accountNumber;
}

    public static double promptForAmount(Scanner sc) {
        double amount;
        do {
            System.out.print("Enter the amount: $");
            while (!sc.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid amount.");
                sc.next();
            }
            amount = sc.nextDouble();
        } while (amount <= 0);
        return amount;
}

    public String getFirstName() {
        return this.firstName;
    }


    //Afisam sumarul contului
    public void printAccountsSummary() {

        System.out.printf("\n\n%s acesta este sumarul contului tau:\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    //Numarul conturilor utilizatorului
    public int numAccounts() {
        return this.accounts.size();
    } 

    //Afisam istoricul tranzactilor pentru un anumit utilizator
    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    //Aflam balanta unui cont specific
    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    //Aflam UUID-ul unui cont specific
    public String getAcctUUID(int acctIdx) {
        return this.accounts.get(acctIdx).getUUID(); 
    }

    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }

}
