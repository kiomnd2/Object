# 코드스피츠 83 오브젝트

## LSP (리스코프 치환원칙)
* 코드에서 다운캐스팅 금지원칙
* 리스코프치환원칙이 확정으로 일어났을때( 다운캐스팅 을 해야할 때) 어떻게 해결해야하나?
    * 제네릭
    

## CODE

* interface 정의
```java
public interface Paper{}
```

```java
public interface Programmer {
    Program makeProgram(Paper paper);
}
```

#### client 

* client
```java
public class Client implements Paper {
    Library library = new Library("vueJS");
    Language language = new Language("kotlinJS");
    Programmer programmer;
    public void setProgrammer(Programmer programmer){
        this.programmer = programmer;
    }    
}
```

* ServerClient
```java
public class ServerClient implements Paper { 
    Server server = new Server("test");
    Language backEndLanguage = new Language("java");
    Language frontEndLanguage = new Language("kotlinjs");
    
    private Programmer backEndProgrammer;
    private Programmer frontEndProgrammer;
    
    public void setBackEndLanguage(Programmer programmer) {
        backEndProgrammer = programmer;
    }

    public void setFrontEndLanguage(Programmer programmer) {
        frontEndProgrammer = programmer;
    }
}
```


#### Programmer

* Frontend
```java
public class FrontEnd implements Programmer{
    private Language language;
    private Library library;
    
    public Program makeProgram(Paper paper) {
        if(paper instanceof Client) {
            Client pb = (Client) pb;
            language = pb.language;
            library = pb.library;            
        }
        return makeFrontEndProgram();
    }
    private Program makeFrontEndProgram() { return new Program; }
}
```
> *  makeProgram 부분에서 Paper는 마커 인터페이스이기 때문에 Paper로부터 
> 정보를 얻고 싶다면 Client로 다운캐스팅 해야한다.
> * BackEnd 클래스도 사정은 마찬가지
> * if는 런타임으로 문제를 미룸 -> 정적타임의 안정성을 잃음
> * if는 제거할 수 없지만 위 코드에선 제거 가능
>

#### Director
```java
public class Director {
    private Map<String, Paper> projects = new HashMap<>();
    public void addProject(String name, Paper paper){ projects.put(name, paper);}
    public void runProject(String name)
    {
        if(!projects.containsKey(name)) throw new RuntimeException("no Project");
        deploy(name, projects.get(name).run());

        if(!projects.containsKey(name)) throw new RuntimeException("no Project");

        Paper paper = projects.get(name);

        if( paper instanceof  ServerClient)
        {
            ServerClient project = (ServerClient)paper; //다운캐스팅
            Programmer frontEnd = new FrontEnd();
            Programmer backEnd = new BackEnd();
            project.setFrontEndProgrammer(frontEnd);
            project.setBackEndProgrammer(backEnd);
            Program client = frontEnd.getProgram( project);
            Program server = backEnd.getProgram( project);
            deploy(name,client,server);
        }
        else if( paper instanceof Client)
        {
            Client project = (Client)paper; //다운캐스팅
            FrontEnd frontEnd = new FrontEnd();
            project.setProgrammer(frontEnd);
            deploy(name, frontEnd.getProgram(project));
        }
    }
    public void deploy(String projectName, Program...programs){}
}
```

### 수정
#### 
