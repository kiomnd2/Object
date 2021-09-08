package chapter2.src;

public class SequenceAmountDiscount extends AmountDiscount{
    private final int sequence;

    public SequenceAmountDiscount(Money money, int sequence) {
        super(money);
        this.sequence = sequence;
    }

    @Override
    public boolean isSatisfiedBy(Screening screening, int audienceCount) {
        return screening.sequence == sequence;
    }
}
