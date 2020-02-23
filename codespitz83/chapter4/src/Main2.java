public class Main2 {
    public static void main(String[] args) {
        Library library;
        final Language[] language = new Language[1];

        Director director = new Director();
        director.addProject("여행사A 프론트 개편", new Client() {
            @Override
            public Program[] run() {
                FrontEnd frontEnd = new FrontEnd<Client>() {
                    @Override
                    void setData(Client paper) {
                        library = paper.library;
                        language = paper.language;
                    }
                };

                programmer = frontEnd;
                return new Program[]{frontEnd.getProgram(this)};

            }
        });

        director.runProject("여행사A 프론트 개편");


        director.addProject("xx은행 리뉴얼", new ServerClient() {
            @Override
            public Program[] run() {
                Programmer frontEnd = new FrontEnd<ServerClient>() {
                    @Override
                    void setData(ServerClient paper) {
                        language[0] = paper.frontEndLanguage;
                    }
                };


                Programmer backEnd = new BackEnd<ServerClient>() {
                    @Override
                    void setData(ServerClient paper) {
                        language = paper.backEndLanguage;
                        server = paper.server;
                    }
                };

                return new Program[]{frontEnd.getProgram(this), backEnd.getProgram(this)};
            }
        });

    }
}
