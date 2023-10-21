import java.util.Scanner;

public class ATM {

    public static void main(String[] args) throws User.InvalidPinException {

        //Initializam scanner-ul 
        Scanner sc = new Scanner(System.in);

        //Initializam Banca
        Bank theBank = new Bank("Banca din Bapteni");

        //Adaugam un utilizator care creeaza si un cont secundar
        User aUser = theBank.addUser("Test", "Marian", "1234");

        //Adaugam un cont de cecuri pentru utilizatori din SUA
        Account newAccount = new Account("Checks (Pentru utilizatori din SUA)", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {
            
            //Loop pentru logare pana cand utilizatorul se logheaza
            curUser = mainMenuPrompt(theBank, sc);
            printUserMenu(curUser, sc);
        }
    }

    /**
     * Meniul de loghare 
     * theBank
     * sc - scanner 
     * @throws User.InvalidPinException
     * 
     */
    public static User mainMenuPrompt(Bank theBank, Scanner sc) throws User.InvalidPinException {

        //initializatori
        String userID;
        String pin;
        User authUser;

        //Ii dam un windows utilizatorului unde sa puna ID si Pinul contului
        do {

            System.out.printf("\n\nBine ai venit la %s\n\n", theBank.getName());
            System.out.print("ID : ");
            userID = sc.nextLine();
            System.out.printf("PIN : ");
            pin = sc.nextLine();
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Ai greist PIN-ul/ID-ul " + "Mai incearca o data.");
            } 

        } while(authUser == null); //Loopam functia while pana cand utilizatorul se logheaza

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc) {

        //afisam un sumar al contului
        theUser.printAccountsSummary();

        int choice;

        //meniul utilizatorului
        do {
            System.out.printf("Bine ai venit %s, ce doresti sa faci?\n", theUser.getFirstName());
            System.out.println(" 1) Tranzactii");
            System.out.println(" 2) Retrage");
            System.out.println(" 3) Depoziteaza");
            System.out.println(" 4) Transfera");
            System.out.println(" 5) Iesi");
            System.out.println();
            System.out.print("Alege: ");
            
            choice = sc.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("Alegere invalida. Te rog alege un numar intre 1 si 5.");
            }
        } while (choice < 1 || choice > 5);

        //Procesam inputul userului
        switch (choice) {
            
            case 1:
                showTransHistory(theUser, sc);
                break;
            case 2:
                withdrawFunds(theUser, sc);
                break;
            case 3:
                depositFunds(theUser, sc);
                break;
            case 4:
                transferFunds(theUser, sc);
                break;
            case 5:
                System.exit(0);
        }

        //Reafisam meniul utilizatorului 
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }

    public static void showTransHistory(User theUser, Scanner sc) {

        int theAcct;

        //Aflam istoricul tranzactilor 
        do {

            System.out.printf("Introdu numarul (1-%d) contului\n" + "ce transazctie doresti sa vezi: ", theUser.numAccounts());

            theAcct = sc.nextInt()-1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Cont invalid. Mai incearca o data");
            }

        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        //Afisam istoricul tranzactilor 
        theUser.printAcctTransHistory(theAcct);

    }

    public static void transferFunds(User theUser, Scanner sc) {

        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        do {
            System.out.printf("Introdu numarul (1-%d) contului\n" + "de unde vrei sa transferi bani: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Cont invalid. Mai incearca o data");
            }
        } while(fromAcct <0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        do {
            System.out.printf("Introdu numarul (1-%d) contului\n" + "catre cine doresti sa transferi: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Cont invalid. Mai incearca o data");
            }
        } while(toAcct <0 || toAcct >= theUser.numAccounts());

        do {
            System.out.printf("Introduceti suma pe care vreti sa o transferati(max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if(amount < 0) {
                System.out.println("Suma trebuie sa fie mai mare decat 0.");
            } else if (amount > acctBal) {
                System.out.printf("Suma nu trebuie sa fie mai mare decat\n" + "balanta contului: $%.02f\n", acctBal);
            }
        }while (amount < 0 || amount > acctBal);

        theUser.addAcctTransaction(fromAcct, -amount, String.format("Transfera contului %s", theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, -amount, String.format("Transfera contului %s", theUser.getAcctUUID(fromAcct)));
    }


    public static void withdrawFunds(User theUser, Scanner sc) {

        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        do {
            System.out.printf("Introduceti numarul (1-%d) contului\n" + "de unde vreti sa retrageti bani: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Cont invalid. Mai incearca o data");
            }
        } while(fromAcct <0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        do {
            System.out.printf("Introduceti suma pe care vreti sa o scoateti(max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if(amount < 0) {
                System.out.println("Suma trebuie sa fie mai mare decat 0.");
            } else if (amount > acctBal) {
                System.out.printf("Suma nu trebuie sa fie mai mare decat\n" + "balanta contului: $%.02f\n", acctBal);
            }
        } while (amount < 0 || amount > acctBal);

        sc.nextLine();

        System.out.print("Introduceti o nota: ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(fromAcct, -amount, memo);
    }

    public static void depositFunds(User theUser, Scanner sc) {

        int toAcct;
        double amount;
        double acctBal;
        String memo;

        do {
            System.out.printf("Introduceti numarul (1-%d) contului\n" + "unde doriti sa depozitati: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Cont invalid. Mai incearca o data");
            }
        } while(toAcct <0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);

        do {
            System.out.printf("Introduceti suma pe care doriti sa o transferati(max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if(amount < 0) {
                System.out.println("Cont invalid. Mai incearca o data");
            } 
        } while (amount < 0);

        sc.nextLine();

        System.out.print("Introduceti o nota: ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(toAcct, amount, memo);
    }
    
}
