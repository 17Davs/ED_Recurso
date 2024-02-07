package Main;

import api.BFS;
import api.Bandeira;
import api.Bot;
import api.Jogador;
import api.Jogo;
import api.Localidade;
import api.TipoAresta;
import api.TipoEstrategia;
import api.TipoMapa;
import api.interfaces.IEstrategia;
import collections.implementations.ArrayUnorderedList;
import collections.exceptions.EmptyCollectionException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * Classe que representa o menu principal do jogo Capture the Flag. Este menu
 * permite criar mapas, importar mapas, visualizar o mapa, preparar jogadores e
 * bots, e iniciar o jogo.
 *
 * @author Rafael Coronel
 */
public class Menu {

    private static Jogo jogo;
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Mostra o menu principal do jogo, permitindo ao jogador escolher entre
     * criar mapa, importar mapa, ou sair do jogo.
     */
    private static void mostrarMenuJogo() {
        boolean exit = false;
        int option = 0;

        do {
            System.out.println("\n");
            System.out.println("+--------------------------------------+");
            System.out.println("|              MENU                    |");
            System.out.println("+--------------------------------------+");
            System.out.println("| Seleciona uma opção:                 |");
            System.out.println("|                                      |");
            System.out.println("| 01. Criar mapa                       |");
            System.out.println("| 02. Mostrar/Importar mapa            |");
            System.out.println("| 0. Sair                              |");
            System.out.println("+--------------------------------------+");
            System.out.println("\n");

            System.out.print("Introduza sua opcao: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    jogo = new Jogo();
                    MenuCriarMapa();
                    MenuInial();
                    break;
                case 2:
                    jogo = new Jogo();
                    mostrarMapa();
                    break;
                case 0:
                    System.out.println("saindo do jogo...");
                    exit = true;
                    break;
                default:
                    System.out.println("Opcao invalida introduza outra vez!");
                    break;
            }

        } while (!exit);
    }

    /**
     * Importa um mapa a partir de um arquivo JSON com o nome especificado.
     *
     * @param nomeMapa Nome do mapa a ser importado.
     */
    private static void ImportarMapa(String nomeMapa) {
        System.out.println("Importando mapa + " + nomeMapa + "!");
        jogo.importarMapa(nomeMapa);
    }

    /**
     * Mostra o menu para criar um novo mapa.
     */
    private static void MenuCriarMapa() {
        //variaveis usadas ao longo do metodo
        String name;
        int preenchimento = 0;
        int quantidadeLocalizacoes = 0;

        do {
            System.out.print("Introduza o número de localizaçoes [Mínimo 10]: ");
            quantidadeLocalizacoes = scanner.nextInt();
        } while (quantidadeLocalizacoes < 10);

        jogo.inicializarMapa(quantidadeLocalizacoes);

        //pedir nome das localizações
        for (int i = 0; i < quantidadeLocalizacoes; i++) {
            System.out.print("Introduza o nome da localizaçao " + (i + 1) + ": ");
            name = scanner.next();
            jogo.adicionarLocalização(name);
        }

        //pedir preenchimento
        System.out.print("Introduza a percentagem de o quanto deseja que o mapa seja conectado:");
        preenchimento = scanner.nextInt();

        System.out.println("Quantidade de Localidades:" + quantidadeLocalizacoes + "  preenchimento: " + preenchimento);

        pedirTipoMapa(preenchimento);

        exportOrNot();
    }

    /**
     * Pede ao usuário para escolher o tipo de mapa a ser gerado com base na
     * quantidade de arestas especificada.
     *
     * @param quantidadeArestas Quantidade de arestas no mapa.
     */
    private static void pedirTipoMapa(int preenchiemto) {
        //pedirTipoDeCaminho
        int opcaoTipo = 0;
        do {
            System.out.println("========   Tipo de Mapa   ========");
            System.out.println("         1. Unidirecional         ");
            System.out.println("          2. Bidirecional         ");
            System.out.println("==================================");

            System.out.print("Introduza sua opcao por favor:");
            opcaoTipo = scanner.nextInt();

            switch (opcaoTipo) {
                case 1:

                    jogo.gerarArestas(preenchiemto, TipoMapa.UNIDIRECIONAL);
                    break;

                case 2:
                    int arestaOpcao = 0;
                    do {
                        System.out.println();
                        System.out.println("========  Tipo de Aresta  ========");
                        System.out.println("      1. Mesmo peso nas arestas   ");
                        System.out.println("   2. Pesos diferente nas arestas ");
                        System.out.println("==================================");

                        System.out.print("Introduza sua opcao por favor:");
                        arestaOpcao = scanner.nextInt();

                        switch (arestaOpcao) {
                            case 1:
                                jogo.gerarArestas(preenchiemto, TipoMapa.BIDIERCIONAL, TipoAresta.MESMO_PESO);
                                break;
                            case 2:
                                jogo.gerarArestas(preenchiemto, TipoMapa.BIDIERCIONAL, TipoAresta.PESO_DIFERENTE);
                                break;
                        }
                    } while (arestaOpcao < 1 || arestaOpcao > 2);
                    break;
            }
        } while (opcaoTipo < 1 || opcaoTipo > 2);
    }

