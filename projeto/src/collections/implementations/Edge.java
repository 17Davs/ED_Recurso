/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collections.implementations;

/**
 * Classe auxiliar para representar uma aresta.
 */
public class Edge<T> {

    private final T vertex1;
    private final T vertex2;
    private final double weight;

    public Edge(T vertex1, T vertex2, double weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    public T getVertex1() {
        return vertex1;
    }

    public T getVertex2() {
        return vertex2;
    }

    public double getWeight() {
        return weight;
    }
}
