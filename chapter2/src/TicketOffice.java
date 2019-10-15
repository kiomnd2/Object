import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TicketOffice 는 판매하거나 교환해줄 티켓의 목록과 판매금액을 인스턴스로 포함하며
 * 사용자에게 티켓을 판매 한다
 */
public class TicketOffice {
    private Long amount;
    private List<Ticket> tickets= new ArrayList<>();

    //티켓 판매소는 기본적으로 판매할 금액과 티켓을 가지고 있따
    public TicketOffice(Long amount, Ticket... ticket) {
        this.amount  -= amount;
        this.tickets.addAll(Arrays.asList(ticket));
    }

    public void sellTicketTo(Audience audience){
        plusAmount(audience.buy(getTicket()));
    }

    public Ticket getTicket() {
        return tickets.remove(0);
    }

    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount){
        this.amount += amount;
    }
}
