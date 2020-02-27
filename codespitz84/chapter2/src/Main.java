import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        Plan plan = new Plan();
        plan.setCalculator(new PricePerTime(Money.of(19), Duration.ofSeconds(60))
                .setNext(new AmountDiscount(Money.of(10000)))
        );

    }
}
