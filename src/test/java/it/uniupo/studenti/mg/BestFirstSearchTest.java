package it.uniupo.studenti.mg;

import it.uniupo.studenti.mg.bestfirstsearch.BestFirstSearchState;
import it.uniupo.studenti.mg.bestfirstsearch.BestFirstSearchStateEightTile;
import it.uniupo.studenti.mg.bestfirstsearch.it.uniupo.studenti.mg.util.ArraySearch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class BestFirstSearchTest {
    private BestFirstSearchStateEightTile eightTileState =
            new BestFirstSearchStateEightTile();

    @Test
    void printEightTileState(){
        System.out.println("+--(FIRST TEST PRINT ROOT)-------------------------------*");
        System.out.printf(eightTileState.toString());
    }

    @Test
    void findFirstIntInArray(){
        int[] a = new int[]{1 ,2, 3, 4, 0, 5, 6, 7, 8};
        int ep = ArraySearch.findFirstInArray(a, 0);
        Assertions.assertEquals(4, ep);
        ep = ArraySearch.findFirstInArray(a, 1);
        Assertions.assertEquals(0, ep);
        ep = ArraySearch.findFirstInArray(a, 8);
        Assertions.assertEquals(8, ep);
        ep = ArraySearch.findFirstInArray(a, 11);
        Assertions.assertEquals(-1, ep);
    }

    @Test
    void moveUpTest(){
        System.out.println("+--(MOVE UP TEST)---------------------------------------*");
        BestFirstSearchStateEightTile newState = eightTileState.moveUp();
        Assertions.assertTrue(
                Arrays.equals(newState.getBoard(), new int[]{1 ,0, 3, 4, 2, 5, 6, 7, 8}));
        System.out.printf(newState.toString());
        newState = newState.moveUp(); // essendo nella prima riga deve restituire null
        Assertions.assertNull(newState);
    }

    @Test
    void moveDownTest(){
        System.out.println("+--(MOVE DOWN TEST)--------------------------------------*");
        BestFirstSearchStateEightTile newState = eightTileState.moveDown();
        Assertions.assertTrue(
                Arrays.equals(newState.getBoard(), new int[]{1 ,2, 3, 4, 7, 5, 6, 0, 8}));
        System.out.printf(newState.toString());
        newState = newState.moveDown(); // essendo nell'ultima riga deve restituire null
        Assertions.assertNull(newState);
    }

    @Test
    void moveLeftTest(){
        System.out.println("+--(MOVE LEFT TEST)--------------------------------------*");
        BestFirstSearchStateEightTile newState = eightTileState.moveLeft();
        Assertions.assertTrue(
                Arrays.equals(newState.getBoard(), new int[]{1 ,2, 3, 0, 4, 5, 6, 7, 8}));
        System.out.printf(newState.toString());
        newState = newState.moveLeft(); // essendo nell'ultima riga deve restituire null
        Assertions.assertNull(newState);
    }

    @Test
    void moveRightTest(){
        System.out.println("+--(MOVE RIGHT TEST)--------------------------------------*");
        BestFirstSearchStateEightTile newState = eightTileState.moveRight();
        Assertions.assertTrue(
                Arrays.equals(newState.getBoard(), new int[]{1 ,2, 3, 4, 5, 0, 6, 7, 8}));
        System.out.printf(newState.toString());
        newState = newState.moveRight(); // essendo nell'ultima riga deve restituire null
        Assertions.assertNull(newState);
    }

    @Test
    void getChildrenTest1(){
        System.out.println("+--(CHILDREN TEST 1)--------------------------------------*");
        eightTileState = new BestFirstSearchStateEightTile();
        System.out.println(eightTileState.toString());
        List<BestFirstSearchState> childrenList = eightTileState.getChildren();
        for (BestFirstSearchState children : childrenList){
            System.out.println(((BestFirstSearchStateEightTile)children).toString());
        }
    }

    @Test
    void getChildrenTest2(){
        System.out.println("+--(CHILDREN TEST 2)--------------------------------------*");
        eightTileState = new BestFirstSearchStateEightTile(
                new int[]{0 ,1, 2, 3, 4, 5, 6, 7, 8}, "ROOT");
        System.out.println(eightTileState.toString());
        List<BestFirstSearchState> childrenList = eightTileState.getChildren();
        for (BestFirstSearchState children : childrenList){
            System.out.println(((BestFirstSearchStateEightTile)children).toString());
        }
    }




}
