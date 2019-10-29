import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DefaultDiscountPolicy implements DiscountPolicy {
    private List<DiscountCondition> conditions = new ArrayList<DiscountCondition>();

    public DefaultDiscountPolicy(DiscountCondition ...conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    public Money calculateDiscountAmount(Screening screening)
    {
        for ( DiscountCondition each : conditions)
        {
            if (each.isSatisfiedBy(screening))
            {
                return getDiscountAmount(screening);
            }
        }
        return Money.ZERO;
    }

    // 실제로 자식클래스에게 책임을 위임함
    abstract protected Money getDiscountAmount(Screening screening);

}
