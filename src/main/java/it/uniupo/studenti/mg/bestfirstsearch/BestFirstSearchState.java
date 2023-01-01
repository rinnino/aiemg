package it.uniupo.studenti.mg.bestfirstsearch;

import java.util.List;
/**
 * Questa classe modella uno stato di un determinato problema di ricerca best first search
 * Uno stato siffatto dovrebbe:
 * <ul>
 *     <li>Permettere di Calcolare la funzione di valutazione f</li>
 *     <li>Tenere traccia del padre ( nessun padre se radice)</li>
 *     <li>Essere Comparable (per la coda di priorità)</li>
 *     <li>Permettere di generare gli stati figli in base alle azioni disponibili</li>
 * </ul>
 */
 public interface BestFirstSearchState  {
    List<BestFirstSearchState> getChildren();
    Double g();
    BestFirstSearchState getFather();

}
