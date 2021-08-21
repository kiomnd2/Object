/**
 * 관람객의 개념을 구현하는 클래스 관람객은 무조건 가방을 들고있따
 */
public class Audience {
    private Bag bag;

    public Audience(Bag bag){
        this.bag = bag;
    }

  /* 가방에 직접 접근 시키는 것을 방지하기위해 buy 메서드를 따로 구현하여 audience가 스스로 해결하게 변경
    public Bag getBag() {
        return bag;
    }*/

    public Long buy(Ticket ticket) {
       /* if(bag.hasInvitation()) {
            bag.setTicket(ticket);
            return 0L;
        }
        else
        {
            bag.minusAmount(ticket.getFee());
            bag.setTicket(ticket);
            return ticket.getFee();
        }
        가방 역시 audience에 의존적이므로 자율적인 존재로 바꾼다
        */
       return bag.hold(ticket);
    }
}
