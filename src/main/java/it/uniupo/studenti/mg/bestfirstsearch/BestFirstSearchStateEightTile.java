package it.uniupo.studenti.mg.bestfirstsearch;

import it.uniupo.studenti.mg.bestfirstsearch.it.uniupo.studenti.mg.util.ArraySearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Questa classe modella uno stato per la ricerca BestFirstSearch per il gioco 8 Tile.
 *
 */

public class BestFirstSearchStateEightTile implements
        BestFirstSearchState,
        Comparable<BestFirstSearchStateEightTile>{

    private int[] board = new int[]{1 ,2, 3, 4, 0, 5, 6, 7, 8};
    private String defaultHeuristic = "H1";

    private String selectedHeuristic = defaultHeuristic;

    // Azione che ha portato alla generazione di questo stato, null se questo stato è radice
    private String genAction = null;

    public BestFirstSearchStateEightTile() {
    }

    public BestFirstSearchStateEightTile(int[] board, String genAction) {
        this.board = board;
        this.genAction = genAction;
    }

    @Override
    public List<BestFirstSearchState> getChildren() {
        List<BestFirstSearchState> childrenList = new ArrayList<>();
        BestFirstSearchState upChildren = this.moveUp();
        BestFirstSearchState downChildren = this.moveDown();
        BestFirstSearchState leftChildren = this.moveLeft();
        BestFirstSearchState rightChildren = this.moveRight();
        if(upChildren != null) childrenList.add(upChildren);
        if(downChildren != null) childrenList.add(downChildren);
        if(leftChildren != null) childrenList.add(leftChildren);
        if(rightChildren != null) childrenList.add(rightChildren);
        return childrenList;
    }

    @Override
    public Double evaluationFunction() {
        return null;
    }

    @Override
    public String selectHeuristic(String e) {
        return null;
    }

    /**
     * Muove la casella vuota in alto, se possibile
     * @return il nuovo stato del gioco, null se la mossa non è possibile
     */
    public BestFirstSearchStateEightTile moveUp (){
        //Se il vuoto è nella prima riga in alto non può muovere in alto
        int emptyPosition = ArraySearch.findFirstInArray(this.board, 0);
        if(emptyPosition > 2){
            //vuoto non sulla prima riga
            BestFirstSearchStateEightTile children = new BestFirstSearchStateEightTile(
                    this.board.clone(), "UP");
            children.board[emptyPosition] = children.board[emptyPosition-3];
            children.board[emptyPosition-3] = 0;
            return children;
        }
        return null;
    }

    public BestFirstSearchStateEightTile moveDown() {
        //Se il vuoto è nell'ultima riga in basso non può muovere in alto
        int emptyPosition = ArraySearch.findFirstInArray(this.board, 0);
        if(emptyPosition < 6){
            //vuoto non sull' ultima riga
            BestFirstSearchStateEightTile children = new BestFirstSearchStateEightTile(
                    this.board.clone(), "DOWN");
            children.board[emptyPosition] = children.board[emptyPosition+3];
            children.board[emptyPosition+3] = 0;
            return children;
        }
        return null;
    }

    public BestFirstSearchStateEightTile moveLeft() {
        //Se il vuoto è nella colonna più a sinistra non possiamo muoverlo ulteriormente
        int emptyPosition = ArraySearch.findFirstInArray(this.board, 0);
        if((emptyPosition % 3) != 0){
            //vuoto non sulla colonna piu a sinistra
            BestFirstSearchStateEightTile children = new BestFirstSearchStateEightTile(
                    this.board.clone(), "LEFT");
            children.board[emptyPosition] = children.board[emptyPosition-1];
            children.board[emptyPosition-1] = 0;
            return children;
        }
        return null;
    }

    //TODO: move right
    public BestFirstSearchStateEightTile moveRight() {
        //Se il vuoto è nella colonna più a destra non possiamo muoverlo ulteriormente
        int emptyPosition = ArraySearch.findFirstInArray(this.board, 0);
        if((emptyPosition % 3) != 2){
            //vuoto non sulla colonna piu a destra
            BestFirstSearchStateEightTile children = new BestFirstSearchStateEightTile(
                    this.board.clone(), "RIGHT");
            children.board[emptyPosition] = children.board[emptyPosition+1];
            children.board[emptyPosition+1] = 0;
            return children;
        }
        return null;
    }

    /**
     * Override per l'interfaccia comparable. Ci serve per sfruttare la classe della priority queue
     * Valuta l' evaluationFunction
     *
     * @param s the object to be compared.
     * @return
     */
    @Override
    public int compareTo(BestFirstSearchStateEightTile s) {
        return this.evaluationFunction().compareTo(s.evaluationFunction());
    }

    /**
     * @return la rappresentazione del gioco:
     * <p><blockquote><pre>
     *  +-----+-----+-----+<br>
     *  |  1  |  2  |  3  |<br>
     *  +-----+-----+-----+<br>
     *  |  4  |     |  6  |<br>
     *  +-----+-----+-----+<br>
     *  |  7  |  8  |  9  |<br>
     *  +-----+-----+-----+<br>
     *  </pre></blockquote></p>
     */
    @Override
    public String toString(){
        System.out.printf("Action %s\n", this.genAction);
        String line = "+-----+-----+-----+\n";
        String numberLine = "|  %d  |  %d  |  %d  |\n";
        StringBuffer sb = new StringBuffer(line);
        for(int i = 0; i <= 8; i+=3){
            sb.append(String.format(numberLine, board[i], board[i+1], board[i+2]));
            sb.append(line);
        }
        return sb.toString();
    }

    public int[] getBoard() {
        return board;
    }

}