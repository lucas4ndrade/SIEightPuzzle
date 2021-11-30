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
    private Board initialBoard;


    public void startSearch() {
        this.view.printWelcome();
        this.computeAlgorithmSelection();

        this.initBoard();

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
    private void initBoard() {
        this.view.printBoardSelection();
        String selection = sc.nextLine();
        switch (selection) {
            case "1":
                this.initialBoard = this.getDefaultBoard();
                break;
            case "2":
                this.initialBoard = this.processBoardInput();
                break;
            default:
                this.view.printWrongSelection();
                this.initBoard();
                break;
        }

        this.getBoardConfirmation();
    }

    private Board getDefaultBoard() {
        /**
         * ESTADO DE TABULEIRO BOM PARA TESTES!!
         * usando custo uniforme -> 1277 milisegundos
         * usando A* simples -> 21 milisegundos
         * usando A* manhattan -> 3 milisegundos
        */
        int[][] defaultBoard = new int[][]{
            {4, 8, 1},
            {7, 2, 3},
            {9, 6, 5}
        };

        return new Board(defaultBoard);
    }

    private Board processBoardInput() {
        this.view.printBoardInput();
        String input = sc.nextLine();

        String[] sepInput = input.split(":");
        int[][] inputBoard = new int[sepInput.length][sepInput.length];
        for (int i = 0; i < sepInput.length; i++) {
            char[] boardLineChars = sepInput[i].toCharArray();
            for (int j = 0; j < boardLineChars.length; j++) {
                char c = boardLineChars[j];
                inputBoard[i][j] = Character.getNumericValue(c);
            }
        }

        return new Board(inputBoard);
    }

    private void getBoardConfirmation() {
        this.view.printInitialBoard(this.initialBoard);
        String selection = sc.nextLine();
        switch (selection) {
            case "1":
                break;
            case "2":
                this.initBoard();
                break;
            default:
                this.view.printWrongSelection();
                this.getBoardConfirmation();
                break;
        }
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
