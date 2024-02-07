/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import collections.implementations.ArrayUnorderedList;

/**
 * Aqui vamos implementar todos os metodos a serem usados na classe menu
 *
 * @author Rafael Coronel e David Santos
 */
public class Jogo {

    /**
     * O mapa do jogo
     */
    private Mapa<Localidade> mapa;

    /**
     * Lista que irá guardar os jogadores que irão jogar
     */
    private ArrayUnorderedList<Jogador> jogadores;

    /**
     * Variavel para determinar o proximo jogador
     */
    private int proximoJogador;

    /**
     * Variavel boolean que ira determinar se ja foi alcançada a vitória ou não
     */
    private boolean vitoria = false;

    /**
     * Construtor da classe jogo que inicia o mapa como nulo e ja adiciona os
     * dois jogadores que vão jogar o jogo
     */
    public Jogo() {
        proximoJogador = 0;
        jogadores = new ArrayUnorderedList<>(2);
        mapa = null;
    }

    
    /**
     * Metodo responsavel por indicar quem começa a jogar
     */
    public void quemComeca() {
        proximoJogador = GestorMapa.gerarNumeroRandom(0, 2);

    }

    /**
     * Atualiza a variavel proximoJogador para saber qual é o proximo jogador a
     * jogar
     */
    public void atualizarProxJogador() {
        proximoJogador = (proximoJogador + 1) % jogadores.size();
    }

    /**
     * Metodo que inicia o jogo e roda por rondas
     */
    public void iniciarJogo() {
        quemComeca();

        while (!vitoria) {
            jogarRonda();
        }
    }

    /**
     * Metodo que joga uma ronda de cada vez movimentando os bots
     */
    public void jogarRonda() {

        if (vitoria) {
            throw new UnsupportedOperationException("Jogo já terminou!");
        }

        Jogador jogadorAtual = jogadores.get(proximoJogador);
        Bot botAtual = jogadorAtual.getNextBot();
        Jogador adversario = jogadores.get((proximoJogador + 1) % 2);
        try {
            botAtual.movimentar();
            verificarVitoria(jogadorAtual, botAtual, adversario); // Verifica a vitória antes de atualizar o próximo jogador         
        } catch (FimCaminhoException ex) {
//            System.out.println("Bot " + botAtual.getId() + " sem caminho --------------------------------------------");
//            botAtual.getEstrategia().atualizarCaminho(botAtual.getLocalAtual(), mapa);
        }
        atualizarProxJogador();
    }

    /**
     * Metodo que verifica se um jogador ganhou ou não
     *
     * @param jogadorAtual Jogador responsavel pelo bot a ser verificado
     * @param botAtual bot a ser verificado
     * @param adversario Jogador adversario
     */
    private void verificarVitoria(Jogador jogadorAtual, Bot botAtual, Jogador adversario) {
        if (botAtual.getLocalAtual().equals(jogadorAtual.getBase())) {
            if (botAtual.getBandeiraAdversaria() != null) {
                System.out.println("===========================================");
                System.out.println("|    O jogador " + jogadorAtual.getId() + " venceu!   |");
                System.out.println("|   Bot " + botAtual.getId() + " devolveu a bandeira adversária  |");
                System.out.println("|    para a sua base em " + botAtual.getLocalAtual().getNome() + "   |");
                System.out.println("===========================================");
                vitoria = true; // Define vitoria como verdadeiro se o jogador vencer
            } else {
                //esta na sua base mas sem bandeira
                System.out.println("|| Jogador " + jogadorAtual.getId() + "---> Bot " + botAtual.getId() + " moveu-se para a sua base " + botAtual.getLocalAtual().getNome() + "(sem bandeira capturada)");
            }
        } else {
            verificarRegras(jogadorAtual, botAtual, adversario);
            vitoria = false; //confirmar
        }
    }

    /**
     * Metodo responsavel pelas verificações das regras do jogo
     *
     * @param jogadorAtual Jogador responsavel pelo bot a ser verificado
     * @param botAtual bot a ser verificado
     * @param adversario Jogador adversario
     */
    private void verificarRegras(Jogador jogadorAtual, Bot botAtual, Jogador adversario) {
        if (botAtual.getLocalAtual().equals(adversario.getBase())) {
            //se o bot chegou na bandeira adversaria
            //capturar a bandeira e gerar caminho de volta da sua estrategia
            botAtual.capturarBandeiraAdversaria(adversario.getBase(), mapa);
            //sinalizar que já capturou
            System.out.println("«« Jogador " + jogadorAtual.getId() + "---> Bot " + botAtual.getId() + " capturou a bandeira adversária em " + botAtual.getBandeiraAdversaria().getNome() + " »»");

        } else {
            /**
             * Cada localização pode acolher mais do que um bot. Se ao entrar
             * uma localização existir um bot adversário com a bandeira da sua
             * equipa, esta deverá voltar para a sua base. No entanto, se um bot
             * com a bandeira entra numa localização, a bandeira permanece na
             * posse do bot. Se coincidirem as duas bandeiras na mesma
             * localização, ambas voltam para a base
             */
            System.out.println("|| Jogador " + jogadorAtual.getId() + "---> Bot " + botAtual.getId() + " moveu-se para a localidade " + botAtual.getLocalAtual().getNome());

            ArrayUnorderedList<Bot> botsAdversarios = adversario.getBots();

            //percorrer lista de bots adversarios para verificar
            //variavel boolean para garantir que só retira a bandeira a 1 bot presente na localidade
            boolean encontrou = false;
            for (int i = 0; i < botsAdversarios.size() && !encontrou; i++) {
                Bot botAdversario = botsAdversarios.get(i);
                //se estiver no mesmo local que o bot atual
                if (botAdversario.getLocalAtual().equals(botAtual.getLocalAtual())) {
                    //se tiver com a bandeira do jogador atual
                    if (botAdversario.getBandeiraAdversaria() != null && botAdversario.getBandeiraAdversaria().equals(jogadorAtual.getBase())) {
                        //esta devera voltar à sua base (ser perdida) e reconfigurar estrategia para ir buscar novamente
                        botAdversario.removerBandeira(mapa);
                        System.out.println("!!!! O Bot adversário " + botAdversario.getId() + " presente na mesma localidade perdeu a a bandeira capturada !!!!");

                        //se o botAtual tambem tem bandeira capturada, ambas são perdidas
                        if (botAtual.getBandeiraAdversaria() != null) {
                            //remover e reconfigurar estrategia
                            botAtual.removerBandeira(mapa);
                            System.out.println("!!!! O Bot " + botAdversario.getId() + "(atual) também perdeu a bandeira capturada !!!!");
                        }
                        encontrou = true;
                    }
                }
            }
        }
    }
    
    
    
    
    /** 
     * Métodos Auxiliares
     */

