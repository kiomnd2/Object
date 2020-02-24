import java.time.LocalDateTime;

public class Date implements Command {

    private final LocalDateTime date;
    private LocalDateTime oldDate;

    public Date(LocalDateTime date) {
        this.date = date ;
    }

    @Override
    public void execute(CompositeTask task) {
        oldDate = task.getDate();
        task.setDate(date);
    }

    @Override
    public void undo(CompositeTask task) {
        task.setDate(oldDate);
    }
}
