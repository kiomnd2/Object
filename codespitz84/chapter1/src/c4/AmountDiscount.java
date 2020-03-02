package c4;

import java.util.Set;

public class AmountDiscount implements Calc {
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
