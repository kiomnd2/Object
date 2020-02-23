public class ServerClient implements Paper {
    Server server = new Server("test");
    Language backEndLanguage = new Language("java");
    Language frontEndLanguage = new Language("kotlinJS");
    private Programmer backEndProgrammer;
    private Programmer frontEndProgrammer;
    public void setBackEndProgrammer(Programmer backEndProgrammer) {
        this.backEndProgrammer = backEndProgrammer;
    }

    public void setFrontEndProgrammer(Programmer frontEndProgrammer) {
        this.frontEndProgrammer = frontEndProgrammer;
    }

    @Override
    public void setData(Programmer programmer) {
        if(programmer instanceof FrontEnd)
        {
            FrontEnd frontEnd = (FrontEnd) programmer;
            frontEnd.setLanguage(frontEndLanguage);
        }
        else if(programmer instanceof BackEnd)
        {
            BackEnd backEnd = (BackEnd) programmer;
            backEnd.setLanguage(backEndLanguage);
            backEnd.setServer(server);
        }
    }
}

