package src;

public class Ticket {

    final static Ticket EMPTY = new Ticket(null);
    final private Theater theater;

    private boolean isEntered = false; // 자신의 상태는 자신이 관리
    public Ticket(Theater theater) {
        this.theater = theater;
    }

    /**
     * 티켓을 검증
     * @param theater
     * @return
     */
    public boolean isValid(Theater theater) {
        if (isEntered || theater != this.theater || this == EMPTY) {
            return false;
        } else {
            isEntered = true;
            return true;
        }
    }

    public Long getFee() {
        return theater.getFee();
    }

}

class Invitation {

    final static Invitation EMPTY = new Invitation(null);

    final private Theater theater;

    public Invitation(Theater theater) {
        this.theater = theater;
    }
}
