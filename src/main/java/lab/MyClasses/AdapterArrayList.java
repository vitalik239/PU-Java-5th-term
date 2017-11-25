package lab.MyClasses;

public class AdapterArrayList<T> implements Adapter {
    private T[] data;

    public AdapterArrayList(T[] data) {
        this.data = data;
    }

    public T get(int index) {
        return data[index];
    }

    public T[] get(int start, int finish) {
        T[] result = new T[finish - start + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = this.data[i + start];
        }
        return result;
    }

    public int size() {
        return data.length;
    }
}
