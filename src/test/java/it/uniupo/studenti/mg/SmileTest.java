package it.uniupo.studenti.mg;

import it.uniupo.studenti.mg.probreasoning.bayesnets.inference.LikelihoodWeighting;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import smile.Network;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SmileTest {

    @BeforeAll
    static void smileLicense(){
        new smile.License(
                "SMILE LICENSE 86966416 d8b837cd bba07efa " +
                        "THIS IS AN ACADEMIC LICENSE AND CAN BE USED " +
                        "SOLELY FOR ACADEMIC RESEARCH AND TEACHING, " +
                        "AS DEFINED IN THE BAYESFUSION ACADEMIC " +
                        "SOFTWARE LICENSING AGREEMENT. " +
                        "Serial #: b82wb3167263qud4hizcrxyju " +
                        "Issued for: MASSIMILIANO GALLO (10031370@studenti.uniupo.it) " +
                        "Academic institution: Universit\u00e0 del Piemonte orientale " +
                        "Valid until: 2023-07-20 " +
                        "Issued by BayesFusion activation server",
                new byte[] {
                        -42,5,113,114,-71,-77,23,77,67,-81,54,82,-23,58,104,-122,
                        32,7,-17,-19,103,59,99,-74,28,107,118,45,-77,-8,10,-96,
                        -24,16,-41,103,-106,125,107,11,-63,-40,-45,-60,79,21,-102,-70,
                        -89,62,-10,-27,-39,-78,88,-33,80,-102,121,24,3,125,-49,32
                }
        );
    }

    @Test
    public void smileTest(){
        Network net = new Network();
        net.readFile("src\\test\\resources\\CloudyRainExample.xdsl");
        for(String nodeId : net.getAllNodeIds()){
            System.out.println(nodeId);
        }
        net.setEvidence("Cloudy", "True");
        net.updateBeliefs();
        double[] beliefs = net.getNodeValue("WetGrass");
        for(int i = 0; i < beliefs.length; i++){
            System.out.println(
                    net.getOutcomeId("WetGrass", i) + " = " + beliefs[i]);
        }
    }

    @Test
    public void smileTutorial3(){
        System.out.println("Starting Tutorial3...");
        Network net = new Network();

        // load the network created by Tutorial1
        net.readFile("src\\test\\resources\\CloudyRainExample.xdsl");

        for (int h = net.getFirstNode(); h >= 0; h = net.getNextNode(h)) {
            printNodeInfo(net, h);
        }

        System.out.println("Tutorial3 complete.");


    }

    private static void printNodeInfo(Network net, int nodeHandle) {
        System.out.printf("Node id/name: %s/%s\n",
                net.getNodeId(nodeHandle),
                net.getNodeName(nodeHandle));

        System.out.print("  Outcomes:");
        for (String outcomeId: net.getOutcomeIds(nodeHandle)) {
            System.out.print(" " + outcomeId);
        }
        System.out.println();

        String[] parentIds = net.getParentIds(nodeHandle);
        if (parentIds.length > 0) {
            System.out.print("  Parents:");
            for (String parentId: parentIds) {
                System.out.print(" " + parentId);
            }
            System.out.println();
        }

        String[] childIds = net.getChildIds(nodeHandle);
        if (childIds.length > 0) {
            System.out.print("  Children:");
            for (String childId: childIds) {
                System.out.print(" " + childId);
            }
            System.out.println();
        }

        printCptMatrix(net, nodeHandle);
    }


    private static void printCptMatrix(Network net, int nodeHandle) {
        double[] cpt = net.getNodeDefinition(nodeHandle);
        int[] parents = net.getParents(nodeHandle);
        int dimCount = 1 + parents.length;

        int[] dimSizes = new int[dimCount];
        for (int i = 0; i < dimCount - 1; i ++) {
            dimSizes[i] = net.getOutcomeCount(parents[i]);
        }
        dimSizes[dimSizes.length - 1] = net.getOutcomeCount(nodeHandle);

        int[] coords = new int[dimCount];
        for (int elemIdx = 0; elemIdx < cpt.length; elemIdx ++) {
            indexToCoords(elemIdx, dimSizes, coords);

            String outcome = net.getOutcomeId(nodeHandle, coords[dimCount - 1]);
            System.out.printf("    P(%s", outcome);

            if (dimCount > 1) {
                System.out.print(" | ");
                for (int parentIdx = 0; parentIdx < parents.length; parentIdx++)
                {
                    if (parentIdx > 0) System.out.print(",");
                    int parentHandle = parents[parentIdx];
                    System.out.printf("%s=%s",
                            net.getNodeId(parentHandle),
                            net.getOutcomeId(parentHandle, coords[parentIdx]));
                }
            }

            double prob = cpt[elemIdx];
            System.out.printf(")=%f\n", prob);
        }
    }


    private static void indexToCoords(int index, int[] dimSizes, int[] coords) {
        int prod = 1;
        for (int i = dimSizes.length - 1; i >= 0; i --) {
            coords[i] = (index / prod) % dimSizes[i];
            prod *= dimSizes[i];
        }
    }

    @Test
    public void smileTest2(){
        System.out.println("Starting smile test 2...");
        Network net = new Network();
        String netDefinitionPath = "src\\test\\resources\\CloudyRainExample.xdsl";
        net.readFile(netDefinitionPath);
        net.setEvidence("Cloudy", "False");
        net.setEvidence("Sprinkler", "True");
        net.setEvidence("Rain", "False");
        net.setEvidence("WetGrass", "True");
        getEvidenceNodeProbability(net, net.getNode("WetGrass"));

        Network net2 = new Network();
        net2.readFile(netDefinitionPath);
        net2.setEvidence("Cloudy", "False");
        net2.setEvidence("Sprinkler", "True");
        net2.setEvidence("Rain", "False");
        getNodeSample(net2, net2.getNode("WetGrass"));

    }

    /**
     * Dato un nodo evidenza (con eventuali genitori evidenza), recupera il giusto valore di probabilità dalla cpt
     * @param net una rete bayesiana con le evidenze già configurate
     * @param nodeId l'id del nodo
     * @return la probabilità dell'evidenza del nodo NodeId
     */
    public double getEvidenceNodeProbability(Network net, int nodeId){
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

    /** Ottenere un campionamento del nodo, tenendo conto di eventuali genitori evidenza.
     * L'idea è che ci serve un vettore con lunghezza pari al numero di outcome del nodo, partendo dall'outcome "0".
     * @param net una rete bayesiana con le evidenze già configurate
     * @param nodeId l'id del nodo
     * @return l'outcomeID campionato
     */
    public int getNodeSample(Network net, int nodeId){
        int sampledOutcome=0;
        double[] cpt = net.getNodeDefinition(nodeId);
        int k = cpt.length;
        int index = 0; //dobbiamo partire dall'outcome zero
        int[] parents = net.getParents(nodeId);
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
        System.out.printf("Random Double = " + randomDouble + " Sampled outcome = " + sampledOutcome + " -> " + net.getOutcomeId(nodeId, sampledOutcome));
        return sampledOutcome;
    }

    @Test
    public void topologicalOrderTest(){
        System.out.println("Starting topologicalOrderTest...");
        Network net = new Network();
        String netDefinitionPath = "src\\test\\resources\\CloudyRainExample.xdsl";
        net.readFile(netDefinitionPath);
        int[] nodes = net.getAllNodes();
        for (int node : nodes) {
            System.out.printf(node + " ( " + net.getNodeName(node) + " ) ");
        }
    }

    @Test
    public void firstWeightedSampleTest(){
        System.out.println("Starting firstWeightedSampleTest...");
        Network net = new Network();
        String netDefinitionPath = "src\\test\\resources\\CloudyRainExample.xdsl";
        net.readFile(netDefinitionPath);
        net.setEvidence("Sprinkler", "True");
        net.setEvidence("WetGrass", "True");
        System.out.println(">>>>>>>> W = " + LikelihoodWeighting.weightedSample(net));
    }

    @Test
    public void likelywoodSamplingTest(){
        System.out.println("Starting likelywoodSamplingTest...");
        String netDefinitionPath = "src\\test\\resources\\CloudyRainExample.xdsl";
        Network net = new Network();
        net.readFile(netDefinitionPath);
        Map<String, String> evidences = new HashMap<>();
        evidences.put("Sprinkler", "True");
        evidences.put("WetGrass", "True");
        int node = net.getNode("Rain");
        int nodeOutcome = 1;
        double queryResult = LikelihoodWeighting.likelyHoodWeighting(netDefinitionPath,
             evidences,
             node,
             nodeOutcome,
            1000
        );
        System.out.printf("\nQUERY P(" + net.getNodeName(node) + "=" + net.getOutcomeId(node, nodeOutcome) + "|");
        for(Map.Entry<String, String> evidence : evidences.entrySet()){
            System.out.printf(evidence.getKey() + "=" + evidence.getValue() + " ");
        }
        System.out.println(") =" + queryResult);
    }


}
