/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import java.util.Iterator;

/**
 *
 * @author David Santos
 */
public class Bot implements Comparable<Bot> {

    /*
    Variavel iterador do tipo localidade
     */
    private Iterator<Localidade> itr;

    /*
    Variavel de uma Localidade
     */
    private Localidade local;

    /*
    Variavel que guarda 
     */
    private int id;

    /*
    Variavel que vai funcionar para todos os bots terem um ID automatico
     */
    private static int proximoID = 0;

    /*
    Construtor da classe Bot
     */
    public Bot() {
        id = ++proximoID;
        this.itr = null;
        this.local = null;

    }

    /*
    Cria o iterador para um bot
     */
    public void setIterator(Iterator<Localidade> iterator) {
        this.itr = iterator;
    }

    /*
    Retorna o terador do bot
     */
    public Iterator<Localidade> getItr() {
        return itr;
    }

    /*
    Retorna a localidade 
     */
    public Localidade getLocalidade() {
        return local;
    }

    /*
    Retorna o ID de um bot
     */
    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Bot o) {
        return Integer.compare(this.getId(), o.getId());
    }

}
