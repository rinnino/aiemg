package it.uniupo.studenti.mg.adversarialsearch.minimax;

public interface MiniMaxSearch {
    GameState minimaxDecision(GameState gameState);
    double  maxValue(GameState gameState);
    double minValue(GameState gameState);
}
