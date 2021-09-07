package chapter2.src;

import java.time.LocalDateTime;

public class Screening {
    private int seat;
    final int sequence;
    final LocalDateTime whenScreened;

    public Screening(int seat, int sequence, LocalDateTime whenScreened) {
        this.seat = seat;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    boolean hasSeat(int count) {
        return seat >= count;
    }

    void reserveSeat(int count) {
        if (hasSeat(count)) seat -= count;
        else throw new RuntimeException("no seat");
    }
}
