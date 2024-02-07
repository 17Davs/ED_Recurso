/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package api.interfaces;

import api.Bandeira;
import api.FimCaminhoException;
import api.Localidade;
import api.Mapa;
import collections.implementations.Network;

/**
 *
 * @author David Santos e Rafael Coronel
 */
public interface IEstrategia {

    /**
     * Método para obter o ponto de partida
     *
     * @return o nome
     */
    public Bandeira getPartida();

    /**
     * Método para obter o ponto de bandeira adversária
     *
     * @return o nome
     */
    public Bandeira getAdversaria();

    /**
     * Método usado para gerar o caminho de volta para a base do jogador Este
     * metodo é executado depois de o bot recolher a Bandeira Adversária
     *
     * @param grafo
     */
    public void gerarCaminhoDeVolta(Mapa<Localidade> grafo);

    /**
     * Método para atualizar o iterador/caminho da estratégia
     *
     * @param atual
     * @param meta
     * @param grafo
     */
    public void atualizarCaminho(Localidade atual, Mapa<Localidade> grafo);

    /**
     * Método para mover com base na estrategia
     *
     * @return a localidade para o bot
     * @throws api.FimCaminhoException
     */
    public Localidade executarMovimento() throws FimCaminhoException;

}