    /**
     * Pergunta ao usuário se deseja exportar o mapa gerado e jogar, ou apenas
     * jogar sem exportar.
     */
    private static void exportOrNot() {
        int opcao = 0;

        do {
            System.out.println();
            System.out.println("============  Export  ============");
            System.out.println("     1. Exportar o mapa e jogar   ");
            System.out.println("2. Apenas jogar sem guardar o mapa");
            System.out.println("==================================");

            System.out.print("Introduza sua opcao por favor:");
            opcao = scanner.nextInt();

            if (opcao == 1) {
                System.out.print("Indica o nome do mapa: ");
                String nomeMapa = scanner.next();
                String currentWorkingDir = System.getProperty("user.dir");
                jogo.exportarMapa(currentWorkingDir + "/src/Files/" + nomeMapa + ".json");
                System.out.println();
            }
        } while (opcao < 1 || opcao > 2);
    }

    /**
     * Exibe o menu inicial do jogo, permitindo que o jogador escolha entre
     * padronizar bots e jogar, visualizar o mapa, ou sair do jogo.
     */
    private static void MenuInial() {
        int opcao = 0;
        System.out.println("  ======= Loading.... ======  ");
        System.out.println("Capture the flag iniciado com sucesso!");

        do {
            try {
                System.out.println();
                System.out.println("============ Menu do Jogo =============");
                System.out.println("      1. Padronizar Bots e Jogar       ");//por retirar
                System.out.println("         2. Visualizar o mapa          ");
                System.out.println("           0. Sair do jogo             ");
                System.out.println("=======================================");

                System.out.println("Introduza sua opcao: ");
                opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        MenuPreparacao();
                        oJogo();
                        opcao = 0;
                        break;

                    case 2:
                        jogo.mostrarMapa();
                        break;
                }
            }catch(InputMismatchException ex){
                System.out.println("Introduza numero: ");
            }

        } while (opcao != 0);
    }

    /**
     * Mostra o mapa e permite ao usuário escolher se deseja importá-lo para o
     * jogo.
     */
    private static void mostrarMapa() {
        int opcao;
        String currentWorkingDir = System.getProperty("user.dir");
        System.out.print("Introduza o nome do mapa a visualizar: ");
        String nMapa = scanner.next();
        jogo.mostrarMapaFromJson(currentWorkingDir + "/src/Files/" + nMapa + ".json");

        System.out.println("Deseja importar esse mapa ou nao :");
        do {
            System.out.println();
            System.out.println("=========================");
            System.out.println("      1. SIM             ");//por retirar
            System.out.println("      2. NAO             ");
            System.out.println("=========================");

            System.out.println("Introduza sua opcao: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    ImportarMapa(currentWorkingDir + "/src/Files/" + nMapa + ".json");
                    MenuInial();
                    break;
                case 2:
                    mostrarMenuJogo();
                    break;
            }

        } while (opcao != 0);
    }

    /**
     * Exibe o menu de preparação do jogo, onde os jogadores são criados e
     * configurados.
     */
    private static void MenuPreparacao() {
        Jogador jogodor1 = criarJogador();
        Jogador jogodor2 = criarJogador();

        //bots
        padronizarBots(jogodor1);
        padronizarBots(jogodor2);
    }

    /**
     * Padroniza os bots para um jogador.
     *
     * @param jogador Jogador para o qual os bots estão sendo padronizados.
     */
    private static void padronizarBots(Jogador jogador) {
        System.out.println("===========================================");
        System.out.println("|         Menu de Padronização de Bots    |");
        System.out.println("===========================================");
        System.out.println("| Jogador: " + jogador.getId());
        System.out.println("-------------------------------------------");

        for (int i = 0; i < jogador.getMaxBots(); i++) {
            criarBot(jogador);
        }
    }

    /**
     * Cria um novo jogador para o jogo.
     *
     * @return O jogador criado.
     */
    private static Jogador criarJogador() {
        int maxBots = jogo.calculoMaxBots();
        int numBots;
        do {
            System.out.print("Introduza o número de bots para jogador [Valor Máximo " + maxBots + " (20% das localidade)]: ");
            numBots = scanner.nextInt();
        } while (numBots > maxBots && numBots < 1);

        Jogador jogador = new Jogador(numBots);
        criarBandeira(jogador);

        jogo.adicionarJogador(jogador);
        return jogador;
    }

    /**
     * Cria uma bandeira para um jogador.
     *
     * @param jogador O jogador para o qual a bandeira está sendo criada.
     */
    private static void criarBandeira(Jogador jogador) {
        ArrayUnorderedList<Localidade> localidades = jogo.getApenasLocalidades();

        System.out.println("======== Definir Base ========");
        System.out.println("Escolha uma base entre as seguintes localidades:");

        for (int i = 0; i < localidades.size(); i++) {
            System.out.println((i + 1) + ". " + localidades.get(i).getNome());
        }
        int opcao = 0;
        do {
            System.out.print("Introduza o número da localidade para a base: ");
            opcao = scanner.nextInt();

            if (opcao >= 1 && opcao <= localidades.size()) {

                jogador.setBase(jogo.definirBandeira(localidades.get(opcao - 1)));
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao < 1 || opcao > localidades.size());

        System.out.println("Bandeira do jogador " + jogador.getId() + " colocada na base " + jogador.getBase().getNome());
        System.out.println("==================================");
    }

    /**
     * Cria um bot para um jogador com base na estratégia escolhida pelo
     * usuário.
     *
     * @param jogador O jogador para o qual o bot está sendo criado.
     */
    private static void criarBot(Jogador jogador) {
        Bot bot = new Bot();
        int opcao;
        do {
            System.out.println("===========================================");
            System.out.println("| Estratégia para o bot " + bot.getId() + "{Jogador " + jogador.getId() + "]:");
            System.out.println("-------------------------------------------");
            System.out.println("| Opções de Estratégia:                 |");
            System.out.println("|   1. Travessia por largura (BFS)        |");
            System.out.println("|   2. Travessia por profundidade (DFS)   |");
            System.out.println("|   3. Shortest Path                      |");
            System.out.println("|   4. Árvore geradora Mínima             |");
            System.out.println("-------------------------------------------");
            System.out.println("===========================================");

            System.out.println("Introduza sua opcao: ");
            opcao = scanner.nextInt();

            if (opcao < 1 || opcao > 4) {
                System.out.println(" Opcão Inválida ");
            }
        } while (opcao < 1 || opcao > 4);

        switch (opcao) {
            case 1:
                System.out.println("Foi escolhido a travessia BFS para o bot " + bot.getId());
                jogo.adicionarBot(jogador, bot, TipoEstrategia.BFS);
                break;

            case 2:
                System.out.println("Foi escolhido a travessia DFS para o bot" + bot.getId());
                jogo.adicionarBot(jogador, bot, TipoEstrategia.DFS);
                break;
            case 3:
                System.out.println("Foi escolhido a travessia Shortest Path para o bot" + bot.getId());
                jogo.adicionarBot(jogador, bot, TipoEstrategia.CAMINHO_MAIS_CURTO);
                break;
            default:
                System.out.println("Foi escolhido a travessia Árvore geradora de custo minimo para o bot" + bot.getId());
                jogo.adicionarBot(jogador, bot, TipoEstrategia.MST);
                break;
        }
    }

    /**
     * Inicia o jogo, exibindo uma mensagem indicando que o jogo foi iniciado.
     */
    private static void oJogo() {
        System.out.println("╔═════════════════════════════════════════════════╗");
        System.out.println("║     JOGO Capture the Flag Iniciado    ║");
        System.out.println("╚═════════════════════════════════════════════════╝");

        jogo.iniciarJogo();

        System.out.println("╔═════════════════════════════════════════════════╗");
        System.out.println("║     JOGO Capture the Flag Terminado   ║");
        System.out.println("╚═════════════════════════════════════════════════╝");

    }

    public static void main(String[] args) {
        Menu.mostrarMenuJogo();
    }

}
