package c3;

import java.util.Set;

public abstract class Calculator {
    private Calculator next;
    public final Calculator setNext(Calculator next) {
        this.next = next;
        return this;
    }
    public final Money calcCallFee(Set<Call> calls, Money result) { //외부노출,.. 훅을 통해 계산
        result = calc(calls, result);
        return next ==null ? result : next.calcCallFee(calls, result);
    }
    abstract protected Money calc(Set<Call> calls, Money result);
}
