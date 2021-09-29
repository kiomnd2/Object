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

## 문제점
```java
if(paper instanceof Client) {
    Client pb = (Client) pb;
    language = pb.language;
    library = pb.library;            
}
```
* Paper가 제역할을 하지 못해 OCP가 깨짐

```java
if(paper instanceof Client) {
    paper.setData(this);
    return ...         
}    
```
```java
public abstract class Programmer{
    public Program getProgram(Paper paper) {
        paper.setData(this);
        return makeProgram(paper);
    }
    abstract Program makeProgram(Paper paper);
}
```
```java
public interface Paper {
    void setData(Programmer programmer);
}

public void setData(Programmer programmer) {
    if(programmer instanceof FrontEnd) {
        FrontEnd frontend = (FrontEnd) programmer;
        ...
        ...
    }
}
```
* 헐리우드 원칙으로 책임 paper 쪽으로 떠밈
* 하지만 paper 역시 setData 시, 역할이 paper로 변한 것 뿐 LSP 가 처리되지 못한건 같다
* 해결을 위해 제네릭을 사용 

```java
public interface Paper<T extends Programmer> {
    void setData(T programmer);
}

public class Client implements Paper<FrontEnd> {

    ...
    @Override
    public void setData(FrontEnd programmer) {
        programmer.setLibrary(library);
        ... 
    }
}
```
* 제네릭을 사용하면 형으로써 if 를 대체할 수 있다.
    * 제네릭을 사용한 클래스는 범용에서 좀더 구체적인 정의를 하는 클래스로 변화한다.
* 하위형을 확정지었기 때문에 컴파일 단에서 오류를 조율 할 수 있다.

## OCP와 제네릭을 통한 해결
 * 헐리우드 원칙 (묻지말고 넘겨라)

```java
public abstract class Programmer<T extends Paper> {
    public Program getProgram(T paper) {
        setData(paper);
        return makeProgram();
    }
    abstract void setData(T paper);
    abstract Progam makeProgram();
}
```
```java
public abstract class BackEnd<T extends Paper> extends Programmer<T> {
    protected Server server;
    protected Language language; 

    @Override
    public Program makeProgram() {
        return new Program();
    }
}
```
* setData에 대한 책임을 자식에 넘김

## 클라이언트의 변화
```java
if (paper instanceof ServerClient) {
    ServerClient project = (ServerClient) paper;

    Programmer frontEnd = new FrontEnd<ServerClient>() {
        @Override
        void setData(ServerClient paper) {
            language = paper.frontEndLanguage;
        }
    }   
    Programmer backEnd = new FrontEnd<ServerClient>() {
            @Override
            void setData(ServerClient paper) {
                server = paper.server;
                language = paper.frontEndLanguage;
            }
    }
    ...
    ...
}
```
* 제네릭을 사용해서 케이스를 제네릭으로 한정지었기 때문에 떡진 if문이 사라져있다.
* if 만큼의 형을 만듦
* 공통적으로 setData 경우의 수만큼 계속 생성되며 중복됨 추상화를 통해 해결

```java
public interface Paper {
    Program[] run();
}
public class Director {
    private Map<String,Paper> projects = new HashMap<>();
    public void addProject(String name, Paper paper) {projects.put(name,paper);}
    public void runProject(String name) {
        if (!projects.containKey(name)) throw new RuntimeException("no project");
        deploy(name, projects.get(name).run());
    } 
    private void deploy(String projectName, Program... program){}
}
```
```java
public class Main {
    public static void main(String[] args) {
        Director director = new Director();
        director.addProject("여행사 A 프론트개편", new Client() {
            
            @Override
            public Program[] run() {
                FrontEnd frontEnd = new FrontEnd<Client>() {
                    @Override
                    void setData(Client paper) {
                        library = paper.library;           
                        language = paper.language;
                    }
                };
                setProgramer(frontEnd);
                return new Program[]{frontEnd.getProgram(this)};
            }
        }
    }
}
```
* 

