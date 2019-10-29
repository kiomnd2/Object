public class Reservation {
    private Customer customer;
    private Screening Screening;
    private Money fee;
    private int audienceCount;

    public Reservation(Customer customer, Screening screening, Money fee, int audienceCount) {
        this.customer = customer;
        this.Screening = screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }




}
