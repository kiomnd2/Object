package chapter2.src;

public interface DiscountPolicy {
    interface AMOUNT extends DiscountPolicy {}
    interface PERCENT extends DiscountPolicy {}
    interface NONE extends DiscountPolicy {}
}
