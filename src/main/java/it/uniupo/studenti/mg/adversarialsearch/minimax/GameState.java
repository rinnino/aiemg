package it.uniupo.studenti.mg.adversarialsearch.minimax;

import java.util.List;

public interface GameState {
    List<GameState> getActions();

    boolean terminalTest();

    boolean cutOffTest(int cutOff);

    double getEval();
}
