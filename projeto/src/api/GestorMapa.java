/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import java.util.Random;

/**
 * Classe para gerir a criaçao do mapa
 *
 * @author Rafael Coronel e David Santos
 */
public class GestorMapa {

    /**
     * Metodo responsavel por gerar o numero de arestas do mapa consoante o
     * preenchimento definido
     *
     * @param preenchimento preenchimento do mapa
     * @param tipoMapa identifica se o mapa é Unidirecional ou Bidirecional
     * @param mapa mapa no qual vamos gerir as arestas
     */
    public static void gerarArestas(int preenchimento, TipoMapa tipoMapa, Mapa<Localidade> mapa) {
        if (tipoMapa != TipoMapa.UNIDIRECIONAL) {
            throw new UnsupportedOperationException("Tipo de Mapa invalido para este método");
        }

        int quantidadeLocalizacoes = mapa.getNumVertices();
        int quantidadeArestas = (int) Math.round(((quantidadeLocalizacoes * (quantidadeLocalizacoes - 1)) * ((double) preenchimento / 100)));
        garantirConexao(tipoMapa, mapa);

        int a = 0;
        quantidadeArestas -= mapa.getNumVertices();
        while (a < quantidadeArestas) {
            int peso = gerarNumeroRandom(1, 16);
            int i = gerarNumeroRandom(0, mapa.getNumVertices() - 1);
            int j;
            do {
                j = gerarNumeroRandom(0, mapa.getNumVertices() - 1);
            } while (j == i);

            //if (!mapa.isAdjacent(i, j)) {
            mapa.addEdge(mapa.getVertex(i), mapa.getVertex(j), peso);
            a++;
            //}
        }
    }

    /**
     * Metodo responsavel por gerar o numero de arestas do mapa consoante o
     * preenchimento definido
     *
     * @param preenchimento preenchimento do mapa
     * @param tipoMapa identifica se o mapa é Unidirecional ou Bidirecional
     * @param tipoAresta define se as arestas tem ou mesmo peso ou peso
     * diferente
     * @param mapa mapa no qual vamos gerir as arestas
     */
    public static void gerarArestas(int preenchimento, TipoMapa tipoMapa, TipoAresta tipoAresta, Mapa<Localidade> mapa) {
        if (tipoMapa != TipoMapa.BIDIERCIONAL) {
            throw new UnsupportedOperationException("Tipo de Mapa invalido para este método");
        }
        int quantidadeLocalizacoes = mapa.getNumVertices();
        int quantidadeArestas = (int) Math.round(((quantidadeLocalizacoes * (quantidadeLocalizacoes - 1)) * ((double) preenchimento / 100)));
        garantirConexao(tipoMapa, mapa);

        int a = 0;
        quantidadeArestas -= mapa.getNumVertices() - 1;
        while (a < quantidadeArestas) {
            int peso = gerarNumeroRandom(1, 16);
            int i = gerarNumeroRandom(0, mapa.getNumVertices() - 1);
            int j;
            do {
                j = gerarNumeroRandom(0, mapa.getNumVertices() - 1);
            } while (j == i);
            if (!mapa.isAdjacent(i, j)) {
                mapa.addEdge(mapa.getVertex(i), mapa.getVertex(j), peso);

                if (tipoAresta == TipoAresta.MESMO_PESO) {
                    mapa.addEdge(mapa.getVertex(j), mapa.getVertex(i), peso);
                } else {
                    int peso2 = gerarNumeroRandom(1, 16);
                    mapa.addEdge(mapa.getVertex(j), mapa.getVertex(i), peso2);
                }
                a++;
            }

        }
    }

    /**
     * retorna um valor aleatorio entre o min e o max
     *
     * @param min valor minimo
     * @param max valor maximo
     * @return um valor aleatorio entre o min e o max
     */
    public static int gerarNumeroRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

    /**
     * Metodo usado para garantir se o grafo esta conexo ou não
     *
     * @param tipoMapa
     */
    private static void garantirConexao(TipoMapa tipoMapa, Mapa<Localidade> mapa) {

        int i = 0;
        while (i < mapa.getNumVertices()) {
            Localidade origem = (Localidade) mapa.getVertex(i);
            Localidade destino = (Localidade) mapa.getVertex((i + 1) % mapa.getNumVertices());
            int peso = GestorMapa.gerarNumeroRandom(1, 16);
            // Verifica se não há uma aresta entre os vértices
            if (!mapa.hasEdge(origem, destino)) {
                // Adiciona uma aresta entre os vértices
                mapa.addEdge(origem, destino, peso);

                if (tipoMapa == TipoMapa.BIDIERCIONAL) {
                    mapa.addEdge(destino, origem, peso);
                }
            }
            i++;
        }
    }

    
    /**
     * Metodo responsavel por adicionar um vértice ao mapa
     *
     * @param nome Nome da localidade
     * @param mapa
     */
    public static void adicionarLocalização(String nome, Mapa<Localidade> mapa) {
        Localidade localidade = new Localidade(nome);
        mapa.addVertex(localidade);
    }
    
}
