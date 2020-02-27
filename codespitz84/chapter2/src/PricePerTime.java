import java.time.Duration;

public class PricePerTime implements Calculator{
    private final Money price;
    private final Duration second;
    public PricePerTime( Money price, Duration second) {
        this.price = price;
        this.second = second;
    }

    @Override
    protected Money calc(Set<Call> calls, Money result) {
        for( Call call : calls) {
            result = result.plus(price.times((call.getDuration().getSecond() / second.getSeconds() )));
        }
    }
}
