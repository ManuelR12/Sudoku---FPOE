package com.example.proyecto2fpoe.Model.List;

/**
 * A generic interface that defines the basic operations for a list-like data structure.
 * It provides methods for adding, removing, and accessing elements, as well as other
 * common list operations. The interface extends {@code Iterable}, allowing iteration
 * over its elements.
 *
 * @param <T> the type of elements in this list
 */
public interface IList<T> extends Iterable<T> {

    /**
     * Adds an element to the beginning of the list.
     *
     * @param element the element to add
     */
    void addFirst(final T element);

    /**
     * Adds an element to the end of the list.
     *
     * @param element the element to add
     */
    void addLast(final T element);

    /**
     * Returns the first element of the list.
     *
     * @return the first element in the list
     * */
    T getFirst();

    /**
     * Returns the last element of the list.
     *
     * @return the last element in the list
     */
    T getLast();

    /**
     * Removes the first element of the list.
     */
    void removeFirst();

    /**
     * Removes the last element of the list.
     */
    void removeLast();

    /**
     * Checks if the list contains a specific element.
     *
     * @param element the element to check for
     * @return {@code true} if the list contains the element, {@code false} otherwise
     */
    Boolean contains(final T element);

    /**
     * Removes the first occurrence of a specific element from the list.
     *
     * @param element the element to remove
     */
    void remove(final T element);

    /**
     * Removes all elements from the list.
     */
    void clear();

    /**
     * Returns the element at a specific position in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    T get(Integer index);

    /**
     * Replaces the element at the specified position in the list with the specified element.
     *
     * @param index the index of the element to replace
     * @param element the element to be stored at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    void set(Integer index, final T element);

    /**
     * Inserts an element at the specified position in the list.
     *
     * @param index the index at which the specified element is to be inserted
     * @param element the element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    void add(Integer index, final T element);

    /**
     * Removes the element at the specified position in the list.
     *
     * @param index the index of the element to be removed
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    void remove(Integer index);

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * or -1 if the list does not contain the element.
     *
     * @param element the element to search for
     * @return the index of the first occurrence of the element, or -1 if not found
     */
    Integer indexOf(final T element);

    /**
     * Returns the index of the last occurrence of the specified element in the list,
     * or -1 if the list does not contain the element.
     *
     * @param element the element to search for
     * @return the index of the last occurrence of the element, or -1 if not found
     */
    Integer lastIndexOf(final T element);

    /**
     * Returns {@code true} if the list contains no elements.
     *
     * @return {@code true} if the list is empty, {@code false} otherwise
     */
    Boolean isEmpty();

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    Integer size();

    /**
     * Returns a deep copy of the list. The new list will contain the same elements,
     * but will be independent of the original list.
     *
     * @return a deep copy of the list
     */
    IList<T> deepCopy();
}
