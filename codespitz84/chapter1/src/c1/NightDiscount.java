package c1;

import java.time.Duration;

public class NightDiscount extends Plan {
    private final Money dayPrice;
    private final Money nightPrice;
    private final Duration second;

    public NightDiscount(Money dayPrice, Money nightPrice, Duration second) {
        this.dayPrice = dayPrice;
        this.nightPrice = nightPrice;
        this.second = second;
    }

    @Override
    protected Money calcCallFee(Call call) { // 하나의 콜에대해서 자기 자신으 ㅣ상태와 call의 상태만 이용해서 값을 계산
        Money price = call.getFrom().getHour() >= 22 ? nightPrice : dayPrice;
        return price.times((call.getDuration().getSeconds() / second.getSeconds()));
    }
}
