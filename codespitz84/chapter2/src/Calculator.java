public abstract class Calculator {
    private Calculator next;
    public final Calculator setNext(Calculator next) {
        this.next = next;
        return this;
    }

    public final Money calcCallFee(Set<Call> calls, Money result) {
        result = calc(calls, result) ;
        return next == null ? result : next.calcCallFee(calls, result);
    }

    abstract protected Money calc(Set<Call> calls, Money result);
}
