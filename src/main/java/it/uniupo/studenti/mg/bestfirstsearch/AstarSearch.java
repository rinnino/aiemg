package it.uniupo.studenti.mg.bestfirstsearch;

import java.util.function.ToDoubleBiFunction;

public class AstarSearch extends BestFirstSearch{

    /**
     * questa euristica di default annulla l'euristica riducendo ad una ricerca senza euristica
     */
    private ToDoubleBiFunction<BestFirstSearchState, BestFirstSearchState>
            heuristicFunction = (state, goal) -> 0;

    public AstarSearch(BestFirstSearchState goal,
                       ToDoubleBiFunction<BestFirstSearchState, BestFirstSearchState> heuristicFunction) {
        super(goal);
        super.setStateComparator(
                (BestFirstSearchState a, BestFirstSearchState b)
                        -> Double.compare(this.EvaluationFunction(a), this.EvaluationFunction(b))
        );
        setHeuristicFunction(heuristicFunction);
    }

    public void setHeuristicFunction(ToDoubleBiFunction<BestFirstSearchState, BestFirstSearchState> heuristicFunction) {
        this.heuristicFunction = heuristicFunction;
    }

    @Override
    public double EvaluationFunction(BestFirstSearchState state) {
        return state.g() + heuristicFunction.applyAsDouble(state, getGoal());
    }
}
