package api;

import collections.implementations.ArrayUnorderedList;
import collections.implementations.Network;

/**
 * Classe mapa que herda as caracteristicas da classe Network pois iremos criar uma network
 * @author David Santos e Rafael Coronel
 */
public class Mapa<T> extends Network<T> {

    /**
     * Construtor da classe Mapa que cria um mapa se uma capacidade especificada
     */
    public Mapa() {
    }

    /**
     * Construtor da classe Mapa que cria um mapa com uma capacidade especificada
     */
    public Mapa(int capacidade) {
        super(capacidade);
    }

    /**
     * Metodo que devolve todos os vertices do mapa adicionando 
     * todos os vertices numa lista não ordenada 
     * @return temp Retorna a lista dos vertices de um mapa
     */
    public ArrayUnorderedList<T> getVertexes() {
        ArrayUnorderedList<T> temp = new ArrayUnorderedList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            temp.addToRear(vertices[i]);
        }
        return temp;
    }

    /**
     * Metodo para devolver so as localidades sem bandeira adicionando essas 
     * localidades numa lista não ordenada
     * @return 
     */
    public ArrayUnorderedList<T> getApenasT() {
        ArrayUnorderedList<T> temp = new ArrayUnorderedList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].getClass().equals(Localidade.class)) {
                temp.addToRear(vertices[i]);
            }
        }
        return temp;
    }

    /**
     * Metodo que atribui uma bandeira a uma localidade especifica
     * @param localidade Vertice onde será colocada a bandeira
     * @return bandeira Retorna a bandeira colocada na localidade
     */
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

    /**
     * Metodo get que retorna o numero de vertices do mapa
     * @return numVertices Retorna o numero de vertices
     */
    public int getNumVertices() {
        return numVertices;
    }

    /**
     * Constroi a matriz de adjacencias e coloca os dados 
     * de ligaçao entre cada vertice
     * @return Retorna a matriz de adjacencias
     */
    public double[][] getAdjMatrix() {
        double[][] temp = new double[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                temp[i][j] = adjMatrix[i][j];
            }
        }
        return temp;
    }

    /**
     * Este método retorna o vértice na posição especificada pelo parâmetro index
     * @param index posição do vertice
     * @return O método retorna o vértice na posição especificada pelo índice.
     * @throws IllegalArgumentException lança a exceção se o index do vertice nao for valido
     */
    public T getVertex(int index) throws IllegalArgumentException {
        if (!indexIsValid(index)) {
            throw new IllegalArgumentException("Index invalid");
        }
        return vertices[index];
    }

}
