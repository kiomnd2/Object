package src;

public class Audience {

    private Ticket ticket = Ticket.EMPTY;

    private Invitation invitation = Invitation.EMPTY;

    private Long amount;

    public Audience(Long amount) {
        this.amount = amount;
    }

    public void buyTicket(TicketSeller seller) {
        ticket = seller.getTicket(this);
    }

    public boolean hasAmount(Long ticketPrice) {
        return this.amount >= ticketPrice;
    }

    public boolean minusAmount(Long amount) {
        if (amount > this.amount) return false;
        this.amount -= amount;
        return true;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void removeInvitation() {
        invitation = Invitation.EMPTY;
    }

}
