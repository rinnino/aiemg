package it.uniupo.studenti.mg;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

public class JavaProgrammigTests {
    /**
     * Test per verificare che il metodo clone di un array con valori primitivi faccia una deep copy
     */

    @Test
    void arrayCloneWithPrimitiveTypes(){
        int[]a = new int[]{1, 2, 3};
        int[]b = a.clone();
        System.out.printf(String.format("a[] = %s, b[] = %s\n", Arrays.toString(a), Arrays.toString(b)));
        System.out.printf(String.format(
                "a[] identity hash code: %s, b[] identity hash code: %s\n",
                System.identityHashCode(a),
                System.identityHashCode(b)));
        Assertions.assertNotEquals(a,b);
    }

    @Test
    void priorityQueueTest(){
        PriorityQueue<Double> pq = new PriorityQueue<>();
        pq.add(1.0);
        pq.add(100.0);
        System.out.println(pq.remove().toString());
        System.out.println(pq.remove().toString());
    }

}
