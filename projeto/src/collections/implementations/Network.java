/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collections.implementations;

import collections.exceptions.ElementNotFoundException;
import collections.exceptions.EmptyCollectionException;
import collections.interfaces.NetworkADT;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Utilizador
 * @param <T>
 */
public class Network<T> implements NetworkADT<T> {

    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices;
    protected double[][] adjMatrix;
    protected T[] vertices;

    /*
     * Cria um grafo vazio
     */
    public Network() {
        numVertices = 0;
        this.adjMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public Network(int capacidade) {
        numVertices = 0;
        this.adjMatrix = new double[capacidade][capacidade];
        this.vertices = (T[]) (new Object[capacidade]);
    }

    @Override
    public void addVertex(T vertex) {
        if (vertices.length == size()) {
            expandCapacity();
        } else {
            vertices[numVertices] = vertex;
            for (int i = 0; i <= numVertices; i++) {
                adjMatrix[numVertices][i] = 0;
                adjMatrix[i][numVertices] = 0;
            }
            numVertices++;
        }
    }

    protected int findIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    private void expandCapacity() {
        T[] newVertices = (T[]) (new Object[vertices.length * 2]);
        double[][] newAdjMatrix = new double[vertices.length  * 2][vertices.length  * 2];

        for (int i = 0; i < numVertices; i++) {
            newVertices[i] = vertices[i];
        }
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                newAdjMatrix[i][j] = adjMatrix[i][j];
            }
        }
        vertices = newVertices;
        adjMatrix = newAdjMatrix;
    }

    @Override
    public void removeVertex(T vertex) {
        int pos = findIndex(vertex);

        if (pos != -1) {
            if (numVertices > 1) {
                vertices[pos] = vertices[numVertices - 1];

                for (int i = 0; i < numVertices; i++) {
                    adjMatrix[i][pos] = adjMatrix[i][numVertices - 1];
                    adjMatrix[pos][i] = adjMatrix[numVertices - 1][i];
                }

                numVertices--;

                vertices[numVertices] = null;
                for (int i = 0; i < numVertices; i++) {
                    adjMatrix[numVertices][i] = 0;
                    adjMatrix[i][numVertices] = 0;
                }

            } else {
                numVertices = 0;
                vertices[0] = null;
                adjMatrix[0][0] = 0;
            }

        }
    }

    @Override
    public void addEdge(T vertex1, T vertex2) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    // retorna a posicao de um vertice no array de vertices
    private int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(vertex)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Vertex not found: " + vertex);
    }

    //colocar todos a private para nao conseguirmos invocar pois 
    //se estamos a usar um network nao usaremos arestas sem peso
    public void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = 1;
            adjMatrix[index2][index1] = 1;
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) {
        remvoveEdge(getIndex(vertex1), getIndex(vertex2));
    }

    private void remvoveEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            //adjMatrix[index1][index2] = false;
            //adjMatrix[index2][index1] = false;
        }
    }

    @Override
    public Iterator iteratorBFS(T startVertex) {

        Integer x = null; // variavel que vai armazenar o indice atual durante a travessia

        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        /*
     * Verifica se o index é valido se nao for entao retorna o iterador vazio
         */
        int startIndex = getIndex(startVertex);
        if (!indexIsValid(startIndex)) {
            System.out.println("Invalid start index: " + startIndex);
            return resultList.iterator();
        }

        /*
     * Cria um array boolean e coloca todos os vertices la como nao visitados
         */
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        /*
     * Coloca a posicao do startVertex na queue e marca a posicao como visitado
         */
        traversalQueue.enqueue(new Integer(startIndex));
        visited[startIndex] = true;

        /*
     * Faz o while ate esvaziar a queue
         */
        while (!traversalQueue.isEmpty()) {
            try {
                /*
             * Retira da queue e guarda na variavel x depois e guarda no ArrayUnorderedList
                 */
                x = traversalQueue.dequeue();
                resultList.addToRear(vertices[x.intValue()]);

                /*
             * adiciona os vertices adjacentes de x na queue e marca como visited
                 */
                for (int i = 0; i < numVertices; i++) {
                    double weight = getEdgeWeight(x, i);
                    if (weight > 0 && !visited[i]) {
                        traversalQueue.enqueue(new Integer(i));
                        visited[i] = true;
                    }
                }
            } catch (EmptyCollectionException ex) {
                System.out.println("Queue is unexpectedly empty.");
                ex.printStackTrace();
            }
        }

        return resultList.iterator();
    }

    @Override
    public Iterator iteratorDFS(T startVertex) {

        Integer x = null;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        int startIndex = getIndex(startVertex);
        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean visited[] = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }
        traversalStack.push(new Integer(startIndex));
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            try {
                x = traversalStack.peek(); //guarda o item no topo da stack
            } catch (EmptyCollectionException ex) {

            }
            found = false;

            for (int i = 0; (i < numVertices) && !found; i++) {
                double weight = getEdgeWeight(x, i);
                if (weight > 0 && !visited[i]) {
                    traversalStack.push(new Integer(i));
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }

            if (!found && !traversalStack.isEmpty()) {
                try {
                    traversalStack.pop();
                } catch (EmptyCollectionException ex) {

                }
            }
        }

        return resultList.iterator();

    }

    private double getEdgeWeight(int currentVertex, int neighbor) {
        if (indexIsValid(currentVertex) && indexIsValid(neighbor)) {
            return adjMatrix[currentVertex][neighbor];
        } else {
            return 0;
        }
    }

    @Override
    public boolean isEmpty() {
        return numVertices == 0;
    }

    @Override
    public boolean isConnected() {
        if (isEmpty()) {
            return false;
        }

        boolean[] visited = new boolean[numVertices];
        int start = findFirstNonNullVertex();

        buscaProfundidade(start, visited);
        for (int i = 0; i < numVertices; i++) {
            if (!visited[i] && vertices[i] != null) {
                return false;
            }
        }

        return true;

    }

    private int findFirstNonNullVertex() {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] != null) {
                return i;
            }
        }
        return -1;
    }

    private void buscaProfundidade(int start, boolean[] visited) {
        visited[start] = true;

        for (int i = 0; i < numVertices; i++) {
            double weight = getEdgeWeight(start, i);
            if (weight > 0 && !visited[i]) {
                buscaProfundidade(i, visited);
            }
        }
    }

    @Override
    public int size() {
        return numVertices;
    }

    @Override
    public double shortestPathWeight(T vertex1, T vertex2) {

        int somatorio = 0;
        Iterator iterator = iteratorShortestPath(vertex1, vertex2);

        while (iterator.hasNext()) {
            PriorityQueueNode<T> node = (PriorityQueueNode<T>) iterator.next();
            somatorio += node.getPriority();
        }

        return somatorio;

    }

    private boolean indexIsValid(int index) {
        return index >= 0 && index < numVertices;
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        return iteratorShortestPath(getIndex(startVertex), getIndex(startVertex));
    }

    public Iterator iteratorShortestPath(int startIndex, int targetIndex) {

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            throw new IllegalArgumentException("Invalid indices");
        }

        int[] distances = new int[numVertices];
        int[] precessors = new int[numVertices];

        // Initialize distances and predecessors
        for (int i = 0; i < numVertices; i++) {
            if (i == startIndex) {
                distances[i] = 0;
            } else {
                distances[i] = Integer.MAX_VALUE;
            }
            precessors[i] = -1;
        }

        PriorityQueue<T> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < numVertices; i++) {
            priorityQueue.addElement(vertices[i], (int) distances[i]);
        }

        // Process vertices using Dijkstra's algorithm
        while (!priorityQueue.isEmpty()) {
            PriorityQueueNode<T> minNode = null;
            try {
                minNode = priorityQueue.removeMin();
            } catch (EmptyCollectionException ex) {

            }
            int u = getIndex(minNode.getElement());

            // Update distances and predecessors
            for (int v = 0; v < numVertices; v++) {
                if (isAdjacent(u, v) && distances[u] + getEdgeWeight(u, v) < distances[v]) {
                    distances[v] = (int) (distances[u] + getEdgeWeight(u, v));
                    precessors[v] = u;

                    try {
                        // Update priority in the priority queue
                        priorityQueue.update(vertices[v], (int) distances[v]);
                    } catch (ElementNotFoundException ex) {
                        Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (EmptyCollectionException ex) {
                        Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        ArrayUnorderedList<T> path = new ArrayUnorderedList<>(numVertices);
        int currentVertex = targetIndex;
        while (currentVertex != -1) {
            path.addToFront(vertices[currentVertex]);
            currentVertex = precessors[currentVertex];
        }
        return path.iterator();
    }

    public boolean hasEdge(T vertex1, T vertex2) {
        int index1 = findIndex(vertex1);
        int index2 = findIndex(vertex2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            return adjMatrix[index1][index2] != 0;
        } else {
            throw new IllegalArgumentException("Vértices inválidos: " + vertex1 + ", " + vertex2);
        }
    }

    public boolean isAdjacent(int vertex1, int vertex2) {
        if (getEdgeWeight(vertex1, vertex2) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        addEdge(findIndex(vertex1), findIndex(vertex2), weight);
    }

    protected void addEdge(int index1, int index2, double weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = weight;
            adjMatrix[index2][index1] = weight;
        } else {
            throw new IllegalArgumentException("Vértices inválidos: ");
        }

    }

}
