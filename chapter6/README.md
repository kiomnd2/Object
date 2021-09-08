# Chapter 6. 메시지와 인터페이스
* 애플리케이션을 클래스로 구성되지만 메시지를 통해 정의된다.
* 객체가 수신하는 메시지들이 퍼블릭 인터페이스를 구성한다.

## 협력과 메시지

### 클라이언트 - 서버 모델
* 객체는 협력에 참여하는 동안 클라이언트와 서버의 역할을 동시에 수행하는 것이 일반적이다.
* 협력의 관점에서 객체는 두가지 종류의 메시지 집합으로 구성된다
    * 하나는 객체가 수신하는 메시지의 집합.
    * 또다른 하나는 외부의 객체에게 전송하는 메시지의 집합이다.
    
### 메시지와 메시지 전송
* 한객체가 다른 객체에게 도움을 요청하는 것을 메시지 전송 혹은 메시지 패싱 이라 한다.
* DiscountCondition : condition.isSatisfiedBy(screening)
    * condition : 수신자
    * isSatisfiedBy : 오퍼레이션 명
    * screening : 인자

### 메시지와 메서드
* 메시지를 수신했을 때, 실제로 어떤 코드가 실행되는지는 메시지 수신자의 실테 타입이 무엇인가에 달려있다. (Runtime)
    * 실제로 인터페이스를 실체화한 종류에 따라 달라진다
* 이처럼 메시지를 수신했을 때 실제로 실행되는 함수 또는 프로시저를 메서드 라고 부른다.
* 메시지와 메서드의 구분은 메시지 전송자와 메시지 수신자가 느슨하게 결합될 수 있게 한다.
* 실행시점에 메시지와 메서드를 바인딩하는 메커니즘은 두 객체 사이의 결합도를 낮춤으로써 유연하고 확장 가능한 코드를 작성할 수 있게 한다.

### 퍼블릭 인터페이스와 오퍼레이션
* 객체가 의사소통을 위해 외부에 공개하는 메시지의 집합을 퍼블릭 인터페이스라고 한다.
* 퍼블린 인터페이스에 포함된 메시지를 오퍼레이션 이라고 부른다. 오퍼레이션은 수행 가능한 어떤 행동에 대한 추상화다.


## 인터페이스와 설계 품질
* 인터페이스는 최소한의 인터페이스와 추상적인 인터페이스라는 조건을 만족해야한다.
* 인터페이스 설계의 가장좋은 방법은 책임 주도 설계 방법을 따르는것이다.
    * 메시지를 먼저 선택함으로써 협력과는 무관한 오퍼레이션이 인터페이스에 스며드는 것을 방지한다.
    * 객체가 메시지를 선택하는 것이 아닌 메시지가 객체를 선택하게 한다.

### 퍼블릭 인터페이스 품질에 영향을 미치는 원칙
* 디미터 법칙
* 묻지말고 시켜라
* 의도를 드러내는 인터페이스
* 명령 - 쿼리 분리
* 디미터 법칙

#### 디미터 법칙
~~~java
// 수정전
public class ReservationAgency {
    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        Movie movie = screening.getMovie();
        
        boolean discountable = false;
        for (DiscountCondition condition : movie.getDiscountConditions()) {
            if (condition.getType() == DiscountConditionType.PERIOD) {
                ,,, // 조건로직
            } else {
                ... // 조걸로직
            }

        }
    }    
}
~~~
* 위코드의 가장 큰 단점은 ReservationAgentCy와 Screening 사이의 결합도가 너무 높기 때문에 Screening의 내부 구현을 변경할 때마다 ReservationAgency의
코드 역시 변한다
* 협력하는 객체의 내부 구조에 대한 결합으로 인해 발생하는 설계문제를 해결하기 위해 제안된 법칙이 디미터 법칙이다.
    * 내부 구조에 강하게 결합되지 않도록 협력 경로를 제한하라.
    * 디미터 법칙을 따르기 위해서는 클래스가 특정한 조건을 만족하는 대상에게만 메시지를 전달하도록 프로그래밍 해야한다.
        * this 객체
        * 메서드의 매개변수
        * this의 속성
        * this의 속성인 컬렉션의 요소
        * 메서드 내에서 생성된 지역 객체

~~~java
// 수정후
public class ReservationAgency{
    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        Money fee = screening.calculateFee(audienceCount);
        return new Reservation(customer, screening, fee, audienceCount);
    }
}
~~~
* ReservationAgency는 메서드의 인자로 전달된  Screening 인스턴스에게만 메세지를 전송한다.
* 불필요한 어떤 것도 다른 객체에게 보여주지 않는다. 
* 클라이언트와 서버간 낮은 결합도를 유지한다

#### 묻지 말고 시켜라
* 훌륭한 메시지는 객체에 상태에 관해 묻지말고, 원하는것을 시켜야 한다.
* 객체의 정보를 이용하는 행동을 객체의 외부가 아닌 내부에 위치시킨다.

#### 의도를 드러내는 인터페이스


* 메서드 명명규칙 첫번째는 메서드가 작업을 어떻게 수행하는지를 나타내도록 이름짓는다
~~~java
public class PeriodCondition {
    public boolean isSatisfiedByPeriod(Screening screening) {}

}

public class SequenceCondition {
    public boolean isSatisfiedBySequence(Screening screening) {}
}
~~~
* 첫번째 규칙을 적용한 위 코드의 단점
    * 메서드에 대해 제대로 커뮤니케이션 하지 못한다. 메서드의 이름이 다르기 때문에 서로 동일한 작업을 수행한다고 생각하기 힘들다
    * 캡슐화를 위반한다. 위 메서드는 클라이언트로 하여금 협력하는 객체의 종류를 알게한다. 위 메서드를 사용하려면 참조하는 객체를 변경하는것 뿐 아니라
    호출하는 메서드를 변경해야한다.
* 두번째 방법은 '어떻게' 가 아니라 '무엇을' 하는지를 드러내는 것이다. 객체가 협력안해서 수행해야 하는 책임에 대해 고민해야 한다.
    * 결과적으로 협력하는 클라이언트의 의도에 부합하도록 메서드의 이름을 짓는다

~~~java
public class PeriodCondition {
    public boolean isSatisfiedBy(Screening screening) {}

}

public class SequenceCondition {
    public boolean isSatisfiedBy(Screening screening) {}
}
~~~
* 두번째 방법을 고려하여 이름을 수정한 코드.
    * 동일한 목적을 수행한다는 것은 알았다.
    * 자바같은 정적 타이핑 언어에서 두 메서드 이름이 같다고 동일한 일처리를 할 수 있는 것이 아니다.
    * 동일한 타입 계층으로 묶어야 한다.
    

~~~java
public interface DiscountCondition {
     boolean isSatisfiedBy(Screening screening);

}

public class PeriodCondition implements DiscountCondition {
    public boolean isSatisfiedBy(Screening screening) {}

}

public class SequenceCondition implements  DiscountCondition{
    public boolean isSatisfiedBy(Screening screening) {}
}
~~~
* 클라이언트 입장에서 두 메서드를 동일한 방식으로 사용할 수 있게 되었다

