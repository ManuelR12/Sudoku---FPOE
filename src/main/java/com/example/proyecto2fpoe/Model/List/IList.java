package com.example.proyecto2fpoe.Model.List;

public interface IList<T> extends Iterable<T> {
    void addFirst(final T element);
    void addLast(final T element);
    T getFirst();
    T getLast();
    void removeFirst();
    void removeLast();
    Boolean contains(final T element);
    void remove(final T element);
    void clear();
    T get(Integer index);
    void set(Integer index, final T element);
    void add(Integer index, final T element);
    void remove(Integer index);
    Integer indexOf(final T element);
    Integer lastIndexOf(final T element);
    Boolean isEmpty();
    Integer size();
    IList<T> deepCopy();
}
