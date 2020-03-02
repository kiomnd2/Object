package c1;

import java.util.HashSet;
import java.util.Set;

abstract class Plan {
    private Set<Call> calls = new HashSet<>();
    public final void addCall(Call call) {
        calls.add(call);
    }
    public final Money calculateFee() { // fianl 을 통해 상속을 막음
        Money result = Money.ZERO;
        for(Call call: calls) result = result.plus(calcCallFee(call));
        return result;
    }
    abstract protected Money calcCallFee(Call call); //하나의 콜에 대한 계산만..
}
