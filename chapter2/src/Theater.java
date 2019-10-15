/**
 * 지금까지 준비한 클래스들을 조합하는 극자 클래스
 */
public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    public void enter(Audience audience) {
        /*
            이와 같은 경우, 판매원이나 관람객의 객체가 변하면, Theater도 같이 변해야한다.
            해결법은 판매원관 관람객의 자율성을 높이면 된다.
        if( audience.getBag().hasInvitation()) {



                 Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
        }
        else {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().minusAmount(ticket.getFee()); //극장에서 사용자의 가방에서 돈을 직접 빼간다
            ticketSeller.getTicketOffice().plusAmount(ticket.getFee()); //판매원도마찬가지
            audience.getBag().setTicket(ticket);
        }*/

        //위의 티켓 판매과정을 모두 ticketSeller로 숨겼다
        ticketSeller.sellTo(audience);

    }
}
