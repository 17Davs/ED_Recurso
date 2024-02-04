/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collections.implementations;

import collections.exceptions.EmptyCollectionException;
import collections.interfaces.StackADT;



/**
 *
 * @author David Santos
 */
public class LinkedStack<T> implements StackADT<T> {

    private LinearNode<T> top;
    private int counter;

    public LinkedStack(LinearNode<T> topNode) {
        this.top = topNode;
        this.counter = 1;
    }

    public LinkedStack() {
        this.top = null;
        this.counter = 0;
    }

    @Override
    public void push(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);

        newNode.setNext(this.top);
        this.top = newNode;
        this.counter++;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty stack");
        }

        T t = this.top.getElement();
        LinearNode<T> previous = this.top;
        this.top = this.top.getNext();
        //tirar referencia do next do elemento removido (atual top passa a ser referenciado 1 unica vez)
        previous.setNext(null);
        counter--;
        return t;
    }

    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty stack");
        }
        return this.top.getElement();
    }

    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    @Override
    public int size() {
        return counter;
    }
    
     @Override
    public String toString() {
        LinearNode<T> current = top;
        String string = "Stack: \n";
        while (current != null) {
            string += current.getElement() + "\n";
            current = current.getNext();
        }
        return string;
    }

}
