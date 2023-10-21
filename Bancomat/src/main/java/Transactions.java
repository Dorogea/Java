import java.util.Date;

public class Transactions {

    //Suma tranzactiei
    private double amount;

    //Data tranzactiei
    private Date timestamp;

    //Notificarea primita legata de tranzactia facuta
    private String memo;

    //Construcotr
    public Transactions(double amount, Account inAccount) {
        this.amount = amount;
        this.timestamp = new Date();
        this.memo = "";
    }

    /**
     * Cream o noua tranzactie
     * amount - suma tranzactiei
     * inAccount - contul tranzactiei
     */
    public Transactions(double amount, String memo, Account inAccount) {
        this(amount, inAccount);
        this.memo = memo;

    }

    //Suma tranzactiei
    public double getAmount() {
        return this.amount;
    }


    /**
     * 
     * Afisam un rezumat al contului
     */
    public String getSummaryLine() {

        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timestamp.toString(), -this.amount, this.memo);
        }
    }
}
