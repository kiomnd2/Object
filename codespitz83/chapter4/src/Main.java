public class Main {
    public static void main(String[] args) {
       Director director = new Director();

       director.addProject("여행사A 프론트 개편", new Client() {
           @Override
           public Program[] run() {
               FrontEnd frontEnd  = new FrontEnd<Client>() {
                   @Override
                   void setData(Client paper) {
                        language = paper.language;
                        library = paper.library;
                   }
               };
               programmer = frontEnd;
               return new Program[]{frontEnd.getProgram(this)};
           }
       });

       director.addProject("xx 은행 리뉴얼", new ServerClient() {
           @Override
           public Program[] run() {
                BackEnd backEnd = new BackEnd<ServerClient>() {
                    @Override
                    void setData(ServerClient paper) {
                        language = paper.backEndLanguage;
                        server = paper.server;
                    }
                };

                FrontEnd frontEnd = new FrontEnd<ServerClient>() {
                    @Override
                    void setData(ServerClient paper) {
                        frontEndLanguage = paper.frontEndLanguage;
                    }
                };
                return new Program[]{ frontEnd.getProgram(this), backEnd.getProgram(this)};
           }
       });
       director.runProject("xx 은행 리뉴얼");
    }
}
