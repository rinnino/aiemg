package it.uniupo.studenti.mg.probreasoning.bayesnets.inference;

import smile.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LikelihoodWeighting {

    /**
     * Questo metodo utilizza l'algoritmo likelihood Weighting per rispondere alla query:
     *      P(node = nodeOutcome | evidence)
     *
     * @param netFile il file che modella la rete bayesiana
     * @param evidences le evidenze richieste nella query, in una mappa di coppie <idNodo, idOutCome>
     * @param node il nodo richiesto dalla query
     * @param nodeOutcome l'outcome del nodo desiderato dalla query
     * @param samplingsNumber il numero di campionamenti
     * @return una stima probabilistica della query
     */
    public static double likelyHoodWeighting(String netFile,
                                             Map<String, String> evidences,
                                             int node,
                                             int nodeOutcome,
                                             int samplingsNumber){
        double sumW = 0; // peso di tutti i campionamenti, serve per normalizzare
        double sumQueryW = 0; // peso dei campionamenti che soddisfano la query
        double w; // peso del run corrente
        for(int i=0; i<samplingsNumber; i++){
            System.out.println("CAMPIONAMENTO " + i + " ---------------------------------------");
            Network net = new Network();
            net.readFile(netFile);
            for(Map.Entry<String, String> evidence : evidences.entrySet()){
                net.setEvidence(evidence.getKey(), evidence.getValue());
            }
            w = weightedSample(net);
            if(net.getEvidence(node) == nodeOutcome){
                // il run soddisfa la query
                sumQueryW += w;
            }
            sumW += w;
        }
        return sumQueryW/sumW;
    }

    /**
     * Campiona i nodi di una rete bayesiana che non fan già parte dell' evidenza.
     * ATTENZIONE: l'oggetto net verrà modificato, inserendo nelle evidenze i risultati dei campionamenti
     * @param net la rete bayesiana
     * @return il peso del run
     */
    public static double weightedSample(Network net){
        double w = 1;
        int[] nodes = net.getAllNodes(); // smile restituisce un array coi nodi in ordine topologico
        List<Double> evidencesProb = new ArrayList<>();
        System.out.println("Inizio del run, con le seguenti evidenze : ");
        for(int i=0; i<nodes.length; i++){
            if(net.isEvidence(i)){System.out.printf("\t" + net.getNodeName(i) + "=" + net.getEvidenceId(i) + " ");}
        }
        System.out.println();
        for(int i=0; i<nodes.length; i++){
            if(net.isEvidence(i)){
                //Se il nodo è un evidenza, allora w = w * P(nodo| evidenze genitori del nodo)
                double evidenceNodeProb = getEvidenceNodeProbability(net, i);
                evidencesProb.add(evidenceNodeProb);
                w *= evidenceNodeProb;
            }else {
                //altrimenti dobbiamo campionare il nodo e aggiungere il campionamento all'evidenza
                net.setEvidence(i, getNodeSample(net, i));
            }
        }
        System.out.print("Peso w del run = ");
        for (Double evidenceProb : evidencesProb) {
            System.out.printf(" " + evidenceProb);
            if(!evidenceProb.equals(evidencesProb.get(evidencesProb.size() - 1))){
                System.out.print(" *");
            }
        }
        System.out.println(" = " + w);
        return w;
    }

    /**
     * Dato un nodo evidenza (con eventuali genitori nell'evidenza), recupera il giusto valore di probabilità dalla cpt
     * @param net una rete bayesiana con le evidenze già configurate
     * @param nodeId l'id del nodo
     * @return la probabilità dell'evidenza del nodo NodeId
     */
    public static double getEvidenceNodeProbability(Network net, int nodeId){
        double[] cpt = net.getNodeDefinition(nodeId);
        int k = cpt.length;
        int index = net.getEvidence(nodeId);
        int[] parents = net.getParents(nodeId);
        System.out.printf("P(" + net.getNodeName(nodeId) + "=" + net.getEvidenceId(nodeId));
        if(parents.length > 0) {System.out.print("|");} //se abbiamo genitori, prob condizionata
        for(int i=0; i<parents.length; i++){
            System.out.printf(net.getNodeName(parents[i]) + "=" + net.getEvidenceId(parents[i]));
            if(i != net.getParents(nodeId).length -1) {System.out.print(", ");} //stampa separatore
            k = k/net.getOutcomeCount(i);
            index += net.getEvidence(parents[i]) * k;
        }
        System.out.println(") = " + cpt[index] + ", CPT index = " + index);
        return cpt[index];
    }

    /**
     * Permette di ottenere un campionamento del nodo, tenendo conto di eventuali genitori evidenza.
     * L'idea è che ci serve un vettore con lunghezza pari al numero di outcome del nodo, partendo dall'outcome "0".
     * Se gli outcome sono ad esempio tre, L = 0.1 M = 0.8 H = 0.1 il vettore probDistrArray sarà : {0.1, 0.8, 0.1}
     * Sorteggiando un double casuale, avremo il valore entro l'intervallo corrispondente. Ad esempio:
     * sorteggio: 0.01 = L; 0.5645 = M, 0.976689 = H
     * @param net una rete bayesiana con le evidenze già configurate
     * @param nodeId l'id del nodo
     * @return l'outcomeID campionato
     */
    public static int getNodeSample(Network net, int nodeId){
        int sampledOutcome=0;
        double[] cpt = net.getNodeDefinition(nodeId);
        int k = cpt.length;
        int index = 0; //dobbiamo partire dall'outcome zero
        int[] parents = net.getParents(nodeId);
        System.out.printf("Campionamento nodo " + net.getNodeId(nodeId) + ", prob. outcomes: ");
        for(int i=0; i<parents.length; i++){
            k = k/net.getOutcomeCount(i);
            index += net.getEvidence(parents[i]) * k;
        }
        double[] probDistrArray = new double[net.getOutcomeCount(nodeId)];
        for (int i=0;i<probDistrArray.length; i++){
            probDistrArray[i] = cpt[index+i];
            System.out.printf(net.getOutcomeId(nodeId, i) + "=" + probDistrArray[i] + "  ");
        }
        Random rng  = new Random();
        double randomDouble = rng.nextDouble();
        double doubleSum = 0;
        for(int i=0; i<probDistrArray.length; i++){
            doubleSum += probDistrArray[i];
            if(randomDouble<doubleSum){sampledOutcome = i; break;}
        }
        System.out.println("Random Double = " + randomDouble + " Sampled outcome = " + sampledOutcome + " -> " + net.getOutcomeId(nodeId, sampledOutcome));
        return sampledOutcome;
    }
}
