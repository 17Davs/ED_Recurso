package api;

import collections.implementations.Network;
import collections.interfaces.NetworkADT;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author David Santos
 */
public class BFS extends Estrategia {
     public BFS(Bandeira partida, Bandeira meta, NetworkADT<Localidade> grafo) {
        super(partida, meta, grafo.iteratorBFS(partida));
    }

    @Override
    public void gerarCaminhoDeVolta(Network<Localidade> grafo) {
        //gerar o caminho inverso apartir da bandeira adversária para a base do jogador
        super.setItr(grafo.iteratorBFS(super.getAdversaria()));
    }

    @Override
    public void atualizarCaminho(Localidade atual, Network<Localidade> grafo) {
        //gerar o caminho bandeira/base passada como meta
        super.setItr(grafo.iteratorBFS(atual));
    }

}
