import java.time.Duration;

public class Movie {
    private final String title;
    private final Duration runningTime;
    private final Money fee;
    private final DiscountPolicy discountPolicy;

    public Movie(String title, Duration runningTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    Money calculateFee(Screening screening, int count) {
        return discountPolicy.calculateFee(screening, count,fee);
    }
}
