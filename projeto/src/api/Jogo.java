/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import api.interfaces.IEstrategia;
import collections.exceptions.EmptyCollectionException;
import collections.implementations.ArrayUnorderedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Aqui vamos implementar todos os metodos a serem usados na classe menu
 *
 * @author David Santos
 */
public class Jogo {

    private Mapa<Localidade> mapa;
    private ArrayUnorderedList<Jogador> jogadores;
    private int proximoJogador;
    private boolean vitoria = false;
    private static int[][] grafo;

    public Jogo() {
        proximoJogador = 0;
        jogadores = new ArrayUnorderedList<>(2);
        mapa = null;
    }

    public void inicializarMapa(int capacidade) {
        mapa = new Mapa<>(capacidade);
    }

    public void importarMapa(String nomeMapa) {
        mapa = ImportExport.importJSON(nomeMapa);
    }

    public void exportarMapa(String nomeMapa) {
        ImportExport.exportToJSON(nomeMapa, mapa);
    }

    public void mostrarMapa() {
        System.out.println(ImportExport.showMapa(mapa));;
    }

    public void mostrarMapaFromJson(String filePath) {
        System.out.println(ImportExport.showMapaFromJson(filePath));
    }

    public void gerarArestas(int preenchimento, TipoMapa tipoMapa) {
        if (tipoMapa != TipoMapa.UNIDIRECIONAL) {
            throw new UnsupportedOperationException("Tipo de Mapa invalido para este método");
        }

        int quantidadeLocalizacoes = mapa.getNumVertices();
        int quantidadeArestas = (int) Math.round(((quantidadeLocalizacoes * (quantidadeLocalizacoes - 1)) * ((double) preenchimento / 100)));
        garantirConexao(tipoMapa);

        int a = 0;
        quantidadeArestas -= mapa.getNumVertices() - 1;
        while (a < quantidadeArestas) {
            int peso = gerarNumeroRandom(1, 15);
            int i = gerarNumeroRandom(0, mapa.getNumVertices() - 1);
            int j;
            do {
                j = gerarNumeroRandom(0, mapa.getNumVertices() - 1);
            } while (j == i);

            //if (!mapa.isAdjacent(i, j)) {
            mapa.addEdge(mapa.getVertex(i), mapa.getVertex(j), peso);
            a++;
            //}

        }

    }

    public void gerarArestas(int preenchimento, TipoMapa tipoMapa, TipoAresta tipoAresta) {
        if (tipoMapa != TipoMapa.BIDIERCIONAL) {
            throw new UnsupportedOperationException("Tipo de Mapa invalido para este método");
        }
        int quantidadeLocalizacoes = mapa.getNumVertices();
        int quantidadeArestas = (int) Math.round(((quantidadeLocalizacoes * (quantidadeLocalizacoes - 1)) * ((double) preenchimento / 100)));
        garantirConexao(tipoMapa);

        int a = 0;
        quantidadeArestas -= mapa.getNumVertices() - 1;
        while (a < quantidadeArestas) {
            int peso = gerarNumeroRandom(1, 15);
            int i = gerarNumeroRandom(0, mapa.getNumVertices() - 1);
            int j;
            do {
                j = gerarNumeroRandom(0, mapa.getNumVertices() - 1);
            } while (j == i);
            if (!mapa.isAdjacent(i, j)) {
                mapa.addEdge(mapa.getVertex(i), mapa.getVertex(j), peso);

                if (tipoAresta == TipoAresta.MESMO_PESO) {
                    mapa.addEdge(mapa.getVertex(j), mapa.getVertex(i), peso);
                } else {
                    int peso2 = gerarNumeroRandom(1, 15);
                    mapa.addEdge(mapa.getVertex(j), mapa.getVertex(i), peso2);
                }
                a++;
            }

        }
    }

    public void adicionarLocalização(String nome) {
        Localidade localidade = new Localidade(nome);
        mapa.addVertex(localidade);
    }

    public int calculoMaxBots() {
        return (int) Math.round(mapa.size() * 0.20);
    }

