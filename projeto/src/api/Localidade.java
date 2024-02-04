/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import java.time.LocalDateTime;
import org.json.simple.JSONObject;

/**
 *
 * @author David Santos
 */
public class Localidade {

    /*
    Guarda o ID de cada Localidade
     */
    private int id;

    /*
    Variavel static que vai admitir a acda localidade ter automaticamente seu ID
     */
    private static int proximoID = 0;

    /*
    Variavel vai guardar o nome da Localidade
     */
    private String nome;

    /*
    Variavel vai devolver se a Localidade esta ocupada ou nao
     */
    private boolean ocupada;

    /*
    Variavel flag vai ser pre definida por um jogador numa localidade especifica
     */
    private Flag flag;

    /*
    Construtor que cria uma localidade com um nome especificado.
     */
    public Localidade(String nome) {
        this.flag = null;
        this.id = ++proximoID;
        this.nome = nome;
        this.ocupada = false;
    }

    /*
    Metodo que retorna o ID da localidade.
     */
    public int getId() {
        return id;
    }

    /**
     * Método para adicionar uma flag em uma localidade.
     *
     * @param flag a flag a adicionar
     * @throws UnsupportedOperationException se já existe uma flag na localidade
     */
    public void setFlag(Flag flag) throws UnsupportedOperationException {
        if (this.flag != null) {
            throw new UnsupportedOperationException("Já existe uma flag nesta Localização");
        }
        this.flag = flag;
    }

    /*
    Metodo que retorna a flag da localidade.
     */
    public Flag getFlag() {
        return flag;
    }

    /*
    Metodo que retorna o nome da localidade.
     */
    public String getNome() {
        return nome;
    }

    /*
    Metodo que define o nome da localidade.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /*
    Metodo que verifica se a localidade está ocupada.
     */
    public boolean isOcupada() {
        return ocupada;
    }

    /*
    Metodo que define se a localidade está ocupada ou não.
     */
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    /*
    Método que compara duas localidades com base em seus IDs.
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
        final Localidade other = (Localidade) obj;
        return this.id == other.id;
    }

    /*
    Método que retorna uma representação em string da localidade.
     */
    @Override
    public String toString() {
        return "Nome: " + nome;
    }

}
