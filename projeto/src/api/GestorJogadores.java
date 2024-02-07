/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import api.interfaces.IEstrategia;
import collections.exceptions.EmptyCollectionException;
import collections.implementations.ArrayUnorderedList;

/**
 *
 * @author David Santos
 */
public class GestorJogadores {
    
      /**
     * Metodo responsavel por adicionar um jogador na lista de jogadores
     *
     * @param jogador Jogador a ser adicionado
     * @param jogadores Lista do jogador
     * @throws UnsupportedOperationException Exceção lançada caso jogadores ja
     * terem sido criado
     */
    public static void adicionarJogador(Jogador jogador, ArrayUnorderedList<Jogador> jogadores) throws UnsupportedOperationException {
        if (jogadores.size() < 2) {
            jogadores.addToRear(jogador);
        } else {
            throw new UnsupportedOperationException("Jogadores já estão criados");
        }
    }

    /**
     * Metodo responsavel pela adição de um bot
     *
     * @param jogador Jogador que é responsavel pelo bot
     * @param bot Bot a ser instanceado
     * @param tipo Define qual a estrategia a ser seguida pelo bot
     * @param jogadores lista de jogadores
     * @param mapa
     */
    public static void adicionarBot(Jogador jogador, Bot bot, TipoEstrategia tipo, ArrayUnorderedList<Jogador> jogadores, Mapa<Localidade> mapa) {
        //associar bot ao jogador e associar estrategia ao bot
        Bandeira inimiga = null;
        try {
            if (jogador.equals(jogadores.first())) {
                inimiga = jogadores.last().getBase();
            } else {
                inimiga = jogadores.first().getBase();
            }
        } catch (EmptyCollectionException e) {
        }

        IEstrategia estrategia;
        switch (tipo) {
            case BFS:
                estrategia = new BFS(jogador.getBase(), inimiga, mapa);
                break;
            case DFS:
                estrategia = new DFS(jogador.getBase(), inimiga, mapa);
                break;
            case CAMINHO_MAIS_CURTO:
                estrategia = new CaminhoMaisCurto(jogador.getBase(), inimiga, mapa);
                break;
            default:
                estrategia = new MST(jogador.getBase(), inimiga, mapa);
                break;
        }
        bot.setLocalAtual(jogador.getBase());
        bot.setEstrategia(estrategia);
        jogador.adicionarBot(bot);

    }
}
