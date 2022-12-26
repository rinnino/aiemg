package it.uniupo.studenti.mg.bestfirstsearch;

import java.util.ArrayList;
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
 * dove E è la classe specifica del nodo (lo stato del gioco 8 tile ad esempio)
 */
public abstract class BestFirstSearch<E extends BestFirstSearchState & Comparable> {
    private final PriorityQueue<E> pq = new PriorityQueue<>();
    private ArrayList<String> actionList = new ArrayList<>();
    private ArrayList<E> stateList = new ArrayList<>();

    public BestFirstSearch() {
    }

    /**
     * La funzione di valutazione f è astratta in quanto cambia in base al tipo di ricerca Best First Search
     * che si vuole implementare. Dunque il metodo è astratto
     */
    public abstract double EvaluationFunction();

    /**
     * Per eseguire una best first search:
     * <ol><li>Inseriamo il nodo che rappresenta lo stato di partenza nella frontiera pq</li>
     * <li>Selezioniamo il nodo della frontiera con f minore, togliamolo da pq e appendiamolo in actionList
     * e stateList.
     * Se il nodo è un GOAL abbiamo finito la ricerca</li>
     * <li>Espandiamo il nodo selezionato valutando l'insieme delle azioni che possiamo
     * eseguire su quel nodo (nel gioco 8 tile spostare il vuoto in alto,basso,destra,sinistra)
     * e generando un numero di nodi "figli" pari alle azioni possibili. Mettiamoli in pq</li>
     * <li>Tornare al punto 2</li>
     *
     * @param s stato iniziale
     * @param g stato GOAL
     */

    private void search(E s, E g){
        pq.add(s);
        while(!pq.isEmpty()){
            E selectedState = pq.remove();
            actionList.add(String.valueOf(selectedState));
            stateList.add(selectedState);
            if(selectedState.equals(g)) break;
            else pq.addAll(selectedState.getChildren());
        }
    }

    public ArrayList<String> getActionList() {
        return actionList;
    }

    public void setActionList(ArrayList<String> actionList) {
        this.actionList = actionList;
    }

    public ArrayList<E> getStateList() {
        return stateList;
    }

    public void setStateList(ArrayList<E> stateList) {
        this.stateList = stateList;
    }
}
