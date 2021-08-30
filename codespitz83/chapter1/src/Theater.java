package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Theater {

    final private List<TicketOffice> ticketOffices = new ArrayList<>();
    final private Long fee;

    /**
     * 극장만이 가격을 확정시킬 수 있다
     * @param fee
     */
    public Theater(Long fee) {
        this.fee = fee;
    }

    public Long getFee() {
        return fee;
    }

    /**
     * 다수의 티켓오피스 수용
     * @param ticketOffices
     */
    public void setTicketOffices(TicketOffice ...ticketOffices) {
        this.ticketOffices.addAll(Arrays.asList(ticketOffices));
    }

    /**
     * 극장은 티켓오피스에 티켓을 발급
     * @param ticketOffice
     * @param num
     */
    public void setTicket(TicketOffice ticketOffice, Long num) {
        if (!ticketOffices.contains(ticketOffice)) return;
        while (num-- > 0) {
            ticketOffice.addTicket(new Ticket(this)); // 어떤 극장이 발급했는가,
        }
    }

    /**
     * 극장이 관객에게 초대장을 전달해줌
     * @param audience
     */
    public void setInvitation(Audience audience) {
        audience.setInvitation(new Invitation(this));
    }

    /**
     * 관객에게 표를 받고, 티켓이 정상인지 판단
     * @param audience
     * @return
     */
    public boolean enter(Audience audience) {
        Ticket ticket = audience.getTicket();
        return ticket.isValid(this); // 표 스스로 정상인지 판단
    }

}
