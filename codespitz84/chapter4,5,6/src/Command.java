public interface Command {
    //가장 대표적인 메서드
    void execute(CompositeTask task);
    void undo(CompositeTask task);
}
