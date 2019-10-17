/**
 * 티켓 판매원은 기본적으로 타캣 매표소를 알고 있어야 한다. 생성자로 강제한다
 */
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice){
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
/*
        if(audience.getBag().hasInvitation()) {
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().setTicket(ticket);
        }
        else {
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().minusAmount(ticket.getFee()); //극장에서 사용자의 가방에서 돈을 직접 빼간다
            ticketOffice.plusAmount(ticket.getFee()); //판매원도마찬가지
            audience.getBag().setTicket(ticket);
        }
        관람객이 티켓을 구매하는과정을 audience 내부로 숨겼다
*/
//        ticketOffice.plusAmount(audience.buy(ticketOffice.getTicket()));
        ticketOffice.sellTicketTo(audience);
    }
}
