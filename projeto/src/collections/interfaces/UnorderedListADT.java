/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package collections.interfaces;

/**
 *
 * @author David Santos
 * @param <T> tipo
 */
public interface UnorderedListADT<T> extends ListADT<T> {

    /**
     * Adds the specified element to this list at the proper location
     *
     * @param element the element to be added to this list
     */
    public void addToFront(T element);

    public void addToRear(T element);

    public void addAfter(T element, T target);
}
