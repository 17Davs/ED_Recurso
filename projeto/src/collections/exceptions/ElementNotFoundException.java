/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collections.exceptions;

/**
 *
 * @author Utilizador
 */
public class ElementNotFoundException extends Exception{
    public ElementNotFoundException() {
        super("Elemento não encontrado na coleção.");
    }

    /**
     * Construtor para a exceção ElementNotFoundException com uma mensagem personalizada.
     * 
     * @param message A mensagem de exceção personalizada.
     */
    public ElementNotFoundException(String message) {
        super(message);
    }
}