    /**
     * retorna um valor aleatorio entre o min e o max
     *
     * @param min valor minimo
     * @param max valor maximo
     * @return um valor aleatorio entre o min e o max
     */
    public int gerarNumeroRandom(int min, int max) {
        Random random = new Random();

        return random.nextInt(max - min) + min;
    }

    public ArrayUnorderedList<Localidade> getLocalidades() {
        return mapa.getVertexes();
    }

    public ArrayUnorderedList<Localidade> getApenasLocalidades() {
        return mapa.getApenasT();
    }

    public Bandeira definirBandeira(Localidade localidade) {
        return mapa.definirBandeira(localidade);
    }

    public void adicionarJogador(Jogador jogador) throws UnsupportedOperationException {
        if (jogadores.size() < 2) {
            jogadores.addToRear(jogador);
        } else {
            throw new UnsupportedOperationException("Jogadores já estão criados");
        }
    }

    public void adicionarBot(Jogador jogador, Bot bot, TipoEstrategia tipo) {
        //associar bot ao jogador e associar estrategia ao bot
        Bandeira inimiga = null;
        try {
            if (jogador.equals(jogadores.first())) {
                inimiga = jogadores.last().getBase();
            } else {
                inimiga = jogadores.first().getBase();
            }
        } catch (EmptyCollectionException e) {
        }

        IEstrategia estrategia;
        switch (tipo) {
            case BFS:
                estrategia = new BFS(jogador.getBase(), inimiga, mapa);
                break;
            case DFS:
                estrategia = new DFS(jogador.getBase(), inimiga, mapa);
                break;
            case CAMINHO_MAIS_CURTO:
                estrategia = new CaminhoMaisCurto(jogador.getBase(), inimiga, mapa);
                break;
            default:
                estrategia = new MST(jogador.getBase(), inimiga, mapa);
                break;
        }
        bot.setLocalAtual(jogador.getBase());
        bot.setEstrategia(estrategia);
        jogador.adicionarBot(bot);

    }

    public void quemComeca() {
        proximoJogador = gerarNumeroRandom(0, 1);

    }

    public void atualizarProxJogador() {
        proximoJogador = (proximoJogador + 1) % jogadores.size();
    }

    public void iniciarJogo() {
        quemComeca();

        while (!vitoria) {
            jogarRonda();
        }
    }

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
            atualizarProxJogador();
        } catch (FimCaminhoException ex) {
            System.out.println("Bot " + botAtual.getId() + " sem caminho --------------------------------------------");
        }
    }

    private void verificarVitoria(Jogador jogadorAtual, Bot botAtual, Jogador adversario) {
        if (botAtual.getLocalAtual().equals(jogadorAtual.getBase()) && botAtual.getBandeiraAdversaria() != null) {
            System.out.println("===========================================");
            System.out.println("|    O jogador " + jogadorAtual.getId() + " venceu!   |");
            System.out.println("|   Bot " + botAtual.getId() + " devolveu a bandeira adversária  |");
            System.out.println("|    para a sua base em " + botAtual.getLocalAtual().getNome() + "   |");
            System.out.println("===========================================");
            vitoria = true; // Define vitoria como verdadeiro se o jogador vencer
        } else {
            verificarRegras(jogadorAtual, botAtual, adversario);
            vitoria = false; //confirmar
        }
    }

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

    private void garantirConexao(TipoMapa tipoMapa) {
        int i = 0;
        while (i < mapa.getNumVertices() - 1) {
            Localidade origem = (Localidade) mapa.getVertex(i);
            Localidade destino = (Localidade) mapa.getVertex(i + 1);
            int peso = gerarNumeroRandom(1, 15);
            // Verifica se não há uma aresta entre os vértices
            if (!mapa.hasEdge(origem, destino)) {
                // Adiciona uma aresta entre os vértices
                mapa.addEdge(origem, destino, peso);
                
                if(tipoMapa==TipoMapa.BIDIERCIONAL){
                     mapa.addEdge(destino, origem, peso);
                }
            }
            i++;
        }
    }

}
