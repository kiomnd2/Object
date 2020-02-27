public class AmountDiscount implements Calculator{
    private final Calculator next;
    private final Money amount;

    public AmountDiscount(Calculator next, Money amount) {
        this.next = next;
        this.amount = amount;
    }

    @Override
    public Money calc(Set<Call> calls, Money result) {
        return result.minus(amount);
    }
}
