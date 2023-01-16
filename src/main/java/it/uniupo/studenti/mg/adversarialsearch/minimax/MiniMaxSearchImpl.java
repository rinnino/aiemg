package it.uniupo.studenti.mg.adversarialsearch.minimax;

import java.util.Comparator;

public class MiniMaxSearchImpl implements MiniMaxSearch{

    @Override
    public GameState minimaxDecision(GameState gameState) {
        long l = gameState.getActions().stream().peek( e -> System.out.println(e + "\n" + minValue(e))).count(); //Stampa di debug stati sintetizzati
        System.out.println("Count:" + l);
        return gameState.getActions().stream().max(Comparator.comparing(this::minValue)).orElse(null);
    }

    @Override
    public double maxValue(GameState gameState) {
        double v = Double.NEGATIVE_INFINITY;
        if(gameState.terminalTest()){return gameState.getEval();}
        else{
            for(GameState action : gameState.getActions()){
                v = Math.max(v, minValue(action));
            }
        }
        return v;
    }

    @Override
    public double minValue(GameState gameState) {
        double v = Double.POSITIVE_INFINITY;
        if(gameState.terminalTest()){return gameState.getEval();}
        else{
            for(GameState action : gameState.getActions()){
                v = Math.min(v, maxValue(action));
            }
        }
        return v;
    }
}
