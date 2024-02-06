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

    public Estrategia(Bandeira partida, Bandeira meta, Iterator<Localidade> iterator) {
        this.partida = partida;
        this.meta = meta;
        this.itr = iterator;
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
            if (localidade.equals((Localidade) partida)) {
                if (itr.hasNext()) {
                    localidade = itr.next();
                }

            }
            return localidade;
        }

        throw new FimCaminhoException();
    }

}
