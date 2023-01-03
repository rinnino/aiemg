package it.uniupo.studenti.mg.util;

import java.util.Arrays;

public class ArraySearch {
    /**
     * Cerca la prima posizione di un Integer in un Array integer
     * @param arr il vettore su cui cercare
     * @param val il valore da cercare
     * @return se esiste, la prima posizione che lo contiene, altrimenti -1
     */
    public static Integer findFirstInArray(int[] arr, int val){
        return Arrays.stream(arr)
                .filter(i -> arr[i] == val)
                .findFirst()
                .orElse(-1);
    }
}
