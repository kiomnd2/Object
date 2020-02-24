public class Toggle implements Command {

    @Override
    public void execute(CompositeTask task) {
        task.toggle();
    }

    @Override
    public void undo(CompositeTask task) {
        task.toggle();
    }
}
