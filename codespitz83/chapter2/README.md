# 코드스피츠 2회차

## Type
* Role : 형을 통해 역할을 묘사함
* Responsibility : 형을 통해 로직을 표현함
* Message : 형을 통해 메세지를 공유함
* Protocol : 객체간 계약을 형을 통해 공유함


## supported types
* static : 단 한 개의 인스턴스가 존재
    * 언어적인 차원의 static 의 작동이 스레드 세이프가 아님을 주의
* enum : 제한된 수의 인스턴스가 존재
    * 제네릭 사용 불가
* class : 무제한의 인스턴스가 존재

## Condition
1. 조건 분기는 절대 제거할 수 없다.
2. 조건 분기에 대한 전략은 두 가지 뿐이다.
    - 내부에서 응집성있게 모아두는 방식
        - 장점 : 모든 경우의 수를 한곳에서 파악할 수 있다.
        - 단점 : 분기가 늘어날 때마다 코드가 변경된다.
    - 외부 분기를 위임하고 경우의 수 만큼 처리기를 만드는 방식
        - 장점 : 분기가 늘어갈 때마다 처리기만 추가하면 된다.
        - 단점 : 모던 경우의 수를 파악할 수 없다.
        
        
## Responsibility Driven

### value = responsibility
* 시스템의 존재 가치는 사용자에게 제공되는 기능
* 사용자사 사용할 기능 시스템의 책임
* 시스템 차원의 책임을 더 작은 단위의 책임으로 분할
* 해당 책임을 추상화하여 역할을 정의함
* 역할에 따라 협력이 정의됨


## Theater With Reservation

### Client
~~~java
Theater theater = Teater(Money.of(100.0));
Movie movie = new Movie<AmountDiscount>(
    "spiderman",
    Duration.ofMinutes(120L),
    Money.of(5000.0),
    new SequenceAmountDiscount(Money.of(1000.0), 1)
);
theater.addMovie(movie);
for (int day=7 ; day <32 ; day++ ) {
    for (int hour =10, seq =1 ; hour <24 ; hour+=3 ; seq++) {
        theater.addScreening(   
            movie,
            new Screening(
                seq,
                LocalDateTime.of(2019, 7, day, hour, 00, 00),
                100 // 좌석수
            )
        );
    }
}

TicketOffice ticketOffice = new TicketOffice(Money.of(0.0));
theater.contractTicketOffice(ticketOffice, 10.0);
TicketSeller seller = new TicketSeller();
seller.setTicketOffice(ticketOffice);

for (Screening screening: theater.getScreening(movie)) {
    customer.reserve(seller, theater, movie, screening, 2); // 예약
    boolean isOk = theater.enter(customer,2);
    System.out.println(isOk);
    break;
}
~~~
* Movie 가 단 한개의 할인 정책을 가질수 있기 때문에, 제네릭으로 선언함
* AmountDiscount 중, Sequence 할인 모델을 쓴다.
* 모든 상영은 다 100석을 가지고 있다.
* 티켓오피스는 극장과 계약을 하며 10 의 수수료를 가진다
* 핵심은 클라이언트 코드를 먼저 짜보는 것
* 값을 되도록 쓰지말고 객체를 써라 (확장에 유리)
