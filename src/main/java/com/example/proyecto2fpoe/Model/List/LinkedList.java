package com.example.proyecto2fpoe.Model.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements IList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Add an element to the front of the list
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

    // Add an element to the end of the list
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

    // Get the first element in the list
    @Override
    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return head.data;
    }

    // Get the last element in the list
    @Override
    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        return tail.data;
    }

    // Remove the first element
    @Override
    public void removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        if (head == tail) { // If there's only one element
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
    }

    // Remove the last element
    @Override
    public void removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
    }

    // Check if the list contains a given element
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

    // Remove a specific element from the list
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

    // Clear the entire list
    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    // Get the element at a specific index
    @Override
    public T get(Integer index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = getNodeAtIndex(index);
        return current.data;
    }

    // Set the element at a specific index
    @Override
    public void set(Integer index, final T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = getNodeAtIndex(index);
        current.data = element;
    }

    // Add an element at a specific index
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

    // Remove the element at a specific index
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

    // Find the index of a specific element
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

    // Find the last index of a specific element
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

    // Check if the list is empty
    @Override
    public Boolean isEmpty() {
        return size == 0;
    }

    // Get the size of the list
    @Override
    public Integer size() {
        return size;
    }

    // Create a deep copy of the list
    @Override
    public IList<T> deepCopy() {
        LinkedList<T> copy = new LinkedList<>();
        Node<T> current = head;
        while (current != null) {
            copy.addLast(current.data); // Deep copy each element
            current = current.next;
        }
        return copy;
    }

    // Helper method to get node at a specific index
    private Node<T> getNodeAtIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    // Iterator implementation
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
}
