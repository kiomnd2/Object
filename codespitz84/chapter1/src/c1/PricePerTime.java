package c1;

import java.time.Duration;

// 본인에 대한 상태만 관리..
//상속이 전혀 없다
public class PricePerTime extends Plan {
    private final Money price;
    private final Duration second;

    public PricePerTime(Money price, Duration second) {
        this.price = price;
        this.second = second;
    }

    // Override protect
    @Override
    protected Money calcCallFee(Call call) {
        return price.times((call.getDuration().getSeconds() / second.getSeconds())); //
    }
}
