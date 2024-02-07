package api;

import collections.implementations.ArrayUnorderedList;
import collections.implementations.Network;

/**
 *
 * @author David Santos
 */
public class Mapa<T> extends Network<T> {

    public Mapa() {
    }

    public Mapa(int capacidade) {
        super(capacidade);
    }

    public ArrayUnorderedList<T> getVertexes() {
        ArrayUnorderedList<T> temp = new ArrayUnorderedList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            temp.addToRear(vertices[i]);
        }
        return temp;
    }

    public ArrayUnorderedList<T> getApenasT() {
        ArrayUnorderedList<T> temp = new ArrayUnorderedList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].getClass().equals(Localidade.class)) {
                temp.addToRear(vertices[i]);
            }
        }
        return temp;
    }

    public Bandeira definirBandeira(Localidade localidade) {
        int i = 0;
        Boolean found = false;
        Bandeira bandeira = new Bandeira(localidade.getId(), localidade.getNome());
        while (i < numVertices && !found) {
            if (vertices[i].equals(localidade)) {
                vertices[i] = (T) bandeira ;
                found = true; 
            }
            i++;
        }
        return bandeira;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public double[][] getAdjMatrix() {
        double[][] temp = new double[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                temp[i][j] = adjMatrix[i][j];
            }
        }
        return temp;
    }

    public T getVertex(int index) throws IllegalArgumentException {
        if (!indexIsValid(index)) {
            throw new IllegalArgumentException("Index invalid");
        }
        return vertices[index];
    }

}
