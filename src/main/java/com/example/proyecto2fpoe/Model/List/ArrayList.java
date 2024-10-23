package com.example.proyecto2fpoe.Model.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic dynamic array implementation that mimics the functionality of
 * a list using an underlying array. This class implements the {@code IList} interface.
 *
 * @param <T> the type of elements in this list
 */
public class ArrayList<T> implements IList<T> {

    /** The underlying array to store elements */
    private T[] array;

    /** The number of elements in the list */
    private int size;

    /** The initial capacity of the array */
    private static final int INITIAL_CAPACITY = 10;

    /**
     * Constructs an empty list with an initial capacity of 10.
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        array = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds an element to the beginning of the list.
     *
     * @param element the element to add
     */
    @Override
    public void addFirst(final T element) {
        add(0, element);
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element the element to add
     */
    @Override
    public void addLast(final T element) {
        ensureCapacity(size + 1);
        array[size++] = element;
    }

    /**
     * Returns the first element of the list.
     *
     * @return the first element in the list
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return array[0];
    }

    /**
     * Returns the last element of the list.
     *
     * @return the last element in the list
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return array[size - 1];
    }

    /**
     * Removes the first element of the list.
     */
    @Override
    public void removeFirst() {
        remove(0);
    }

    /**
     * Removes the last element of the list.
     *
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public void removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        array[--size] = null;
    }

    /**
     * Checks if the list contains a specific element.
     *
     * @param element the element to check for
     * @return {@code true} if the list contains the element, {@code false} otherwise
     */
    @Override
    public Boolean contains(final T element) {
        return indexOf(element) != -1;
    }

    /**
     * Removes the first occurrence of a specific element from the list.
     *
     * @param element the element to remove
     */
    @Override
    public void remove(final T element) {
        int index = indexOf(element);
        if (index != -1) {
            remove(index);
        }
    }

    /**
     * Removes all elements from the list.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    /**
     * Returns the element at a specific position in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public T get(Integer index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        return array[index];
    }

    /**
     * Replaces the element at the specified position in the list with the specified element.
     *
     * @param index the index of the element to replace
     * @param element the element to be stored at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public void set(Integer index, final T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        array[index] = element;
    }

    /**
     * Inserts an element at the specified position in the list.
     *
     * @param index the index at which the specified element is to be inserted
     * @param element the element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
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

    /**
     * Removes the element at the specified position in the list.
     *
     * @param index the index of the element to be removed
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public void remove(Integer index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * or -1 if the list does not contain the element.
     *
     * @param element the element to search for
     * @return the index of the first occurrence of the element, or -1 if not found
     */
    @Override
    public Integer indexOf(final T element) {
        for (int i = 0; i < size; i++) {
            if ((array[i] == null && element == null) || (array[i] != null && array[i].equals(element))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element in the list,
     * or -1 if the list does not contain the element.
     *
     * @param element the element to search for
     * @return the index of the last occurrence of the element, or -1 if not found
     */
    @Override
    public Integer lastIndexOf(final T element) {
        for (int i = size - 1; i >= 0; i--) {
            if ((array[i] == null && element == null) || (array[i] != null && array[i].equals(element))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns {@code true} if the list contains no elements.
     *
     * @return {@code true} if the list is empty, {@code false} otherwise
     */
    @Override
    public Boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    @Override
    public Integer size() {
        return size;
    }

    /**
     * Returns a deep copy of the list.
     *
     * @return a deep copy of the list
     */
    @Override
    public IList<T> deepCopy() {
        ArrayList<T> copy = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            copy.addLast(array[i]);
        }
        return copy;
    }

    /**
     * Ensures that the array has sufficient capacity to hold additional elements.
     * If the current capacity is not enough, the array size is doubled.
     *
     * @param minCapacity the minimum required capacity
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > array.length) {
            int newCapacity = Math.max(array.length * 2, minCapacity);
            @SuppressWarnings("unchecked")
            T[] newArray = (T[]) new Object[newCapacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list
     */
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

