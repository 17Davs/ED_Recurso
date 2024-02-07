/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import collections.implementations.Network;
import collections.interfaces.NetworkADT;

/**
 *
 * @author David Santos
 */
public class CaminhoMaisCurto extends Estrategia {

    public CaminhoMaisCurto(Bandeira partida, Bandeira meta, NetworkADT<Localidade> grafo) {
        super(partida, meta, grafo.iteratorShortestPath(partida, meta));
    }

    @Override
    public void gerarCaminhoDeVolta(Mapa<Localidade> grafo) {
        //gerar o caminho inverso apartir da bandeira adversária para a base do jogador
        super.setItr(grafo.iteratorShortestPath(super.getAdversaria(), super.getPartida()));
    }

    @Override
    public void atualizarCaminho(Localidade atual, Mapa<Localidade> grafo) {
        //gerar o caminho bandeira/base passada como meta
        super.setItr(grafo.iteratorShortestPath(atual, super.getAdversaria()));
    }

}
