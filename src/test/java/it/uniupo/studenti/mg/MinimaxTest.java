package it.uniupo.studenti.mg;

import it.uniupo.studenti.mg.adversarialsearch.minimax.GameState;
import it.uniupo.studenti.mg.adversarialsearch.minimax.GameStateTicTacToeImpl;
import it.uniupo.studenti.mg.adversarialsearch.minimax.MiniMaxSearch;
import it.uniupo.studenti.mg.adversarialsearch.minimax.MiniMaxSearchImpl;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class MinimaxTest {

    GameStateTicTacToeImpl ticTacToe = new GameStateTicTacToeImpl(
            new char[][]{{'o','o',' '},{'x',' ',' '},{'x',' ',' '}},
            GameStateTicTacToeImpl.Player.MAX, 0);

    @Test
    public void GameStatePrintTest(){
        System.out.println("+--(GameStatePrintTest)-------------------------------*");
        System.out.println(ticTacToe);
    }

    @Test
    public void getLinesFromBoardTest(){
        System.out.println("+--(getLinesFromBoardTest)-------------------------------*");
        List<char[]> lines = ticTacToe.getBoardRows();
        for(char[] line : lines){
            System.out.println(Arrays.toString(line) + " Number of x: " +
                    GameStateTicTacToeImpl.countSymbolInRow('x',line) +
                    ", Number of o: " +
                    GameStateTicTacToeImpl.countSymbolInRow('o',line));
        }
    }

    @Test
    public void  evalTest(){
        System.out.println("+--(evalTest)-------------------------------*");
        System.out.println(ticTacToe.toString());
        ToDoubleFunction<char[][]> eval = ticTacToe.getEvalFunction();
        System.out.println("MAX: " + eval.applyAsDouble(ticTacToe.getBoard()));
    }

    @Test
    public void getActionsTest(){
        System.out.println("+--(getActionsTest)-------------------------------*");
        System.out.println(ticTacToe);
        System.out.println("Actions for MAX:");
        List<GameState> actions = ticTacToe.getActions();
        for(GameState action : actions){
            System.out.println(action);
        }
        ticTacToe = new GameStateTicTacToeImpl(
                new char[][]{{'x','o',' '},{'x',' ',' '},{'x',' ',' '}}, GameStateTicTacToeImpl.Player.MIN, 0);
        System.out.println("Actions for MIN:");
        actions = ticTacToe.getActions();
        for(GameState action : actions){
            System.out.println(action);
        }

    }

    @Test
    public void GameStateArgMaxTest(){
        System.out.println("+--(GameStateArgMaxTest)-------------------------------*");
        GameState tttState = new GameStateTicTacToeImpl(
                new char[][]{{'x','o','x'},{'o','x',' '},{' ',' ',' '}},
                GameStateTicTacToeImpl.Player.MAX, 0);
        for(GameState g : tttState.getActions()){
            System.out.println(g);
            System.out.println(g.getEval());
        }
        System.out.println("Stato selezionato:\n" +
                tttState.getActions().stream().max(Comparator.comparing(GameState::getEval)).get());
    }

    @Test
    public void terminalTestControlTest(){
        System.out.println("+--(terminalTestControlTest)-------------------------------*");
        List<GameState> gameStates = ( new GameStateTicTacToeImpl(
                new char[][]{{'x','o',' '},{'x',' ',' '},{' ',' ',' '}},
                GameStateTicTacToeImpl.Player.MAX, 0).getActions());

        for(GameState gameState : gameStates){
            System.out.println(gameState + "\n" + gameState.terminalTest());
        }

    }

    @Test
    public  void cutOffTest(){
        System.out.println("+--(cutOffTest)-------------------------------*");
        GameState state = new GameStateTicTacToeImpl(
                new char[][]{{'x','o','x'},{'x',' ',' '},{' ',' ',' '}},
                GameStateTicTacToeImpl.Player.MAX, 0);
        System.out.println("Starting from depth 0:\n"+state);
        List<GameState> gameStates = (state.getActions());
        for(GameState gameState : gameStates){
            System.out.println(gameState + "\nCutoffTest: " + gameState.cutOffTest(2));
        }
        state = new GameStateTicTacToeImpl(
                new char[][]{{'x','o','x'},{'x',' ',' '},{' ',' ',' '}},
                GameStateTicTacToeImpl.Player.MAX, 1);
        System.out.println("Starting from depth 1:\n"+state);
        gameStates = (state.getActions());
        for(GameState gameState : gameStates){
            System.out.println(gameState + "\nCutoffTest: " + gameState.cutOffTest(2));
        }
        state = new GameStateTicTacToeImpl(
                new char[][]{{'x','o','x'},{'x',' ',' '},{' ',' ',' '}},
                GameStateTicTacToeImpl.Player.MAX, 2);
        System.out.println("Starting from depth 2:\n"+state);
        gameStates = (state.getActions());
        for(GameState gameState : gameStates){
            System.out.println(gameState + "\nCutoffTest: " + gameState.cutOffTest(2));
        }
    }

    @Test
    public void miniMaxSearchTest1(){
        System.out.println("+--(miniMaxSearchTest1)-------------------------------*");
        GameState state = new GameStateTicTacToeImpl(
                new char[][]{{'o',' ',' '},{'o','x',' '},{' ',' ','x'}},
                GameStateTicTacToeImpl.Player.MAX, 0);
        System.out.println("Starting from:\n"+state);
        MiniMaxSearch miniMaxSearch = new MiniMaxSearchImpl();
        System.out.println("Minimax Decision: \n" +miniMaxSearch.minimaxDecision(state));
    }

}
