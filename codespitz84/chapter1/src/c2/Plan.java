package c2;

import java.util.HashSet;
import java.util.Set;

//합성으로 전환
// 소유하고 있는 전략객체가 해결
//이 클래스가 확장될 가능성이 있는지 파악하고 final을 검
public class Plan {
    private Calculator calc;
    private Set<Call> calls = new HashSet<>();
    public final void addCall(Call call) {
        calls.add(call);
    }

    // 전략객체로 Calculator을 클라이언트에서 정의하고 처리
    public void setCalc(Calculator calc) {
        this.calc = calc;
    }
    public final Money calculateFee() {
        Money result = Money.ZERO;
        for(Call call: calls ) result = result.plus(calc.calcCallFee(call));
        return result;
    }
}
