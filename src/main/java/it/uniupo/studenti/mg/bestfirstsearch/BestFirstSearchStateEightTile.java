package it.uniupo.studenti.mg.bestfirstsearch;

import it.uniupo.studenti.mg.util.ArraySearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Questa classe modella uno stato per la ricerca BestFirstSearch per il gioco 8-Tile.
 *
 */

public class BestFirstSearchStateEightTile implements BestFirstSearchState{

    private int[] board = new int[]{1 ,2, 3, 4, 0, 5, 6, 7, 8};
    private BestFirstSearchStateEightTile father = null;

    private int stateCost = 0;

    // Azione che ha portato alla generazione di questo stato, null se questo stato è radice
    private String genAction = null;

    public BestFirstSearchStateEightTile() {
    }

    public BestFirstSearchStateEightTile(int[] board, String genAction,int g) {
        this.board = board;
        this.genAction = genAction;
        this.stateCost = g;
    }

    public BestFirstSearchStateEightTile(
            int[] board, String genAction,int g, BestFirstSearchStateEightTile father){
        this.board = board;
        this.genAction = genAction;
        this.stateCost = g;
        this.father = father;
    }

    @Override
    public List<BestFirstSearchState> getChildren() {
        List<BestFirstSearchState> childrenList = new ArrayList<>();
        BestFirstSearchStateEightTile upChildren = this.moveUp();
        BestFirstSearchStateEightTile downChildren = this.moveDown();
        BestFirstSearchStateEightTile leftChildren = this.moveLeft();
        BestFirstSearchStateEightTile rightChildren = this.moveRight();
        if(upChildren != null) childrenList.add(upChildren);
        if(downChildren != null) childrenList.add(downChildren);
        if(leftChildren != null) childrenList.add(leftChildren);
        if(rightChildren != null) childrenList.add(rightChildren);
        return childrenList;
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
                    this.board.clone(), "UP", this.stateCost + 1, this);
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
                    this.board.clone(), "DOWN", this.stateCost + 1, this);
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
                    this.board.clone(), "LEFT", this.stateCost + 1, this);
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
                    this.board.clone(), "RIGHT", this.stateCost + 1, this);
            children.board[emptyPosition] = children.board[emptyPosition+1];
            children.board[emptyPosition+1] = 0;
            return children;
        }
        return null;
    }

    private static int fromBoardPositionToAbscissa(int boardPosition){
        return boardPosition%3;
    }

    private static int fromBoardPositionToOrdinate(int boardPosition){
        if (boardPosition<3) {return 0;}
        if (boardPosition<6) {return 1;}
        return 2;
    }

    public static int manhattanDistance(BestFirstSearchStateEightTile s,BestFirstSearchStateEightTile g){
        int res = 0;
        int[] sBoard = s.getBoard();
        int[] gBoard = g.getBoard();
        for(int i = 1; i<9; i++){
            int sAbscissa = fromBoardPositionToAbscissa(ArraySearch.findFirstInArray(sBoard, i));
            int sOrdinate = fromBoardPositionToOrdinate(ArraySearch.findFirstInArray(sBoard, i));
            int gAbscissa = fromBoardPositionToAbscissa(ArraySearch.findFirstInArray(gBoard, i));
            int gOrdinate = fromBoardPositionToOrdinate(ArraySearch.findFirstInArray(gBoard, i));
            res += Math.abs((sAbscissa-gAbscissa))+Math.abs((sOrdinate-gOrdinate));
        }
        return res;
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
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Action %s\n", this.genAction));
        sb.append(String.format("g(n) =  %s\n", this.g()));
        String line = "+-----+-----+-----+\n";
        sb.append(line);
        String numberLine = "|  %d  |  %d  |  %d  |\n";
        for(int i = 0; i <= 8; i+=3){
            sb.append(String.format(numberLine, board[i], board[i+1], board[i+2]));
            sb.append(line);
        }
        return sb.toString();
    }


    public int[] getBoard() {
        return board.clone();
    }

    @Override
    public Double g() {
        return (double) this.stateCost;
    }

    @Override
    public BestFirstSearchState getFather() {
        return this.father;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof BestFirstSearchStateEightTile) {
            return Arrays.equals(this.board, ((BestFirstSearchStateEightTile) o).getBoard());
        }else return false;
    }

}