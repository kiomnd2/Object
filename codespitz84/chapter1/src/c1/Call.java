package c1;

import java.time.Duration;
import java.time.LocalDateTime;

public class Call {
    private final Duration duration;

    public Call(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getFrom() {
        return null;
    }
}
