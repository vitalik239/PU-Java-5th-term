package lab.functions;


public interface Function<S, T> {
    void execute(Function<?, S> f);
    T getResult();
}
