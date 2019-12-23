public class AmountPolicy extends DiscountPolicy {
    private final Money fee;

    public AmountPolicy(Money money) {
        this.money = money;
    }

    @Override
    protected Money calculateFee(Money fee) {
        return fee.minus(amount);
    }
}
