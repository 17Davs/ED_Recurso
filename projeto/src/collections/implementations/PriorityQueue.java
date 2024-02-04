/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collections.implementations;

import collections.exceptions.ElementNotFoundException;
import collections.exceptions.EmptyCollectionException;
import java.util.Arrays;

/**
 *
 * @author David Santos
 */
/**
 * PriorityQueue demonstrates a priority queue using a Heap.
 *
 */
public class PriorityQueue<T> extends
        ArrayHeap<PriorityQueueNode<T>> {

    private PriorityQueueNode<T>[] tree;
    private final int DEFAULT_CAPACITY = 10000;

    /**
     * Creates an empty priority queue.
     */
    public PriorityQueue() {
        super();
        tree = new PriorityQueueNode[DEFAULT_CAPACITY];
    }

    /**
     * Adds the given element to this PriorityQueue.
     *
     * @param object the element to be added to the priority queue
     * @param priority the integer priority of the element to be added
     */
    public void addElement(T object, int priority) {
        PriorityQueueNode<T> node
                = new PriorityQueueNode<>(object, priority);
        super.addElement(node);
    }

    /**
     * Removes the next highest priority element from this priority queue and
     * returns a reference to it.
     *
     * @return a reference to the next highest priority element in this queue
     */
    public T removeNext() {
        try {
            PriorityQueueNode<T> temp
                    = (PriorityQueueNode<T>) super.removeMin();
            return temp.getElement();
        } catch (EmptyCollectionException ex) {

        }
        return null; // exception??
    }

    public PriorityQueueNode<T> findNode(T targetElement) throws ElementNotFoundException, EmptyCollectionException {
        if (targetElement == null) {
            throw new ElementNotFoundException("elemento null");
        }
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty");
        }
        PriorityQueueNode<T> temp = null;
        boolean found = false;

        for (int ct = 0; ct < count && !found; ct++) {
            if (targetElement.equals(tree[ct])) {
                found = true;
                temp = tree[ct];
            }
        }
        if (!found) {
            throw new ElementNotFoundException("binary tree");
        }
        return temp;
    }

    public void update(T targetElement, int priority) throws ElementNotFoundException, EmptyCollectionException {

        if (targetElement == null) {
            throw new ElementNotFoundException("elemento null");
        }
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty");
        }

        int index = -1;
        boolean found = false;

        PriorityQueueNode<T> tempPriorityQueue = new PriorityQueueNode<>(targetElement, 1000);

        for (int ct = 0; ct < count && !found; ct++) {
            if (tree[ct] != null && tempPriorityQueue != null && tempPriorityQueue.equals(copyPriorityQueueNode(tree[ct]))) {
                found = true;
                tree[ct] = tree[count - 1];
                index = ct;
            }
        }

        heapifyRemove();
        count--;

        addElement(targetElement, priority);

    }

    private PriorityQueueNode<T> copyPriorityQueueNode(PriorityQueueNode<T> original) {
        if (original == null) {
            return null;
        }

        T element = original.getElement();
        int priority = original.getPriority();

        return new PriorityQueueNode<>(element, priority);
    }

    private void heapifyRemove() {
        PriorityQueueNode<T> temp;
        int node = 0;
        int left = 1;
        int right = 2;
        int next;

        if ((tree[left] == null) && (tree[right] == null)) {
            next = count;
        } else if (tree[left] == null) {
            next = right;
        } else if (tree[right] == null) {
            next = left;
        } else if (((Comparable) tree[left]).compareTo(tree[right]) < 0) {
            next = left;
        } else {
            next = right;
        }
        temp = tree[node];

        while ((next < count) && (((Comparable) tree[next]).compareTo(temp) < 0)) {
            tree[node] = tree[next];
            node = next;
            left = 2 * node + 1;
            right = 2 * (node + 1);
            if ((tree[left] == null) && (tree[right] == null)) {
                next = count;
            } else if (tree[left] == null) {
                next = right;
            } else if (tree[right] == null) {
                next = left;
            } else if (((Comparable) tree[left]).compareTo(tree[right]) < 0) {
                next = left;
            } else {
                next = right;
            }
        }
        tree[node] = temp;
    }

    public void setPriority(T targetElement, int newPriority) {
        try {
            PriorityQueueNode<T> targetNode = findNode(targetElement);
            targetNode.setPriority(newPriority);
        } catch (ElementNotFoundException | EmptyCollectionException e) {
            // Lide com a exceção, se necessário
            e.printStackTrace();
        }
    }
}
