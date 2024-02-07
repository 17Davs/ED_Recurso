/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import collections.implementations.Network;



public class MST extends Estrategia{
    public MST(Bandeira partida, Bandeira meta, Mapa<Localidade> grafo) {
        super(partida, meta, grafo.iteratorMST(partida));
    }

    @Override
    public void gerarCaminhoDeVolta(Mapa<Localidade> grafo) {
        //gerar o caminho inverso apartir da bandeira adversária para a base do jogador
        super.setItr(grafo.iteratorMST(super.getAdversaria()));
    }

    @Override
    public void atualizarCaminho(Localidade atual, Mapa<Localidade> grafo) {
        //gerar o caminho bandeira/base passada como meta
        super.setItr(grafo.iteratorMST(atual));
    }
}
