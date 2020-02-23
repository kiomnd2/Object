import java.util.HashMap;
import java.util.Map;

public class Director {
    private Map<String, Paper> projects = new HashMap<>();
    private Language language;
    private Library library;
    private Server server;

    public void addProject(String name, Paper paper){ projects.put(name, paper);}
    public void runProject(String name)
    {
        if(!projects.containsKey(name)) throw new RuntimeException("no Project");
        Paper paper = projects.get(name);
        if( paper instanceof  ServerClient)
        {
            ServerClient project = (ServerClient) paper;
            Programmer frontEnd = new FrontEnd() {
                @Override
                void setData(ServerClient paper) {
                    language = paper.frontEndLanguage;
                }
            }

        }
        else if( paper instanceof Client)
        {
            Client project = (Client)paper;
            Programmer frontEnd = new FrontEnd();
            project.setProgrammer(frontEnd);
            deploy(name, frontEnd.makeProgram(project));
        }
    }

    public void deploy(String projectName, Program...programs){}
}
