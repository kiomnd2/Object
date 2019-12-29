public abstract class FrontEnd<T extends Paper> extends Programmer<T>{
    private Language language;
    private Library library;

    @Override
    Program makeProgram() {
        return new Program();
    }
}
