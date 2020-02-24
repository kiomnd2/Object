import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CommandTask root = new CommandTask("root", LocalDateTime.now());
        root.addTask("sub1" , LocalDateTime.now());
        root.addTask("sub2" , LocalDateTime.now());

        Renderer renderer1 = new Renderer(()-> new JsonVisitor());
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));

        System.out.println("=======================================");
        root.undo();

        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));

        System.out.println("=======================================");
        root.undo();

        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));

        System.out.println("=======================================");
        root.redo();

        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));


/*
        TaskReport report  = root.getReport(CompositeSortType.TITLE_ASC);
        List<TaskReport> list =  report.getList();
        CompositeTask sub1 = list.get(0).getTask();
        CompositeTask sub2 = list.get(1).getTask();
        sub1.addTask("sub1_1", LocalDateTime.now());
        sub1.addTask("sub1_2", LocalDateTime.now());
        sub2.addTask("sub2_1", LocalDateTime.now());
        sub2.addTask("sub2_2", LocalDateTime.now());*/
//
//        Renderer renderer1 = new Renderer(()-> new ConsoleVisitor());
//        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));
/*

        Renderer renderer2 = new Renderer(()-> new JsonVisitor());
        renderer2.render(root.getReport(CompositeSortType.TITLE_ASC));
*/


    }
}
