package api;

import collections.implementations.ArrayUnorderedList;

/**
 * Classe responsavel pelo jogador do Jogo
 * @author Rafael Coronel e David Santos
 */
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
    private ArrayUnorderedList<Bot> bots;

    /**
     * Variavel para armazenar o indice do proximo bot
     */
    private int proximoBot;

    /**
     * Variavel para armazenar o tamanho maximo da lista bots
     */
    private int maxBots;

    /**
     * Bandeira do Jogador
     */
    private Bandeira base;

    /*
    Construtor para criar um jogador  ja com a quantidade de bots especificada
    
    @param numBots parametro que vai guardar quantos bots tera um jogador
     */
    public Jogador(int numBots) {
        this.id = ++proximoID;
        this.maxBots = numBots;
        this.bots = new ArrayUnorderedList<>(maxBots);
        this.base = null;
        this.proximoBot = 0;
    }

    /**
     * Metodo get que retorna o ID de um jogador
     */
    public int getId() {
        return id;
    }

    public int getMaxBots() {
        return maxBots;
    }

    /**
     * Metodo get do tipo Localidade que reorna a Base de um jogador
     * @return 
     */
    public Bandeira getBase() {
        return base;
    }

    /**
     * Metodo set para definir a base de um jogador
     *
     * @param base parametro que sera admitida como a base do jogador
     */
    public void setBase(Bandeira base) {
        this.base = base;
    }

    /**
     * Metodo que retorna os bots da Queue
     *
     * @return lista de bots do jogador
     */
    public ArrayUnorderedList<Bot> getBots() {
        return bots;
    }

    /**
     * Adiciona um bot na lista de bots
     *
     * @param bot sera o bot que ira ser colocado na lista
     */
    public void adicionarBot(Bot bot) {
        bots.addToRear(bot);
    }

    /**
     * Retorna o tamanho ou o numero de bots presentes na lista de bots
     *
     * @return numero de bots da lista
     */
    public int getNumeroBots() {
        return bots.size();
    }

    /**
     * Metodo que retorna o proximo bot a jogar
     *
     * @return proximo bot
     */
    public Bot getNextBot() {
        Bot bot = bots.get(proximoBot);
        proximoBot = (proximoBot + 1) % bots.size();
        return bot;
    }

    /**
     * Metodo equals que compara dois jogadores atraves do id
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jogador other = (Jogador) obj;
        return this.id == other.id;
    }
    
   
}
