package it.uniupo.studenti.mg.adversarialsearch.minimax;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;

public class GameStateTicTacToeImpl implements GameState, Comparable<GameStateTicTacToeImpl> {

    @Override
    public int compareTo(GameStateTicTacToeImpl o) {
        return Double.compare(this.getEval(), o.getEval());
    }

    public enum Player {
        MAX,
        MIN
    }

    private final int depth;

    private final char[][] board ;

    // Giocatore che ha diritto a muovere in questo stato
    private final Player playerWhoMoves;

    //euristica valutazione dello stato
    ToDoubleFunction<char[][]> evalFunction = (ticTacToeBoard) ->
        {
            int eval = 0;
            char[][] b = ticTacToeBoard.clone();
            List<char[]> rows = GameStateTicTacToeImpl.getBoardRows(b);
            for(char[] row : rows ){
                if(GameStateTicTacToeImpl.countSymbolInRow('x',row)==3){eval+=100;}
                else if((GameStateTicTacToeImpl.countSymbolInRow('x', row) == 2) &&
                        (GameStateTicTacToeImpl.countSymbolInRow(' ', row) == 1)){eval+=10;}
                else if((GameStateTicTacToeImpl.countSymbolInRow('x', row) == 1) &&
                        (GameStateTicTacToeImpl.countSymbolInRow(' ', row) == 2)){eval+=1;}
                if(GameStateTicTacToeImpl.countSymbolInRow('o',row)==3){eval-=100;}
                else if((GameStateTicTacToeImpl.countSymbolInRow('o', row) == 2) &&
                        (GameStateTicTacToeImpl.countSymbolInRow(' ', row) == 1)){eval-=10;}
                else if((GameStateTicTacToeImpl.countSymbolInRow('o', row) == 1) &&
                        (GameStateTicTacToeImpl.countSymbolInRow(' ', row) == 2)){eval-=1;}
            }
            return eval;
        };

    public GameStateTicTacToeImpl(char[][] board, Player player, int depth) {
        this.board = board;
        this.playerWhoMoves = player;
        this.depth = depth;
    }

    public ToDoubleFunction<char[][]> getEvalFunction() {
        return evalFunction;
    }

    public char[][] getBoard() {
        return board.clone();
    }

    @Override
    public List<GameState> getActions() {
        char symbol = 'x';
        char[][] newBoard;
        Player newBoardPlayer = Player.MIN;
        List<GameState> actions = new ArrayList<>();
        switch (playerWhoMoves){
            case MAX:
                break;
            case MIN:
                symbol = 'o';
                newBoardPlayer = Player.MAX;
                break;
        }
        for(int row=0; row<board.length; row++){
            for(int column=0; column<board[row].length; column++){
                if(board[row][column]==' '){
                    newBoard = deepCopyCharMatrix(board);
                    newBoard[row][column] = symbol;
                    actions.add(new GameStateTicTacToeImpl(newBoard, newBoardPlayer, depth+1));
                }
            }
        }
        return actions;
    }

    private static char[][] deepCopyCharMatrix(char[][] matrix) {
        if (matrix == null)
            return null;
        char[][] result = new char[matrix.length][];
        for (int r = 0; r < matrix.length; r++) {
            result[r] = matrix[r].clone();
        }
        return result;
    }

    /**
     *
     * @return true se lo stato è terminale (c'è un tris oppure non e piu possibile eseguire altre azioni)
     */
    @Override
    public boolean terminalTest() {
        List<char[]> rows = getBoardRows(board);
        //controllo se abbiamo dei tris, nel caso è uno stato terminale
        for(char[] row : rows){
            if((GameStateTicTacToeImpl.countSymbolInRow('x', row)==3)||
                    (GameStateTicTacToeImpl.countSymbolInRow('o', row)==3))
            {return true;}
        }
        //controllo se abbiamo spazi vuoti, nel caso ne trovassi almeno uno allora non è uno stato terminale
        for(char[] row : rows){
            if(GameStateTicTacToeImpl.countSymbolInRow(' ', row)>0) {return false;}
        }
        //non ho trovato ne tris, ne spazi vuoti, quindi è uno stato terminale di "patta"
        return true;
    }

    @Override
    public boolean cutOffTest(int cutOff) {
        if(terminalTest()){return true;}
        else return depth >= cutOff;
    }

    @Override
    public double getEval() {
        return this.evalFunction.applyAsDouble(board);
    }

    @Override
    public String toString() {
        String BoardLine = "+---+---+---+";
        StringBuilder sb = new StringBuilder();
        sb.append(BoardLine);
        for (char[] line : board){
            sb.append(String.format("\n| %c | %c | %c |\n%s",line[0], line[1], line[2], BoardLine));
        }
        return sb.toString();
    }

    public List<char[]> getBoardRows(){
        return GameStateTicTacToeImpl.getBoardRows(board);
    }

    public static List<char[]> getBoardRows(char[][] board){
        List<char[]> rows = new ArrayList<>();

        //aggiungi righe
        rows.add(board[0].clone());
        rows.add(board[1].clone());
        rows.add(board[2].clone());

        //aggiungi colonne
        rows.add(new char[]{board[0][0], board[1][0], board[2][0]});
        rows.add(new char[]{board[0][1], board[1][1], board[2][1]});
        rows.add(new char[]{board[0][2], board[1][2], board[2][2]});

        //aggiungi diagonali
        rows.add(new char[]{board[0][0], board[1][1], board[2][2]});
        rows.add(new char[]{board[0][2], board[1][1], board[2][0]});

        return rows;
    }

    public static int countSymbolInRow(char symbol, char[] row){
        return (int) IntStream.range(0, row.length).filter(i -> row[i] == symbol).count();
    }



}
