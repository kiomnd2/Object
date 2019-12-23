public class PercentPolicy extends DiscountPolicy{
    private final double percent;

    public PercentPolicy(Money fee) {
        this.fee = fee;
    }

    @Override
    protected Money calculateFee(Money fee) {
        return fee.minus(fee.multi(percent));
    }
}
