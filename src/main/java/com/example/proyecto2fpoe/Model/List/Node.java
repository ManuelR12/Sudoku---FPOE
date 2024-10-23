package com.example.proyecto2fpoe.Model.List;

/**
 * A node in a doubly linked list. Each node holds a reference to an element and
 * pointers to the next and previous nodes in the list.
 *
 * @param <T> the type of the element stored in the node
 */
public class Node<T> {

    /**
     * The data stored in this node.
     */
    T data;

    /**
     * A reference to the next node in the list.
     */
    Node<T> next;

    /**
     * A reference to the previous node in the list.
     */
    Node<T> prev;

    /**
     * Constructs a new node with the specified data.
     * The next and previous pointers are initialized to {@code null}.
     *
     * @param data the data to be stored in this node
     */
    Node(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
