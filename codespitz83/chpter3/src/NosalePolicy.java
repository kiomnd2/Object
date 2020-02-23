public class NosalePolicy extends DiscountPolicy{

    public NosalePolicy() {
    }

    @Override
    protected Money calculateFee(Money fee) {
        return fee;
    }
}
