/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

/**
 *
 * @author David Santos
 */
public class Bandeira extends Localidade {
    
    /*
       Variavel do tipo jogador para identificar qual jogador é dono da flag
    */
    private Jogador jogador;

    /*
    Construtor da classe Bandeira
    
    @param jogador define qual jogador sera o dono da flag
    */
    public Bandeira(int id, String nome) {
        super();
        setNome(nome);
        setId(id);   
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
        final Bandeira other = (Bandeira) obj;
        return super.getId()==other.getId();
    }

 
    
    
    
}