    /**
     * Cria um mapa já com uma capacidade especificada
     *
     * @param capacidade capacidade que queremos dar ao mapa
     */
    public void inicializarMapa(int capacidade) {
        mapa = new Mapa<>(capacidade);
    }

    /**
     * Metodo usado para importação de um mapa num ficheiro JSON
     *
     * @param nomeMapa nome do mapa JSON
     */
    public void importarMapa(String nomeMapa) {
        mapa = ImportExport.importJSON(nomeMapa);
    }

    /**
     * Metodo para guardar um mapa criado num ficheiro JSON
     *
     * @param nomeMapa nome do mapa a ser guardado
     */
    public void exportarMapa(String nomeMapa) {
        ImportExport.exportToJSON(nomeMapa, mapa);
    }

    /**
     * Metodo responsavel por mostrar as caracteristicas de uma mapa
     */
    public void mostrarMapa() {
        System.out.println(ImportExport.showMapa(mapa));;
    }

    /**
     * Metodo responsavel por mostrar as caracteristicas de uma mapa guardado
     * num ficheiro JSON
     */
    public void mostrarMapaFromJson(String filePath) {
        System.out.println(ImportExport.showMapaFromJson(filePath));
    }

    /**
     * Metodo responsavel por gerar o numero de arestas do mapa consoante o
     * preenchimento definido
     *
     * @param preenchimento preenchimento do mapa
     * @param tipoMapa identifica se o mapa é Unidirecional ou Bidirecional
     */
    public void gerarArestas(int preenchimento, TipoMapa tipoMapa) {
        GestorMapa.gerarArestas(preenchimento, tipoMapa, mapa);
    }

    /**
     * Metodo responsavel por gerar o numero de arestas do mapa consoante o
     * preenchimento definido
     *
     * @param preenchimento preenchimento do mapa
     * @param tipoMapa identifica se o mapa é Unidirecional ou Bidirecional
     * @param tipoAresta define se as arestas tem ou mesmo peso ou peso
     * diferente
     */
    public void gerarArestas(int preenchimento, TipoMapa tipoMapa, TipoAresta tipoAresta) {
        GestorMapa.gerarArestas(preenchimento, tipoMapa, tipoAresta, mapa);
    }

    /**
     * Metodo responsavel por adicionar um vértice ao mapa
     *
     * @param nome Nome da localidade
     */
    public void adicionarLocalização(String nome) {
        GestorMapa.adicionarLocalização(nome, mapa);
    }

    /**
     * Metodo usado para limitar o numero de bots a um jogador consoante o
     * numero de localidades no mapa
     *
     * @return
     */
    public int calculoMaxBots() {
        return (int) Math.round(mapa.size() * 0.20);
    }

    /**
     * Metodo que retorna as localidades do mapa
     *
     * @return retorna todos os vertices do mapa
     */
    public ArrayUnorderedList<Localidade> getLocalidades() {
        return mapa.getVertexes();
    }

    /**
     * Metodo que retorna as localidades do mapa que não possuem bandeira
     *
     * @return retorna todas as localidades sem bandeira
     */
    public ArrayUnorderedList<Localidade> getApenasLocalidades() {
        return mapa.getApenasT();
    }

    /**
     * Metodo responsavel por colocar uma bandeira numa localidade
     *
     * @param localidade Localidade onde colocamos a bandeira
     * @return
     */
    public Bandeira definirBandeira(Localidade localidade) {
        return mapa.definirBandeira(localidade);
    }

    /**
     * Metodo responsavel por adicionar um jogador na lista de jogadores
     *
     * @param jogador Jogador a ser adicionado
     * @throws UnsupportedOperationException Exceção lançada caso jogadores ja
     * terem sido criado
     */
    public void adicionarJogador(Jogador jogador) throws UnsupportedOperationException {
        GestorJogadores.adicionarJogador(jogador, jogadores);
    }

    /**
     * Metodo responsavel pela adição de um bot
     *
     * @param jogador Jogador que é responsavel pelo bot
     * @param bot Bot a ser instanceado
     * @param tipo Define qual a estrategia a ser seguida pelo bot
     */
    public void adicionarBot(Jogador jogador, Bot bot, TipoEstrategia tipo) {
        GestorJogadores.adicionarBot(jogador, bot, tipo, jogadores, mapa);
    }

}
