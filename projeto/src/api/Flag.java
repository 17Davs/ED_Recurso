/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

/**
 *
 * @author David Santos
 */
public class Flag {
    
    /*
    ID para identificar a Flag
    */
    private int id;

    /*
    Variavel responsavel para dar o ID automaticamente 
    */
    private static int proximoID=0;
    
    /*
       Variavel do tipo jogador para identificar qual jogador sera o dono da flag
    */
    private Jogador jogador;

    /*
    Construtor da classe Flag
    
    @param jogador define qual jogador sera o dono da flag
    */
    public Flag(Jogador jogador) {
        this.id= ++proximoID;
        this.jogador = jogador;
    }

    /*
    Retorna o ID da Flag
    */
    public int getId() {
        return id;
    }

    /*
    Define o ID de uma Flag
    
    @param id para armazenar o id da flag
    */
    public void setId(int id) {
        this.id = id;
    }


    /*
    Retorna o jogador que é dono da flag
    */
    public Jogador getJogador() {
        return jogador;
    }

    /*
    Cria o jogador que ira ser o dono de uma flag
    
    @param jogador jogador a ser criado
    */
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

 
    
    
    
}
