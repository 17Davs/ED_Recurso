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
