/*This is generic class to deal with statistics objects*/
package org.example.model.data;

public class Data<T> {
    private T data;

    public Data(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return
                "data: " + data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data<?> data1 = (Data<?>) o;

        return data != null ? data.equals(data1.data) : data1.data == null;
    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
