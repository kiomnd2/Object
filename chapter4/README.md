# Chapter 4. 설계 품질과 트레이드 오프
> 설계는 변경을 위해 존재하고, 어떤 식으로든 비용이 발생한다. 적절한 설계란  적절한 비용으로 응집도가 높고 서로 느슨하게 결합된 요소로 구성된다

* 객체의 상태가 아닌 행동에 초점을 맞춰라



### 데이터중심의 영화 예매 시스템
* 데이터 중심 관점은, 객체 상태에 초점을 맞추고 객체를 설계한다.
* 객체의 상태는 구현에 속하며, 구현은 불안정하기에 변하기 쉽다
    * 데이터에 초점을 맞춘 설계는 변경에 취약하다

    

~~~java
// 데이터가 무엇인가? 
public enum MovieType {
    AMOUNT_DISCOUNT,
    PERCENT_DISCOUNT,
    NONE_DISCOUNT
}
public class Movie {
    private String title;               //제목
    private Duration runningTime;       //러닝타임
    private Money fee;                  //영화요금
    private List<DiscountCondition> discountConditions; // 할인 조건이 여러개로 표현됨
    private MovieType movieType;    // movie 타입에따라 할인 정책의 종류를 결정한다
    private Money discountAmount;
    private double discountPercent;
}
~~~
* 예매 가경을 계산하기 위해서는 Movie 에 설정된 할인 정책이 무엇인지 알아야하고, 어떤 데이터가 필요한지 알아야한다. (discountAmount, discountPercent)
* 데이터 중심 설계는 movieType같이 인스턴스 변수와 인스턴스의 종류에 따라 배타적으로 사용될 수 있는 인스턴스변수 (discountAmount, discountPercent)를 하나의
클래스에 포함시키는 방식을 흔히 볼 수 있다

~~~java
public enum DiscountConditionType {
    SEQUENCE, PERIOD
}
public class DiscountCondition {
    private DiscountConditionType type;
    private int sequence;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
~~~
* Movie 클래스와 마찬가지로 type에 따라 필요한 sequence와 기간 정보를 둘다 가지고 있다.

~~~java
public class ReservationAgency {
    public Reservation reserve(Screening screen, Customer customer, int audienceCount) {
        Movie movie = screen.getMovie();
        
        boolean discountable = false;
        for (DiscountCondition condition : movie.getDiscountCondition()) {
            if (condition.getType() == DiscountConditionType.PERIOD) {
                discountable = screen.getWhenScreened().getDayOfWeek()...... //기간 조건이 일치하는지
            } else {
                discountable = condition.getSequence() == screen.getSequence();
            }
            
            if (discountable) {
                break;
            }
        }
        Movie fee; 
        
        if (discountable) {
            Money discountAmount = Money.ZERO;
            switch(movie.getMovieType()) {
                case AMOUNT_DISCOUNT:
                    discountAmount = movie.getDiscountAmount();
                    break;                
                case PERCENT_DISCOUNT:
                    discountAmount = movie.getFee().times(movie.getDiscountPercent());
                    break;
                case NONE_DISCOUNT:
                    discountAmount = Money.ZERO;
                    break;
            }
            fee = movie.getFee().minus(discountAmount).times(audienceCount);
        } else {
            fee = movie.getFee();
        }
        return new Reservation(customer, screen, fee, audienceCount);
    }   
}
~~~
* 다음 코드는 discountCondition 에 대한 루프를 돌면서, 할인 가능 여부를 판단하고, 적절한 할인 정책에 따라 예매 요금을 계산한다

### 데이터 중심 영화 예매 시스템의 문제점
#### 캡슐화 위반

~~~java
public class Movie {
    private Money fee;
    
    public Money getFee() {
        return fee;
    }
    
    public void setFee(Money fee) {
        this.fee = fee;    
    }
}
~~~
* setFee, getFee 메서드는 fee 라는 이름의 변수를 외부로 노출한다. -> 캡슐화 위반

#### 높은 결합도
* 객체 내부의 구현이 객체 인터페이스에 드라난다는 것은 클라이언트가 구현에 강하게 결합된다는 것을 의미한다
* 객체의 구현을 변경은 곧 코드의 변경을 의미한다

~~~java

public class ReservationAgency {
    public Reservation reserve(Screening screening, Customer customer, int audience) {
        Money fee;
        
        if (discountable) {
            ...
            fee = movie.getFee().minus(discountedAmount).times(audienceCount);
        } else { 
            fee = movie.getFee(); 
        }
    }
}
~~~

* fee의 타입을 변경한다고 가정할 때, getFee 메서드의 반환타입을 수정해야한다. 그리고 바꾸는 로직에 맞춰 reserveAgentcy의 구현도 변경된 타입에 맞게 변경해야한다
* 여러 객체들을 사용하는 제어로직이 특정 객체 안에 집중되기에, 하나의 제어 객체가 다수의 데이터 객체에 강하게 결합된다. 
#### 낮은 응집도
* 서로 다른 이유로 변경되는 코드가 하나의 모듈 안에 공존할 때 모듈의 응집도가 낮다고 말한다.
* 다음과 같은 수정사항이 발생할 경우 ReservationAgency의 코드를 수정해야 한다
    * 할인정책이 추가될 경우
    * 할인 조건이 추가되는 경우
    * 예매 요금을 계산하는 방법이 변경될 경우
* 낮은 응집도는 코드의 변경이 아무 상관없는 로직에도 영향을 끼진다
* 요구사항의 변경은 곧 코드의 변경이다


### 자율적인 객체를 향해

#### 스스로 자신의 데이터를 책임지는 객체
 * 객체 내부에 저장되는 데이터보다, 객체가 협력에 참여하면서 수행할 책임을 정의하는게 중요하다
 * 다시 영화 얘매 시스템으로..
 
~~~java
public class DiscountCondition {
    public DiscountConditionType getType() {
        return type;
    }
    
