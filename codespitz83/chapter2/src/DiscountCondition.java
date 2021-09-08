package chapter2.src;

public interface DiscountCondition {
    boolean isSatisfiedBy(Screening screening, int audienceCount);
    Money calculateFee(Money fee);
}
