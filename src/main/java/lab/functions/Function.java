package lab.functions;


public interface Function<S, T> {
    void execute(Function<?, S> f) throws Exception;
    Class inputClass();
    Class resultClass();
    T getResult();
}
