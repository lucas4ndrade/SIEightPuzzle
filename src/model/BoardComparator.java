package model;
import java.util.Comparator;

/**
 * BoardComparator represents the compare function for the board priority queue.
 * Compares boards based on their priority values, depending on witch search algoritm was selected.
 */
public class BoardComparator implements Comparator<Board>{
    private SearchAlgorithm searchAlg;

    public BoardComparator(SearchAlgorithm searchAlg) {
        this.searchAlg = searchAlg;
    }

    public int compare(Board s1, Board s2) {
        switch (this.searchAlg) {
            case UNIFORM_COST:
                return uniformComparison(s1, s2);
            case A_MANHATAN:
                return manhattanComparison(s1, s2);
            case A_SIMPLE:
                return simpleComparison(s1, s2);
        }

        return 0;
    }

    /**
     * Compares two boards using uniform cost comparison
     */
    private int uniformComparison(Board s1, Board s2){
        return s1.getMoves() - s2.getMoves();
    }

    /**
     * Compares two boards using manhattan cost comparison
     */
    private int manhattanComparison(Board s1, Board s2){
        return (s1.getMoves() + s1.manhattanSum()) - (s2.getMoves() + s2.manhattanSum());
    }

    /**
     * Compares two boards using simple cost comparison
     */
    private int simpleComparison(Board s1, Board s2){
        return (s1.getMoves() + s1.wrongTiles()) - (s2.getMoves() + s2.wrongTiles());
    }
}
