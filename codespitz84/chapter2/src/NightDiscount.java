import java.time.Duration;

public class NightDiscount implements Calculator {
    private final Money dayPrice;
    private final Money nightPrice;
    private final Duration second;

    public NightDiscount(Money dayPrice, Money nightPrice, Duration second) {
        this.dayPrice = dayPrice;
        this.nightPrice = nightPrice;
        this.second = second;
    }

    @Override
    public Money calc(Set<Call> calls, Money result) {
        for( Call call : calls) {
            Money price = call.getForm().getHour() >= 22 ? nightPrice : dayPrice;
            result = result.plus(price.times((call.getDuraion().getSecond() / second.getSeconds() )));
        }
        return result ;
    }
}
