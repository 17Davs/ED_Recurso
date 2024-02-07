/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

/**
 * Classe responsavel pelo tratamento das Bandeiras
 * @author David Santos e Rafael Coronel
 */
public class Bandeira extends Localidade {

    /**
       Variavel do tipo jogador para identificar qual jogador é dono da flag
     */
    private Jogador jogador;

    
    /**
     * Construtor da classe Bandeira
     * @param 
     * @param nome 
     */
    public Bandeira(int id, String nome) {
        super();
        setNome(nome);
        setId(id);
        jogador = null;
    }

    /**
    Retorna o jogador que é dono da flag
     */
    public Jogador getJogador() {
        return jogador;
    }

    /**
    Cria o jogador que ira ser o dono de uma flag
    
    @param jogador jogador a ser criado
     */
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

}
