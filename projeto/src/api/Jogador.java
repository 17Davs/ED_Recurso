/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import collections.implementations.ArrayOrderedList;


public class Jogador {

    /*
    Variavel que ira guardar o ID de um Jogador
     */
    private int id;

    /*
    Variavel static que usamos para fazer com que o ID seja atribuido de forma automatica
     */
    private static int proximoID = 0;

    /*
    Uma lista de bots que armazenara os bots do jogador
     */
    private ArrayOrderedList<Bot> bots;

    /*
    Local onde a Flag de um Jogador sera colocada
     */
    private Localidade base;

    /*
    Construtor para criar um jogador  ja com a quantidade de bots especificada
    
    @param numBots parametro que vai guardar quantos bots tera um jogador
     */
    public Jogador(int numBots) {
        this.id = ++proximoID;
        this.bots = new ArrayOrderedList<>(numBots);
        this.base = null;
    }

    /*
    Metodo get que retorna o ID de um jogador
     */
    public int getId() {
        return id;
    }

    /*
    Metodo get do tipo Localidade que reorna a Base de um jogador
     */
    public Localidade getBase() {
        return base;
    }

    /*
    Metodo set para definir a base de um jogador
    
    @param base parametro que sera admitida como a base do jogador
     */
    public void setBase(Localidade base) {
        this.base = base;
    }

    /*
    Metodo que retorna os bots da Queue
     */
    public ArrayOrderedList<Bot> getBots() {
        return bots;
    }

    /*
    *Adiciona um bot na lista de bots   
    @param bot sera o bot que ira ser colocado na lista
     */
    public void adicionarBot(Bot bot) {
        bots.add(bot);
    }

    /*
    * Retorna o tamanho ou o numero de bots presentes na lista de bots
     */
    public int getNumeroBots() {
        return bots.size();
    }
    
    
    

//    /*
//    Metodo que faz um dequeue de um bot que estava na Queue assim retornando o bot seguinte ao que foi retirado
//     */
//    public Bot getNextBot() throws EmptyCollectionException {
//        return bots.dequeue();
//    }

//    /*
//    Este metodo vai ser usado na classe GameFacilities e é o responsavel por atribuir um iterador ao bot e coloca-lo na Queue de bots
//    
//    @param bot sera o bot que ira ser colocado na Queue
//    @param iterator sera o iterador que o bot ira utilizar
//     */
//    public void iteratorToBot(Bot bot, Iterator<Localidade> iterator) throws EmptyCollectionException {
//        bot.setIterator(iterator);
//
//        bots.enqueue(bot);
//
//    }
}
