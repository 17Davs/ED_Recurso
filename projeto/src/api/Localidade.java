/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

/**
 * Classe responsavel por guardar dados de uma Localidade
 *
 * @author David Santos e Rafael Coronel
 */
public class Localidade {

    /**
     * Guarda o ID de cada Localidade
     */
    private int id;

    /**
     * Variavel static que vai admitir a acda localidade ter automaticamente seu
     * ID
     */
    private static int proximoID = 0;

    /**
     * Variavel vai guardar o nome da Localidade
     */
    private String nome;

    /**
     * Construtor que cria uma localidade com um nome especificado.
     *
     * @param nome nome
     */
    public Localidade(String nome) {
        this.id = ++proximoID;
        this.nome = nome;

    }

    protected Localidade() {

    }

    /**
     * Metodo que retorna o ID da localidade.
     *
     * @return id id
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo usado no construtor de bandeira.
     *
     * @param id id
     */
    protected void setId(int id) {
        if (this instanceof Bandeira) {
            this.id = id;
        }
    }

    /**
     * Metodo que retorna o nome da localidade.
     *
     * @return nome localidade
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo que define o nome da localidade.
     *
     * @param nome localidade
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método que compara duas localidades com base em seus IDs.
     *
     * @param obj objeto
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Localidade)) {
            return false;
        }
        final Localidade other = (Localidade) obj;
        return this.id == other.id;
    }

    /**
     * Método que retorna uma representação em string da localidade.
     * @return string
     */
    @Override
    public String toString() {
        return "Nome: " + nome;
    }

}
