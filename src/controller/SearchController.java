package controller;

import java.util.PriorityQueue;
import java.util.Scanner;

import model.Board;
import model.BoardComparator;
import model.SearchAlgorithm;
import view.AppView;

public class SearchController {
    private Scanner sc = new Scanner(System.in);
    private AppView view = new AppView();
    private SearchAlgorithm selectedAlgorithm;


    public void startSearch() {
        this.view.printWelcome();
        this.computeAlgorithmSelection();

        Board initialBoard = this.generateInitalBoard();
        this.view.printInitialBoard(initialBoard);
        sc.nextLine();

        PriorityQueue<Board> pq = new PriorityQueue<Board>(new BoardComparator(this.selectedAlgorithm));
        pq.add(initialBoard);

        try {
            long start = System.currentTimeMillis();
            Board goal = new Board();
            while(!pq.isEmpty()) {
                Board currentNode = pq.poll();
                if(currentNode.isGoal()) {
                    goal = currentNode;
                    pq.clear();
                } else {
                    pq.addAll(currentNode.getChilds());
                }
            }
            long finish = System.currentTimeMillis();
            this.computeEndGame(goal, finish-start);
        } catch (OutOfMemoryError e) {
            this.view.printHeapFail();
        } catch (Exception e) {
            this.view.printGenericFail(e);
        }  finally {
            this.finish();
        }
    }

    //TODO randomize this!
    private Board generateInitalBoard() {
        /**
         * ESTADO DE TABULEIRO BOM PARA TESTES!!
         * usando custo uniforme -> 1277 milisegundos
         * usando A* simples -> 21 milisegundos
         * usando A* manhattan -> 3 milisegundos
        */
        int[][] initialState = new int[][]{
            {4, 8, 1},
            {7, 2, 3},
            {9, 6, 5}
        };

        return new Board(initialState);
    }

    private void computeAlgorithmSelection() {
        this.view.printAlgorithmSelection();
        String selection = sc.nextLine();
        switch (selection) {
            case "1":
                this.selectedAlgorithm = SearchAlgorithm.UNIFORM_COST;
                break;
            case "2":
                this.selectedAlgorithm = SearchAlgorithm.A_SIMPLE;
                break;
            case "3":
                this.selectedAlgorithm = SearchAlgorithm.A_MANHATAN;
                break;
            case "i":
                this.view.printDetails();
                sc.nextLine();
                this.computeAlgorithmSelection();
                break;
            default:
                this.view.printWrongSelection();
                this.computeAlgorithmSelection();
                break;
        }
    }

    private void computeEndGame(Board goal, long elapsedTime) {
        this.view.printGameEnd(elapsedTime);
        String selection = sc.nextLine();
        switch (selection) {
            case "1":
                this.view.printSimpleSolution(goal);
                break;
            case "2":
                this.view.printDetailedSolution(goal);
                break;
            default:
                this.view.printWrongSelection();
                this.computeEndGame(goal, elapsedTime);
                break;
        }
    }

    private void finish() {
        this.view.printFinish();
        String selection = sc.nextLine();
        switch (selection) {
            case "1":
                this.startSearch();
                break;
            case "2":
                break;
            default:
                this.view.printWrongSelection();
                this.finish();
                break;
        }
    }
}
