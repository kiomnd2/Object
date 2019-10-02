import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompositeTask {


    private String title;
    private LocalDateTime date;
    private Boolean isComplete = false;
    final private Set<CompositeTask> list = new HashSet<>();

    public CompositeTask(String title, LocalDateTime date) {
        setTitle(title);
        setDate(date);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void toggle() {
        isComplete = !isComplete;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public void addTask(String title, LocalDateTime date) {
        list.add(new CompositeTask(title , date));
    }

    public void removeTask(CompositeTask task) {
        list.remove(task);
    }

    public TaskReport getReport(CompositeSortType type) {
        TaskReport report = new TaskReport(this);
        for( CompositeTask t : list) {
            report.add(t.getReport(type));
        }
        return report;
    }

}
