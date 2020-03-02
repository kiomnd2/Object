package c3;

import java.util.Set;

public class AmountDiscount extends Calculator{
    private final Money amount;

    public AmountDiscount( Money amount) {
        this.amount = amount;
    }

    @Override
    public Money calc(Set<Call> calls, Money result) {
        result = result.minus(amount);
        return result;
    }
}
