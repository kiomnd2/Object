/**
 * 관람객이 가질수 있는것은 현금과 , 초대장과 ,티켓
 * 관람객의 소지품을 구현할 bag 클래스
 */
public class Bag {
    private Long amount;
    private Invitation invitation;
    private Ticket ticket;


    public Bag(long amount) {
        this (null,amount);
    }


    public Bag(Invitation invitation, long amount) {
        this.invitation= invitation;
        this.amount = amount;
    }


    public Long hold(Ticket ticket) {
        if(hasInvitation()){
            setTicket(ticket);
            return 0L;
        }
        else {
            setTicket(ticket);
            minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }

    //가방안에 초대장만 잇거나 , 돈만 잇거나 둘중 하나는 있도록 생성자로 강제




    //초대장 여부
    public boolean hasInvitation() {
        return invitation !=null;
    }

    //티켓여부
    public boolean hasTicket() {
        return ticket !=null;
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    private void minusAmount (Long amount) {
        this.amount -= amount;
    }

    public void plusAmuount(Long amount) {
        this.amount += amount;
    }
}
