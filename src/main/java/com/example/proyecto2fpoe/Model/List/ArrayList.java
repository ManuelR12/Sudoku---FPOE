package com.example.proyecto2fpoe.Model.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<T> implements IList<T> {
    private T[] array;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        array = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public void addFirst(final T element) {
        add(0, element);
    }

    @Override
    public void addLast(final T element) {
        ensureCapacity(size + 1);
        array[size++] = element;
    }

    @Override
    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return array[0];
    }

    @Override
    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return array[size - 1];
    }

    @Override
    public void removeFirst() {
        remove(0);
    }

    @Override
    public void removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        array[--size] = null;
    }

    @Override
    public Boolean contains(final T element) {
        return indexOf(element) != -1;
    }

    @Override
    public void remove(final T element) {
        int index = indexOf(element);
        if (index != -1) {
            remove(index);
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(Integer index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        return array[index];
    }

    @Override
    public void set(Integer index, final T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        array[index] = element;
    }

    @Override
    public void add(Integer index, final T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        ensureCapacity(size + 1);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public void remove(Integer index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
    }

    @Override
    public Integer indexOf(final T element) {
        for (int i = 0; i < size; i++) {
            if ((array[i] == null && element == null) || (array[i] != null && array[i].equals(element))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer lastIndexOf(final T element) {
        for (int i = size - 1; i >= 0; i--) {
            if ((array[i] == null && element == null) || (array[i] != null && array[i].equals(element))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Integer size() {
        return size;
    }

    @Override
    public IList<T> deepCopy() {
        ArrayList<T> copy = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            copy.addLast(array[i]);
        }
        return copy;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > array.length) {
            int newCapacity = Math.max(array.length * 2, minCapacity);
            @SuppressWarnings("unchecked")
            T[] newArray = (T[]) new Object[newCapacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[current++];
            }
        };
    }
}

