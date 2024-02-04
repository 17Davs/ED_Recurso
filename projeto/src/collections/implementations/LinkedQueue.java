/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collections.implementations;

import collections.exceptions.EmptyCollectionException;
import collections.interfaces.QueueADT;






/**
 *
 * @author David Santos
 */
public class LinkedQueue<T> implements QueueADT<T> {

    private LinearNode<T> front, rear;

    private int counter;

    public LinkedQueue() {
        this.front = null;
        this.rear = null;
        this.counter = 0;
    }
    
    
    @Override
    public void enqueue(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);

        if (isEmpty()) {
            this.front = newNode;
        } else {
            this.rear.setNext(newNode);
        }
        this.rear = newNode;
        this.counter++;

    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty queue!");
        }
        T element = this.front.getElement();
        
        this.front = this.front.getNext();
        this.counter--;
        
        if (isEmpty()) {
            this.rear = null;
        }
        
        return element;
    }

    @Override
    public T first() throws EmptyCollectionException{
         if (isEmpty()) {
            throw new EmptyCollectionException("Empty queue!");
        }
        return front.getElement();
    }

    @Override
    public boolean isEmpty() {
        return (counter == 0);
    }

    @Override
    public int size() {
        return counter;
    }
      @Override
    public String toString() {
        LinearNode<T> current = front;
        String string = "queue (primmeiro para ultimo): \n";
        while (current != null) {
            string += current.getElement() + "\n";
            current = current.getNext();
        }
        return string;
    }

}
