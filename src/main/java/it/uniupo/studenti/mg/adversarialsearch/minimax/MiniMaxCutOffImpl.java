package it.uniupo.studenti.mg.adversarialsearch.minimax;

public class MiniMaxCutOffImpl extends  MiniMaxSearchImpl{

    private final int cutOff;

    public MiniMaxCutOffImpl(int cutOff) {
        this.cutOff = cutOff;
    }

    @Override
    public GameState minimaxDecision(GameState gameState) {
        return super.minimaxDecision(gameState);
    }

    @Override
    public double maxValue(GameState gameState) {
        if(!gameState.cutOffTest(cutOff)){return super.maxValue(gameState);}
        else return gameState.getEval();
    }

    @Override
    public double minValue(GameState gameState) {
        if(!gameState.cutOffTest(cutOff)){return super.minValue(gameState);}
        else return gameState.getEval();
    }
}
