package it.uniupo.studenti.mg.bestfirstsearch;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Classe che implementa una generica ricerca best first search.
 * Questo tipo di ricerca espande un nodo dalla frontiera in base
 * al risultato di una funzione di valutazione f: S -> R+ applicata ad ogni nodo:
 * <p><ul>
 *      <li>S è Lo spazio degli stati.</li>
 *      <li>R+ Un numero reale positivo</li>
 * </ul><p>
 * Viene selezionato ed espanso il nodo con f minore. Dunque si tratta di una
 * coda di priorità. Possiamo Utilizzare l'apposita classe java PriorityQueue<E>
 * dove E è la classe specifica del nodo (lo stato del gioco 8-Tile ad esempio)
 */
public abstract class BestFirstSearch {
    private final BestFirstSearchState goal;

    private Comparator<BestFirstSearchState> stateComparator;

    // Numero d'iterazioni usate da BFS, è una misura di performance
    private int iterations = 0;

    public BestFirstSearch(BestFirstSearchState goal) {
        this.goal = goal;
    }

    /**
     * La funzione di valutazione f è astratta in quanto cambia in base al tipo di ricerca Best First Search
     * che si vuole implementare. Dunque il metodo è astratto
     */
    public abstract double EvaluationFunction(BestFirstSearchState state);

    /**
     * Per eseguire una best first search:
     * <ol><li>Inseriamo il nodo che rappresenta lo stato di partenza nella frontiera pq</li>
     * <li>Selezioniamo il nodo della frontiera con f minore, togliamolo da pq.
     * Se il nodo è un GOAL abbiamo finito la ricerca e lo restituiamo come stato risultato</li>
     * <li>Espandiamo il nodo selezionato valutando l'insieme delle azioni che possiamo
     * eseguire su quel nodo (nel gioco 8-tile spostare il vuoto in alto, basso,destra,sinistra)
     * <li>L'espansione del nodo selezionato genererà un numero di nodi "figli" <= al numero di azioni possibili che
     * verranno aggiunti ala coda di priorità pq</li>
     * <li>Tornare al punto 2</li>
     *
     * @param s stato iniziale
     * @param g stato GOAL
     */

    public BestFirstSearchState search(BestFirstSearchState s, BestFirstSearchState g){
        iterations = 0;
        PriorityQueue<BestFirstSearchState> pq = new PriorityQueue<>(stateComparator);
        pq.add(s);
        while(!pq.isEmpty()){
            iterations++;
            BestFirstSearchState selectedState = pq.remove();
            if(selectedState.equals(g)) {return selectedState;}
            else pq.addAll(selectedState.getChildren());
        }
        return null;
    }

    //TODO: se avanza tempo, versione search per evitare stati ripetuti

    public BestFirstSearchState getGoal() {
        return goal;
    }

    public void setStateComparator(Comparator<BestFirstSearchState> stateComparator) {
        this.stateComparator = stateComparator;
    }

    public int getIterations() {
        return iterations;
    }

}
