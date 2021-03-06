package c4;

import java.util.HashSet;
import java.util.Set;

public class Calculator {
    private Set<Calc> calcs = new HashSet<>();
    public Calculator(Calc calc) {
        calcs.add(calc);
    }
    public final Calculator setNext(Calc next) {
        calcs.add(next);
        return this;
    }
    public final Money calcCallFee(Set<Call> calls, Money result) {
        for(Calc calc : calcs ) result = calc.calc(calls, result);
        return result;
    }
}
