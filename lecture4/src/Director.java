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
        deploy(name, projects.get(name).run());

        /*if(!projects.containsKey(name)) throw new RuntimeException("no Project");

        Paper paper = projects.get(name);

        if( paper instanceof  ServerClient)
        {
            ServerClient project = (ServerClient)paper;
            Programmer frontEnd = new FrontEnd<ServerClient>() {
                @Override
                void setData(ServerClient paper) {
                    language = paper.frontEndLanguage;
                }
            };

            Programmer backEnd = new FrontEnd<ServerClient>() {
                @Override
                void setData(ServerClient paper) {
                    server = paper.server;
                    language = paper.backEndLanguage;
                }
            };

            project.setFrontEndProgrammer(frontEnd);
            project.setBackEndProgrammer(backEnd);
            Program client = frontEnd.getProgram( project);
            Program server = backEnd.getProgram( project);
            deploy(name,client,server);

        }
        else if( paper instanceof Client)
        {
            Client project = (Client)paper;
            FrontEnd frontEnd = new FrontEnd<Client>() {
                @Override
                void setData(Client paper) {
                    library = paper.library;
                    language = paper.language;
                }
            };
            project.setProgrammer(frontEnd);
            deploy(name, frontEnd.getProgram(project));
        }*/

    }

    public void deploy(String projectName, Program...programs){}
}