    public boolean isDiscountable(DayOfWeek dayOfWeek, LocalTime time) {
        if (type != DiscountConditionType.PERIOD) {
            throw new IllegalArgumentException();
        }
        return this.dayOfweek.equals(dayOfWeek) && ....
    }
    
    public boolean isDiscountable(int sequence) {
        if (type != DiscountConditionType.SEQUENCE) {
            throw new IllegalArgumentException();
        }
        return this.sequence = sequence;
    }
}
~~~
* DiscountCondition 클래스에 대해 어떤 오퍼레이션을 수행할 수 있는가?
     * sequence 와 day 두가지 할인 조건에 대한 판단을 스스로 수행해야 한다
        

~~~java
public class Movie {

    public MovieType getMovieType() {
        return movieType ;
    }

    public Money calculateAmountDiscountedFee() {
        if(movieType !=MovieType.AMOUNT_DISCOUNT) {
            throw new IllegalArgumentException();
        }
        return fee.minus(discountAmount);
    }

    public Money calculatePercentDiscountedFee() {
        if(movieType !=MovieType.PERCENT_DISCOUNT) {
            throw new IllegalArgumentException();
        }
        return fee.minus(fee.times(discountPercent));
    }


    public Money calculateNoneDiscountedFee() {
        if(movieType !=MovieType.NONE_DISCOUNT) {
            throw new IllegalArgumentException();
        }
        return fee;
    }

    public boolean isDiscountable(LocalDateTime whenScreened, int sequence) {
        for(DiscountCondition condition : discountConditions) {
            if(condition.getType() == DiscountConditionType.PERIOD) {
                if (condition.isDiscountable(whenScreened.getDayOfWeek(), whenScreened.toLocalTime() )) {
                    return true;
                }
            } else  {
                if( condition.isDiscountable(sequence)) {
                    return true;
                }
            }
        }
        return false;
    }
}
~~~
* Movie 클래스는 어떤 오퍼레이션을 포함하는가?
    * 요금을 계산하는 오퍼레이션, 할인 여부를 판단하는 오퍼레이션
    * DiscountCondition의 목록을 포함하기 때문에, 할인 여부를 판단하는 오퍼레이션 (isDiscountable)
    


~~~java
public class Screening{

    public Money calculateFee(int audienceCount){
        switch (movie.getMovieType()) {
            case AMOUNT_DISCOUNT:
                if(movie.isDiscountable(whenScreened, sequence)) {
                    return movie.calculateAmountDiscountedFee().times(audienceCount);
                }
                break;
            case PERCENT_DISCOUNT:
                if(movie.isDiscountable(whenScreened, sequence)) {
                    return movie.calculatePercentDiscountedFee().times(audienceCount);
                }
                break;
            case NONE_DISCOUNT:
                if(movie.isDiscountable(whenScreened, sequence)) {
                    return movie.calculateNoneDiscountedFee().times(audienceCount);
                }
                break;
        }
        return movie.calculateNoneDiscountedFee().times(audienceCount);
    }

}
~~~
* Screening 클래스는 Movie 가 금액 할인 정책이나 비율 할인 정책을 지원할 경우 Movie의 isDiscountableㅁ 메서드를 호출 해, 할인이 가능한지
여부를 판단한 후 적절한 Movie의 메서드를 호출해서 요금을 계산한다

~~~java
public class ReservationAgency {
  public Reservation reserve(Screening screening, Customer customer, int audienceCount)
    {
        Money fee = screening.calculateFee(audienceCount);
        return new Reservation(customer, screening, fee, audienceCount);
    }
}
~~~

### 문제점
* 위 코드는 본질적으로 데이터 중심 설계에 해당한다

#### 캡슐화 위반
* DiscountCondition 클래스의 isDiscountable(DayOfWeek dayofWeek, LocalTime time) 메서드는, dayOfWeek 와 time 에 해당하는
데이터를 결국 외부로 노출시킨다 ( 캡슐화를 위반한다 ), isDiscountable(int sequence) 마찬가지
* Movie 클래스 역시 calculateAmountDiscountedFee, calculatePercentDiscountedFee, calculateNoneDiscountedFee 를 노출한다.

#### 높은 결합도
* DiscountCondition의 내부 구현이 외부로 노출되었기 때문에 Movie와의 결합도는 높을 수 밖에 없다.
* Movie의 isDiscountable 메서드는 DiscountCondition을 순회하면서 적절한 할인조건을 호춣한다.
* Movie와 DiscountCondition의 결합도가 높기에 생기는 문제
     * DiscountCondition의 조건이 추가될 때마다, is else를 수정해야함
     * DiscountCondition의 만족여부 판단로직이 변경되면, 파라미터를 변경해야함

#### 낮은 응집도 
* DiscountCondition이 할인 여부를 판단하는데 필요한 정보가 변경된다면, 결과적으로 Screening 또한 변경된다
* 하나의 변경을 위해 여러 코드를 수정해야 하는것은, 응집도가 낮다는것을 의미한다.


### 데이터중심 설계의 문제점
* 데이터 중심 설계는 본질적으로 너무 이른 시기에 데이터에 관해 결정하도록 강요한다.
* 데이터 중심 설계에서는 협력이라는 문맥을 고려하지 않고객체를 고립시킨채 오퍼레이션을 할당한다.
