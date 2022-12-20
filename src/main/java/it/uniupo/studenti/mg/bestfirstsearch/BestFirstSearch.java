package it.uniupo.studenti.mg.bestfirstsearch;

import java.util.PriorityQueue;

/**
 * Classe che implementa una generica ricerca best first search.
 * Questo tipo di funzione espande un nodo dalla frontiera in base
 * al risultato di una funzione di valutazione f: S -> R+ applicata ad ogni nodo:
 * <p><ul>
 *      <li>S è Lo spazio degli stati. uno stato rappresentato da un nodo</li>
 *      <li>R+ Un reale positivo</li>
 * </ul><p>
 * Viene selezionato ed espanso il nodo con f minore. Dunque si tratta di una
 * coda di priorità. Possiamo Utilizzare l'apposita classe java PriorityQueue<E>
 * dove E è la classe specifica del nodo (lo stato del gioco 8 tile ad esempio)
 */
public abstract class BestFirstSearch<E> {
    private PriorityQueue<E> pq = new PriorityQueue<E>();
    public BestFirstSearch() {
    }

    /**
     * La funzione di valutazione f è astratta in quanto cambia in base al tipo di ricerca Best First Search
     * che si vuole implementare. Dunque il metodo è astratto
     */
    public abstract double EvaluationFunction();

    /**
     *  Quando espandiamo un nodo dobbiamo valutare l'insieme delle azioni che possiamo
     *  eseguire su quel nodo (nel gioco 8 tile spostare il vuoto in alto,basso,destra,sinistra)
     *  e generare un numero di nodi "figli" pari alle azioni possibili.
     *  Se nodo selezionato è un nodo GOAL abbiamo finito, altrimenti viene rimosso dalla frontiera.
     *  I figli generati finiscono nella frontiera, che altro non è che una coda di prioritò. la priorità
     *  viene calcolata dala funzione f
     */





}
