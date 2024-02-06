/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import collections.implementations.ArrayUnorderedList;
import java.util.Random;

/**
 *
 * @author David Santos
 */
public class Jogo {

    private Mapa<Localidade> mapa;
    private Jogador jogador1;
    private Jogador jogador2;

    public Jogo() {
    }

    public void importarMapa(String nomeMapa) {
        mapa = ImportExport.importJSON(nomeMapa);
    }

    public void ExportarMapa(String nomeMapa) {
        ImportExport.exportToJSON(nomeMapa, mapa);
    }
    public void mostrarMapa() {
        ImportExport.showMapa(mapa);
    }
    

    public void gerarArestas(int quantidadeArestas, TipoMapa tipoMapa) {
        if (tipoMapa != TipoMapa.BIDIERCIONAL) {
            throw new UnsupportedOperationException("Tipo de Mapa invalido para este método");
        }

        for (int a = 0; a < quantidadeArestas; a++) {

            int peso = gerarNumeroRandom(1, 15);
            int i = gerarNumeroRandom(0, mapa.getNumVertices() - 1);
            int j = gerarNumeroRandom(0, mapa.getNumVertices() - 1);

            mapa.addEdge(mapa.getVertex(i), mapa.getVertex(j), peso);

        }

    }

    public void gerarArestas(int quantidadeArestas, TipoMapa tipoMapa, TipoAresta tipoAresta) {
        if (tipoMapa != TipoMapa.BIDIERCIONAL) {
            throw new UnsupportedOperationException("Tipo de Mapa invalido para este método");
        }

        for (int a = 0; a < quantidadeArestas; a++) {

            int peso = gerarNumeroRandom(1, 15);
            int i = gerarNumeroRandom(0, mapa.getNumVertices() - 1);
            int j = gerarNumeroRandom(0, mapa.getNumVertices() - 1);
            
            mapa.addEdge(mapa.getVertex(i), mapa.getVertex(j), peso);
            
            if (tipoAresta == TipoAresta.MESMO_PESO) {
                mapa.addEdge(mapa.getVertex(j),mapa.getVertex(i), peso);
            } else {
                int peso2 = gerarNumeroRandom(1, 15);
                mapa.addEdge(mapa.getVertex(j),mapa.getVertex(i), peso2);
            }
        }
    }
    
    

    public void adicionarLocalização(String nome) {
        Localidade localidade = new Localidade(nome);
        mapa.addVertex(localidade);
    }

    /**
     * retorna um valor aleatorio entre o min e o max
     *
     * @param min valor minimo
     * @param max valor maximo
     * @return um valor aleatorio entre o min e o max
     */
    public int gerarNumeroRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }
    
    public ArrayUnorderedList<Localidade> getLocalidades(){
        return mapa.getVertexes();
    }
     

    public Bandeira definirBandeira(Localidade localidade){
        Bandeira bandeira = new Bandeira(localidade.getId(), localidade.getNome());
        localidade = bandeira;
        return (Bandeira) localidade;
    }
}
