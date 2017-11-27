package lab.MyClasses;

public interface AdapterArrayList<T> extends Adapter {
    T get(int index);
    T[] get(int start, int finish);
    int size();
}