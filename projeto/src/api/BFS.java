package api;

import collections.implementations.Network;
import collections.interfaces.NetworkADT;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Classe MST que herda caracteristicas da classe Estrategia e usada para os bots que
 * quiserem seguir o iterador BFS
 * @author David Santos e Rafael Coronel
 */
public class BFS extends Estrategia {
    
    /**
     * Construtor da classe BFS
     * @param partida Ponto de partida onde o bot irá começar
     * @param meta Ponto de chegada onde o bot quer chegar
     * @param grafo mapa que o bot ira seguir o seu iterador
     */
    public BFS(Bandeira partida, Bandeira meta, Mapa<Localidade> grafo) {
        super(partida, meta, grafo.iteratorBFS(partida));
    }

    /**
     * Este é o metodo responsavel para quando o bot capturar a bandeira 
     * adversária e terá de voltar para a sua base
     * @param grafo mapa onde o bot ira percorrer com o iterador BFS
     */
    @Override
    public void gerarCaminhoDeVolta(Mapa<Localidade> grafo) {
        //gerar o caminho inverso apartir da bandeira adversária para a base do jogador
        super.setItr(grafo.iteratorBFS(super.getAdversaria()));
    }

    /**
     * Este metodo é o responsavel para se no caso o bot ja 
     * estiver a voltar para a base com a bandeira adversária e caia em uma localidade com
     * um bot adversária irá perder a bandeira logo tera de atualizar o caminho para a bandeira adversária outra vez
     * @param atual Localidade Atual
     * @param grafo mapa onde o bot ira percorrer com o iterador BFS
     */
    @Override
    public void atualizarCaminho(Localidade atual, Mapa<Localidade> grafo) {
        //gerar o caminho bandeira/base passada como meta
        super.setItr(grafo.iteratorBFS(atual));
    }

}
