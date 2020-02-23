import java.util.HashSet;

public abstract class DiscountPolicy {
    private Set<DiscountCondition> conditions = new HashSet<>();

    public void addCondition(DiscountCondition condition) {
        condition.add(condition);
    }

    public void copyCondition(DiscountPolicy policy)
    {
        policy.conditions.addAll(conditions);
    }

    public Money calculateFee(Screening screening, int count, Money fee)
    {
        for(DiscountCondition condition: conditions)
        {
            if(condition.isSatisfiedBy(screening, count)) return calculateFee(fee);
        }
        return fee;
    }

    protected abstract Money calculateFee(Money fee);
}
