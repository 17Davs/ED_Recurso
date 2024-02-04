package api;

import api.interfaces.IEstrategia;

/**
 * Classe que representa BOT
 *
 * @author David Santos
 */
public class Bot implements Comparable<Bot> {

    /**
     * Variavel que guarda
     */
    private int id;

    /**
     * Variavel que vai funcionar para todos os bots terem um ID automatico
     */
    private static int proximoID = 0;

    /*
    Variavel da Localidade atual do bot
     */
    private Localidade localAtual;

    /**
     * Variavel da bandeira adversária capturada
     */
    private Bandeira bandeiraAdversaria;

    /**
     * Variavel da estrategia do bot
     */
    private IEstrategia estrategia;

    /**
     * Construtor da classe Bot
     */
    public Bot() {
        id = ++proximoID;
        this.estrategia = null;
        this.localAtual = null;
        this.bandeiraAdversaria = null;
    }

    /**
     * Retorna o ID de um bot
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna a Localidade
     *
     * @return localidde atual
     */
    public Localidade getLocalAtual() {
        return localAtual;
    }

    /**
     * Atualiza a localidade atual para o argumento passado
     *
     * @param localAtual
     */
    public void setLocalAtual(Localidade localAtual) {
        this.localAtual = localAtual;
    }

    /**
     * Retorna a estrategia usada pelo bot
     *
     * @return estrategia
     */
    public IEstrategia getEstrategia() {
        return estrategia;
    }

    /**
     * Retorna a estrategia usada pelo bot
     *
     * @return bandeira adversaria capturada
     */
    public Bandeira getBandeiraAdversaria() {
        return bandeiraAdversaria;
    }

    /**
     * Remove a bandeira adversaria capturada
     *
     */
    public void removerBandeira() {
        this.bandeiraAdversaria = null;
    }

    /**
     * Define a bandeira adversaria capturada
     *
     */
    public void setBandeiraAdversaria(Bandeira bandeiraAdversaria) {
        this.bandeiraAdversaria = bandeiraAdversaria;
    }

    /**
     * Atualizar a estrategia para a estrategeia dada
     *
     * @param estrategia
     */
    public void setEstrategia(IEstrategia estrategia) {
        this.estrategia = estrategia;
    }

    /**
     * Movimentar bot
     */
    public void movimentar() {

        if (estrategia == null) {
            throw new NullPointerException("Estrategia não configurada para bot " + this.id);
        }
        try {
            localAtual = estrategia.executarMovimento();

        } catch (FimCaminhoException ex) {
        }
    }

    @Override
    public int compareTo(Bot o) {
        return Integer.compare(this.getId(), o.getId());
    }

}
