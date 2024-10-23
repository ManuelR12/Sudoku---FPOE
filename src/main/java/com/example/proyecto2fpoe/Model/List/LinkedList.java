package com.example.proyecto2fpoe.Model.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A doubly linked list implementation of the {@link IList} interface. This list supports
 * adding, removing, and accessing elements from both the front and the back, as well as
 * common list operations such as index-based access and searching.
 *
 * @param <T> the type of elements in this list
 */
public class LinkedList<T> implements IList<T> {

    /**
     * The head (first) node of the list.
     */
    private Node<T> head;

    /**
     * The tail (last) node of the list.
     */
    private Node<T> tail;

    /**
     * The number of elements in the list.
     */
    private int size;

    /**
     * Constructs an empty linked list.
     */
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds an element to the front of the list.
     *
     * @param element the element to add
     */
    @Override
    public void addFirst(final T element) {
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param element the element to add
     */
    @Override
    public void addLast(final T element) {
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Retrieves the first element in the list.
     *
     * @return the first element in the list
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return head.data;
    }

    /**
     * Retrieves the last element in the list.
     *
     * @return the last element in the list
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return tail.data;
    }

    /**
     * Removes the first element of the list.
     *
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public void removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        if (head == tail) { // only one element in the list
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
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
        if (head == tail) { // only one element in the list
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
    }

    /**
     * Checks if the list contains a specific element.
     *
     * @param element the element to search for
     * @return {@code true} if the list contains the element, {@code false} otherwise
     */
    @Override
    public Boolean contains(final T element) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Removes the first occurrence of the specified element from the list.
     *
     * @param element the element to remove
     */
    @Override
    public void remove(final T element) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                if (current == head) {
                    removeFirst();
                } else if (current == tail) {
                    removeLast();
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    size--;
                }
                return;
            }
            current = current.next;
        }
    }

    /**
     * Removes all elements from the list.
     */
    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    /**
     * Returns the element at a specific position in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified position
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public T get(Integer index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return getNodeAtIndex(index).data;
    }

    /**
     * Replaces the element at the specified position in the list with the specified element.
     *
     * @param index   the index of the element to replace
     * @param element the element to be stored at the specified position
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public void set(Integer index, final T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        getNodeAtIndex(index).data = element;
    }

    /**
     * Inserts an element at the specified position in the list.
     *
     * @param index   the index at which the element should be inserted
     * @param element the element to insert
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public void add(Integer index, final T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (index == 0) {
            addFirst(element);
        } else if (index == size) {
            addLast(element);
        } else {
            Node<T> newNode = new Node<>(element);
            Node<T> current = getNodeAtIndex(index);
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    /**
     * Removes the element at the specified position in the list.
     *
     * @param index the index of the element to remove
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public void remove(Integer index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = getNodeAtIndex(index);
        if (current == head) {
            removeFirst();
        } else if (current == tail) {
            removeLast();
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;
        }
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * or -1 if the list does not contain the element.
     *
     * @param element the element to search for
     * @return the index of the first occurrence, or -1 if not found
     */
    @Override
    public Integer indexOf(final T element) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(element)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element in the list,
     * or -1 if the list does not contain the element.
     *
     * @param element the element to search for
     * @return the index of the last occurrence, or -1 if not found
     */
    @Override
    public Integer lastIndexOf(final T element) {
        Node<T> current = tail;
        int index = size - 1;
        while (current != null) {
            if (current.data.equals(element)) {
                return index;
            }
            current = current.prev;
            index--;
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
     * Creates a deep copy of this linked list. The new list will contain the same elements,
     * but will be independent of the original list.
     *
     * @return a deep copy of the linked list
     */
    @Override
    public IList<T> deepCopy() {
        LinkedList<T> copy = new LinkedList<>();
        Node<T> current = head;
        while (current != null) {
            copy.addLast(current.data);
            current = current.next;
        }
        return copy;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in the list
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    /**
     * Helper method to get the node at a specific index.
     *
     * @param index the index of the node to retrieve
     * @return the node at the specified index
     */
    private Node<T> getNodeAtIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}
