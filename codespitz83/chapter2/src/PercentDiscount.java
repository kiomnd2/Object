package chapter2.src;

abstract public class PercentDiscount implements DiscountPolicy.PERCENT, DiscountCondition{
    private final Double percent;

    public PercentDiscount(Double percent) {
        this.percent = percent;
    }

    @Override
    public Money calculateFee(Money fee) {
        return fee.minus(fee.multi(percent));
    }
}
