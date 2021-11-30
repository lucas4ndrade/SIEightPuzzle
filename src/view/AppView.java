package view;

import model.Board;

public class AppView {
    public void printWelcome(){
        System.out.println("Bem vindo ao programa para busca de soluções do jogo 8Puzzle!");
        System.out.println("Primeiro, selecione o algoritmo de busca que deseja utilizar, ou digite 'i' para detalhes de cada algoritmo.");
    }

    public void printAlgorithmSelection(){
        System.out.println("[1] Custo uniforme.");
        System.out.println("[2] A* simples.");
        System.out.println("[3] A* detalhado.");
        System.out.println("");
        System.out.println("[i] Detalhes.");
        System.out.print("Escolha: ");
    }

    public void printWrongSelection() {
        System.out.println("Esta não era uma das opções disponíveis. Por favor, tente novamente!");
    }

    public void printDetails() {
        System.out.println("Esta busca utiliza um cálculo de prioridade, os estados do tabuleiro (nodos) com menor valor de prioridade serão computados primeiro.");
        System.out.println("");
        System.out.println("Custo uniforme -> Utiliza apenas o número de movimentos necessários para chegar ao nodo atual como valor de prioridade na busca.");
        System.out.println("A* simples -> Utiliza o número de movimentos necessários + o número de peças no lugar errado como valor de prioridade na busca.");
        System.out.println("A* detalhado -> Utiliza o número de movimentos necessários + a soma 'manhattan'** das peças como valor de prioridade");
        System.out.println("");
        System.out.println("** -> Soma manhattan: Número de movimentos que cada peça deve fazer para chegar ao seu lugar correto.");
        System.out.println("Aperte enter para continuar.");
    }

    public void printBoardSelection() {
        System.out.println("Como você deseja iniciar o tabuleiro?");
        System.out.println("[1] Gerar um tabuleiro para mim.");
        System.out.println("[2] Quero indicar um tabuleiro.");
        System.out.print("Escolha: ");
    }

    public void printBoardInput() {
        System.out.println("Entre um tabuleiro separando cada linha por um caractere ':'.");
        System.out.println("Nota: o espaço em branco do tabuleiro é representado pelo maior número.");
        System.out.println("Ex.: A entrada do tabuleiro:");
        System.out.println("[123]\n[456]\n[78 ]");
        System.out.println("Seria 123:456:789");
        System.out.print("Entrada: ");
    }

    public void printInitialBoard(Board board) {
        System.out.println("O seu tabuleiro inicial é:");
        System.out.println(board.toString());
        System.out.println("[1] Começar busca.");
        System.out.println("[2] Voltar.");
    }

    public void printGameEnd(long elapsedTime) {
        System.out.println("O programa achou uma solução em "+elapsedTime+" milisegundos!");
        System.out.println("Como deseja ver a solução?");
        System.out.println("[1] Apenas número de movimentos necessários.");
        System.out.println("[2] Passo a passo detalhado.");
        System.out.println("");
        System.out.print("Escolha: ");
    }

    public void printSimpleSolution(Board board) {
        System.out.println("Segundo o algoritmo selecionado, o tabuleiro pode ser resolvido em "+board.getMoves()+" movimentos.");
    }

    public void printDetailedSolution(Board board) {
        System.out.println("Passo a passso detalhado:");
        Board aux = board;
        int step = board.getMoves();
        while(aux.getPrevious() != null) {
            System.out.println("Passo "+step+":");
            System.out.println(aux.toString());
            aux = aux.getPrevious();
            step--;
        }
    }

    public void printFinish() {
        System.out.println("Gostaria de tentar novamente?");
        System.out.println("[1] Sim.");
        System.out.println("[2] Não.");
        System.out.println("");
        System.out.print("Escolha: ");
    }

    public void printHeapFail() {
        System.out.println("Ops!\nparece que utilizando o algoritmo selecionado, e com a quantidade de memória alocada,\nnão foi possível encontrar uma solução para o tabuleiro :(");
    }

    public void printGenericFail(Exception e) {
        System.out.println("Ops! Algo deu errado ao executar o programa: "+e.getMessage());
        System.out.println(e.getStackTrace());
    }
}
