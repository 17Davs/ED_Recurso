package Main;

import api.Bandeira;
import api.Jogo;
import api.Localidade;
import api.TipoAresta;
import api.TipoMapa;
import collections.implementations.ArrayUnorderedList;
import java.util.Scanner;

/**
 *
 * @author Rafael Coronel
 */
public class Menu {

    private static Jogo jogo;
    private static Scanner scanner = new Scanner(System.in);

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
            System.out.println("| 02. Importar mapa                    |");
            System.out.println("| 0. Sair                              |");
            System.out.println("+--------------------------------------+");
            System.out.println("\n");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    MenuCriarMapa();
                    MenuInial();
                    break;
                case 2:
                    ImportarMapa();
                    MenuInial();
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

    private static void ImportarMapa() {
        System.out.println("Importação de um mapa!");
        System.out.print("Introduza o nome do mapa:");
        String nomeMapa = scanner.next();
        jogo.importarMapa(nomeMapa);
    }

    private static void MenuCriarMapa() {
        //variaveis usadas ao longo do metodo
        String name;
        int preenchimento = 0;
        int quantidadeLocalizacoes = 0;
        int quantidadeArestas = 0;

        do {
            System.out.print("Introduza o número de localizaçoes [Mínimo 10]: ");
            quantidadeLocalizacoes = scanner.nextInt();
        } while (quantidadeLocalizacoes < 10);

        //pedir nome das localizações
        for (int i = 0; i < quantidadeLocalizacoes; i++) {
            System.out.print("Introduza o nome da localizaçao " + (i + 1) + ": ");
            name = scanner.next();
            jogo.adicionarLocalização(name);
        }

        //pedir preenchimento
        System.out.print("Introduza a percentagem de o quanto deseja que o mapa seja conectado:");
        preenchimento = scanner.nextInt();

        quantidadeArestas = (int) Math.round(((quantidadeLocalizacoes * (quantidadeLocalizacoes - 1)) * ((double) preenchimento / 100)));

        System.out.println("Quantidade de Localidades:" + quantidadeLocalizacoes + "  preenchimento: " + preenchimento);
        System.out.println("Calculo de arestas ficou igual a :" + quantidadeArestas);

        pedirTipoMapa(quantidadeArestas);

        //
        exportOrNot();
    }

    private static void pedirTipoMapa(int quantidadeArestas) {
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
                    jogo.gerarArestas(quantidadeArestas, TipoMapa.UNIDIRECIONAL);
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
                                jogo.gerarArestas(quantidadeArestas, TipoMapa.BIDIERCIONAL, TipoAresta.MESMO_PESO);
                                break;
                            case 2:
                                jogo.gerarArestas(quantidadeArestas, TipoMapa.BIDIERCIONAL, TipoAresta.PESO_DIFERENTE);
                                break;
                        }
                    } while (arestaOpcao < 1 && arestaOpcao > 2);
                    break;
            }
        } while (opcaoTipo < 1 && opcaoTipo > 2);
    }

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
                jogo.ExportarMapa(nomeMapa);
                System.out.println();
            }
        } while (opcao < 1 && opcao > 2);
    }

    
    private static void MenuInial(){
        
        int opcao = 0;
        System.out.println("  ======= Loading.... ======  ");
        System.out.println("Capture the flag iniciado com sucesso!");

        do {
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
                break;

                case 2:
                    jogo.mostrarMapa();
                    break;
            }

        } while (opcao != 0);    
    }
    
    private static void MenuPreparacao(){
        
        //bandeira
        
        //jogador 
        
        //bots
        
        
        
    }
    
   
    public void criarFlags(Jogador jogador) {

        ArrayUnorderedList<Localidade> localidades = jogo.getLocalidades();

        System.out.println("======== Definir Base ========");
        System.out.println("Escolha uma base entre as seguintes localidades:");

        for (int i = 0; i < localidades.size(); i++) {
            System.out.println((i + 1) + ". " + localidades.get(i).getNome());
        }

        Bandeira bandeira = null;

        do {
            System.out.print("Introduza o número da localidade para a base: ");
            int opcao = scanner.nextInt();

            if (opcao >= 1 && opcao <= localidades.size()) {
                jogo.definirBandeira(localidades.get(opcao - 1));

                if (base.getFlag() == null) {
                    System.out.println("Base selecionada: " + base.getNome());
                    Flag flag = new Flag(jogador);
                    base.setFlag(flag);
                } else {
                    System.out.println("Erro: A base já possui uma flag. Escolha outra base.");
                    base = null;
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }

        } while (base == null);

        jogador.setBase(base);
        System.out.println("Flag do jogador " + jogador.getId() + " colocada na base " + base.getNome());
        System.out.println("==================================");

    }
    
}
