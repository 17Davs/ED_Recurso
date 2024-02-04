/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collections.implementations;



import collections.interfaces.UnorderedListADT;
import java.util.NoSuchElementException;

/**
 *
 * @author David Santos
 */
public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

      public ArrayUnorderedList() {
        super();
    }
    public ArrayUnorderedList(int initialCapacity) {
        super(initialCapacity);
    }
    @Override
    public void addToFront(T element) {
        if (super.size() == list.length) {
            expandCapacity();
        }

        for (int i = rear; i > 0; --i) {
            list[i] = list[i - 1];
        }

        list[0] = element;
        rear++;
        modCount++;
    }

    @Override
    public void addToRear(T element) {
        if (super.size() == list.length) {
            expandCapacity();
        }

        list[rear++] = element;
        modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws NoSuchElementException {
        if (super.size() == list.length) {
            expandCapacity();
        }
       
        //search for target   throws NoSuchElementException    
        int targetIndex = find(target);

        // percorrer array e fazer shift
        for (int i = rear; i > targetIndex + 1; --i) {
            list[i] = list[i - 1];
        }
        //inserir element na posição logo apos target
        list[targetIndex + 1] = element;
        rear++;
        modCount++;
    }

   

}
