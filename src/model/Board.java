package model;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Board represents a state on the game board
*/
public class Board {
    private Board previous;
    private int moves;
    private int[][] state;
    private int dimension;

    /**
     * Creates an empty board
     */ 
    public Board() {
    }

    /**
     * Creates a board state from an n by n array of numbers
     */ 
    public Board(int[][] state){
        this.previous = null;
        this.moves = 0;
        this.state = state;
        this.dimension = state.length;
    }

    /**
     * Creates a board state using an n by n array of numbers, the number of moves to get to the state and the previous state.
     */ 
    public Board(int[][] state, int moves, Board previous){
        this.previous = previous;
        this.moves = moves;
        this.state = state;
        this.dimension = state.length;
    }

      /**
     * returns the string value of a board.
     */
    public String toString(){
        String st = "";
        for (int lineIndex = 0; lineIndex < state.length; lineIndex++) {
            int[] line = state[lineIndex];
            for (int colIndex = 0; colIndex < line.length; colIndex++) {
                int tile = line[colIndex];
                if(tile == this.dimension*this.dimension) {
                    st = st + " ";
                } else {
                    st = st + tile;
                }
            }
            st = st + "\n";
        }
        return st;
    }

    /**
     * returns the number of tiles that are out of place in the board
     */
    public int wrongTiles(){
        int nWrong = 0;
        for (int lineIndex = 0; lineIndex < state.length; lineIndex++) {
            int[] line = state[lineIndex];
            for (int colIndex = 0; colIndex < line.length; colIndex++) {
                int tile = line[colIndex];
                int[] goalPosition = this.getTileGoal(tile);

                if(goalPosition[0] != lineIndex || goalPosition[1] != colIndex) {
                    nWrong++;
                }
            }
        }
        return nWrong;
    }

    /**
     * sum of Manhattan distances.
     * (i.e the distance between each of the tiles current position and their goals)
     */
    public int manhattanSum(){
        int sum = 0;
        for (int lineIndex = 0; lineIndex < state.length; lineIndex++) {
            int[] line = state[lineIndex];
            for (int colIndex = 0; colIndex < line.length; colIndex++) {
                //found where the tile currently is!
                int tile = line[colIndex];

                //gets the tile goal position coordinates
                int[] goalPosition = this.getTileGoal(tile);
                int goalLineIndex = goalPosition[0];
                int goalcolIndex = goalPosition[1];

                //gets Manhattan distance
                sum = sum + Math.abs(lineIndex - goalLineIndex) + Math.abs(colIndex - goalcolIndex);
            }
        }
        return sum;
    }

    /** 
     * checks if the current board state is a goal
     */
    public boolean isGoal(){
        return this.wrongTiles() == 0;
    }

    /**  
     * checks if a board is equal another given board
     */
    public boolean equals(Board y){
        int[][] yState = y.getState();
        for (int lineIndex = 0; lineIndex < state.length; lineIndex++) {
            for (int colIndex = 0; colIndex < state[lineIndex].length; colIndex++) {
                if(state[lineIndex][colIndex] != yState[lineIndex][colIndex]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * returns all possible states from the current state
     */
    public ArrayList<Board> getChilds(){
        ArrayList<Board> childs = new ArrayList<Board>();
        //search for the coordinate of the tile with the gratest number (i.e the "blank" tile)
        for (int lineIndex = 0; lineIndex < state.length; lineIndex++) {
            int[] line = state[lineIndex];
            for (int colIndex = 0; colIndex < line.length; colIndex++) {
                int tile = line[colIndex];

                if(tile == dimension*dimension) {
                    //check every possible move from the current position,
                    //if its a valid move, add resultant state to child array.
                    if(lineIndex + 1 <= (dimension-1)) {
                        int[][] aux = this.deepCopy();
                        aux[lineIndex][colIndex] = this.state[lineIndex+1][colIndex];
                        aux[lineIndex+1][colIndex] = tile;
                        childs.add(new Board(aux, this.moves+1, this));
                    }
                    if(lineIndex - 1 >= 0) {
                        int[][] aux = this.deepCopy();
                        aux[lineIndex][colIndex] = this.state[lineIndex-1][colIndex];
                        aux[lineIndex-1][colIndex] = tile;
                        childs.add(new Board(aux, this.moves+1, this));
                    }
                    if(colIndex + 1 <= (dimension-1)) {
                        int[][] aux = this.deepCopy();
                        aux[lineIndex][colIndex] = this.state[lineIndex][colIndex+1];
                        aux[lineIndex][colIndex+1] = tile;
                        childs.add(new Board(aux, this.moves+1, this));
                    }
                    if(colIndex - 1 >= 0) {
                        int[][] aux = this.deepCopy();
                        aux[lineIndex][colIndex] = this.state[lineIndex][colIndex-1];
                        aux[lineIndex][colIndex-1] = tile;
                        childs.add(new Board(aux, this.moves+1, this));
                    }
                    return childs;
                }
            }
        }
        return childs;
    }

    /**
     * returns the number of moves that where necessary to get to this state.
     */
    public int getMoves() {
        return this.moves;
    }

    /**
     * returns the previous move
     */
    public Board getPrevious() {
        return this.previous;
    }

    /**
     * returns the current state (i.e the n-by-n array representing the board)
     */
    public int[][] getState() {
        return this.state;
    }

    /**
     * Find the tile goal coordinates (i.e the line and collum index on the board bidimentional array)
     * @param n the tile number
     * @return an array containing the tile goal coordinates ([0] line coordinate, [1] collum coordinate)
     */
    private int[] getTileGoal(int tile) {
        int[] coordinates = new int[2];

        int module = tile%dimension;
        int div = tile/dimension;
        if(module == 0) {
            coordinates[0] = div - 1;
            coordinates[1] = dimension - 1;
        } else {
            coordinates[0] = div;
            coordinates[1] = module - 1;
        }

        return coordinates;
    }

    /**
     * Returns a deep copy of the current state
     */
    private int[][] deepCopy() {
        final int[][] copy = new int[this.state.length][];
        for (int i = 0; i < this.state.length; i++) {
            copy[i] = Arrays.copyOf(this.state[i], this.state[i].length);
        }
        return copy;
    }
}
