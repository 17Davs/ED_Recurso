/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import api.interfaces.IEstrategia;

import java.util.Iterator;

/**
 *
 * @author David Santos
 */
public abstract class Estrategia implements IEstrategia {

    private Bandeira partida;
    private Bandeira meta;
    private Iterator<Localidade> itr;
    private boolean primeiroMovimento;

    public Estrategia(Bandeira partida, Bandeira meta, Iterator<Localidade> iterator) {
        this.partida = partida;
        this.meta = meta;
        this.itr = iterator;
        primeiroMovimento =true;
    }

    @Override
    public Bandeira getPartida() {
        return partida;
    }

    @Override
    public Bandeira getAdversaria() {
        return meta;
    }

    public Iterator<Localidade> getItr() {
        return itr;
    }

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
