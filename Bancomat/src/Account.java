import java.util.ArrayList;

public class Account {

    //Numele contului
    private String name;

    //ID-ul utilizatorului
    private String uuid;

    //Lista tranzactiilor 
    private ArrayList<Transactions> transactions;

    /**
     * Cream un cont nou
     * name - Numele contului
     * holder - Detinatorul contului
     * theBank - Banca la care este contul
     */
    public Account(String name, User holder, Bank theBank) {

        //Adaugam numele contului si persoana care detine contul
        this.name = name;
        //Pin pentru un cont nou
        this.uuid = theBank.getNewAccountUUID();
        //Tranzactii
        this.transactions = new ArrayList<Transactions>();
     }

   //ID-ul contului
     public String getUUID() {
        return this.uuid;
     }

     //Facem un rezumat al contului
     public String getSummaryLine() {

         //lu am balanta contului
         double balance = this.getBalance();
         String balanceStr;

         //formatam textul in funtie de balanta 
         if (balance >= 0) {
            balanceStr = String.format("$%.02f", balance);
         } else {
            balanceStr = String.format("($%.02f)", -balance);
         }
         return String.format("%s : %s : %s", this.uuid, balanceStr, this.name);
     }

     //Aflam balanta contului prin adaugarea sumelor tranzactiilor 
     public double getBalance() {
      
         double balance = 0;
         for (Transactions t: this.transactions) {
            balance += t.getAmount();
         }
         return balance;
     }

     // Afisam istoria tranzactilor contului
     public void printTransHistory() {

      System.out.printf("\nTransactions history for account %s\n", this.uuid);
      for (int t = this.transactions.size()-1; t >= 0; t--) {
         System.out.println(this.transactions.get(t).getSummaryLine());
      }
      System.out.println();
     }

     //Creeam 
     public void addTransaction(double amount, String memo) {

         Transactions newTrans = new Transactions(amount, memo, null);
         this.transactions.add(newTrans);
     }
}
