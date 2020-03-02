package c3;

import java.util.Set;

public class Tex extends Calculator {
    private final double ratio;

    public Tex(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public Money calc(Set<Call> calls, Money result) {
        result = result.plus(result.times(ratio));
        return result;
    }
}
