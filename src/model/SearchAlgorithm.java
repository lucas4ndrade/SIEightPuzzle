package model;
public enum SearchAlgorithm {
    /**
     * represents the A* algorithm using only the number of moves as a heuristic
     */
    UNIFORM_COST,
    /**
     * represents the A* algorithm considering the number of wrong tiles in the heuristic
     */
    A_SIMPLE,
    /**
     * represents the A* algorithm considering the manhatan sum in the heuristic
     */
    A_MANHATAN
}
