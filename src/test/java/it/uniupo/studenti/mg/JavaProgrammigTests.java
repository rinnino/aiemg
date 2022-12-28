package it.uniupo.studenti.mg;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class JavaProgrammigTests {
    /**
     * Test per verificare che il metodo clone di un array con valori primitivi faccia una deep copy
     */
    @Test
    void arrayCloneWithPrimitiveTypes(){
        int[]a = new int[]{1, 2, 3};
        int[]b = a.clone();
        System.out.printf(String.format("a[] = %s, b[] = %s\n", Arrays.toString(a), Arrays.toString(b)));
        System.out.printf(String.format("a[] reference: %s, b[] reference: %s",a.toString(),b.toString()));
        Assertions.assertNotEquals(a,b);
    }
}
