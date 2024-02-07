/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import api.interfaces.IEstrategia;

import java.util.Iterator;

/**
 * Classe abstrata que sera herdada pelos iteradores
 * @author David Santos e Rafael Coronel
 */
public abstract class Estrategia implements IEstrategia {

    /**
     * Variavel onde esta a bandeira do jogador
     */
    private Bandeira partida;
    
    /**
     * Variavel onde esta a bandeira do jogador adversario
     */
    private Bandeira meta;
    
    /**
     * Iterador utilizado para percorrer as localidades
     */
    private Iterator<Localidade> itr;
    private boolean primeiroMovimento;

    /**
     * Construtor da classe Estrategia
     * @param partida Bandeira de partida do jogador
     * @param meta Bandeira de destino do jogador adversário
     * @param iterator Iterador utilizado para percorrer as localidades
     */
    public Estrategia(Bandeira partida, Bandeira meta, Iterator<Localidade> iterator) {
        this.partida = partida;
        this.meta = meta;
        this.itr = iterator;
        primeiroMovimento =true;
    }

    /**
     * Obtém a bandeira de partida do jogador
     * @return Bandeira de partida
     */
    @Override
    public Bandeira getPartida() {
        return partida;
    }

    /**
     * Obtém a bandeira do jogador adversário
     * @return Bandeira do jogador adversário
     */
    @Override
    public Bandeira getAdversaria() {
        return meta;
    }

     /**
     * Obtém o iterador utilizado para percorrer as localidades
     * @return Iterador das localidades
     */
    public Iterator<Localidade> getItr() {
        return itr;
    }

    /**
     * Define o iterador utilizado para percorrer as localidades
     * @param itr Iterador das localidades
     */
    public void setItr(Iterator<Localidade> itr) {
        this.itr = itr;
        primeiroMovimento=true;
    }

    public boolean isPrimeiroMovimento() {
        return primeiroMovimento;
    }

    public void setPrimeiroMovimento(boolean primeiroMovimento) {
        this.primeiroMovimento = primeiroMovimento;
    }

    
    
    
    /**
     * Método responsavel por executar o movimento
     *
     * @return a Localidade para a qual o bot deverá mover
     * @throws FimCaminhoException
     */
    @Override
    public Localidade executarMovimento() throws FimCaminhoException {

        if (itr.hasNext()) {
            Localidade localidade = itr.next();
     
            //em caso de ser a primeira movimentação
            if (primeiroMovimento) {
                if (itr.hasNext()) {
                    localidade = itr.next();
                    primeiroMovimento=false;
                }

            }
            return localidade;
        }

        throw new FimCaminhoException();
    }

}
