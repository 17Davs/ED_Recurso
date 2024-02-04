/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collections.implementations;




import collections.exceptions.EmptyCollectionException;
import collections.interfaces.ListADT;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 *
 * @author David Santos
 * @param <T>
 */
public abstract class ArrayList<T> implements ListADT<T> {

    private final int DEFAULT_CAPACITY = 20, NOT_FOUND = -1, EXPAND_BY = 2;

    protected int modCount;
    protected int rear;
    protected T[] list;

    public ArrayList() {
        modCount = 0;
        this.rear = 0;
        this.list = (T[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Creates an empty list using the specified capacity.
     *
     * @param initialCapacity represents the specified capacity
     */
    public ArrayList(int initialCapacity) {
        modCount = 0;
        this.rear = 0;
        this.list = (T[]) (new Object[initialCapacity]);
    }

    protected void expandCapacity() {
        T[] newList = (T[]) new Object[EXPAND_BY * list.length];
        int i = 0;
        for (T t : list) {
            newList[i++] = t;
        }
        list = newList;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        T element = list[0];

        for (int i = 0; i < rear - 1; i++) {
            list[i] = list[i + 1];
        }
        list[--rear] = null;
        modCount++;
        return element;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        T element = list[rear - 1];

        list[--rear] = null;
        modCount++;
        return element;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException, NoSuchElementException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }

        //se naõ encontrar lança NoSuchElement
        int index = find(element);

        T removedElement = list[index];

        for (int i = index; i < rear - 1; i++) {
            list[i] = list[i + 1];
        }

        list[--rear] = null;
        modCount++;
        return removedElement;
    }

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        return list[0];
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty list");
        }
        return list[rear - 1];

    }

    protected int find(T target) throws NoSuchElementException {

        for (int i = 0; i < rear; i++) {
            if (list[i].equals(target)) {
                return i;
            }
        }
        throw new NoSuchElementException("Target not found");
    }

    @Override
    public boolean contains(T target) {
        try {
            find(target);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    @Override
    public int size() {
        return rear;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    @Override
    public String toString() {

        String string = "List: \n";

        for (int j = 0; j < rear; j++) {
            string += list[j] + "\n";
        }

        return string;
    }

    private class MyIterator implements Iterator<T> {

        private boolean okToRemove;
        private int expectedModCount;
        private int current;

        public MyIterator() {
            expectedModCount = modCount;
            current = 0;
            okToRemove = false;
        }

        @Override
        public boolean hasNext() {
            return (current != size());
        }

        @Override
        public T next() {
            if (expectedModCount != modCount) {
                throw new java.util.ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.okToRemove = true;

            return (T) list[this.current++];
        }

        @Override
        public void remove() {

            //expectedModCount
            if (expectedModCount != modCount) {
                throw new java.util.ConcurrentModificationException();
            }

            if (!this.okToRemove) {
                throw new IllegalStateException();
            }

            this.okToRemove = false;

            try {
                ArrayList.this.remove(list[--this.current]);
                this.expectedModCount++;
            } catch (EmptyCollectionException ex) {

            }

        }

    }
}
