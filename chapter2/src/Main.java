import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        // 상영시간 120 분짜리 영화 10000원 의 입장료, 800원의 할인, 조건은 1번째와 10번째로 입장하거나, 특정 기간에 입장
        Movie avatar = new Movie("아바타", Duration.ofMinutes(120),
                Money.wons(10000),
                new AmountDiscountPolicy(Money.wons(800),
                        new SequenceCondition(1),
                        new SequenceCondition(10),
                        new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10,0),LocalTime.of(11,59))));
    }
}
