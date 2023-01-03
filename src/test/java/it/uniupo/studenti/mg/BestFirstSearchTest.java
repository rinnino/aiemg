package it.uniupo.studenti.mg;

import it.uniupo.studenti.mg.bestfirstsearch.AstarSearch;
import it.uniupo.studenti.mg.bestfirstsearch.BestFirstSearchState;
import it.uniupo.studenti.mg.bestfirstsearch.BestFirstSearchStateEightTile;
import it.uniupo.studenti.mg.util.ArraySearch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.function.ToDoubleBiFunction;


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
        Assertions.assertArrayEquals(newState.getBoard(), new int[]{1 ,0, 3, 4, 2, 5, 6, 7, 8});
        System.out.printf(newState.toString());
        newState = newState.moveUp(); // essendo nella prima riga deve restituire null
        Assertions.assertNull(newState);
    }

    @Test
    void moveDownTest(){
        System.out.println("+--(MOVE DOWN TEST)--------------------------------------*");
        BestFirstSearchStateEightTile newState = eightTileState.moveDown();
        Assertions.assertArrayEquals(newState.getBoard(), new int[]{1 ,2, 3, 4, 7, 5, 6, 0, 8});
        System.out.printf(newState.toString());
        newState = newState.moveDown(); // essendo nell'ultima riga deve restituire null
        Assertions.assertNull(newState);
    }

    @Test
    void moveLeftTest(){
        System.out.println("+--(MOVE LEFT TEST)--------------------------------------*");
        BestFirstSearchStateEightTile newState = eightTileState.moveLeft();
        Assertions.assertArrayEquals(newState.getBoard(), new int[]{1 ,2, 3, 0, 4, 5, 6, 7, 8});
        System.out.printf(newState.toString());
        newState = newState.moveLeft(); // essendo nell'ultima riga deve restituire null
        Assertions.assertNull(newState);
    }

    @Test
    void moveRightTest(){
        System.out.println("+--(MOVE RIGHT TEST)--------------------------------------*");
        BestFirstSearchStateEightTile newState = eightTileState.moveRight();
        Assertions.assertArrayEquals(newState.getBoard(), new int[]{1 ,2, 3, 4, 5, 0, 6, 7, 8});
        System.out.printf(newState.toString());
        newState = newState.moveRight(); // essendo nell'ultima riga deve restituire null
        Assertions.assertNull(newState);
    }

    @Test
    void getChildrenTest1(){
        System.out.println("+--(CHILDREN TEST 1)--------------------------------------*");
        eightTileState = new BestFirstSearchStateEightTile();
        System.out.println(eightTileState);
        List<BestFirstSearchState> childrenList = eightTileState.getChildren();
        for (BestFirstSearchState children : childrenList){
            System.out.println(children.toString());
        }
    }

    @Test
    void getChildrenTest2(){
        System.out.println("+--(CHILDREN TEST 2)--------------------------------------*");
        eightTileState = new BestFirstSearchStateEightTile(
                new int[]{0 ,1, 2, 3, 4, 5, 6, 7, 8}, "ROOT", 0);
        System.out.println(eightTileState);
        List<BestFirstSearchState> childrenList = eightTileState.getChildren();
        for (BestFirstSearchState children : childrenList){
            System.out.println(children.toString());
        }
    }

    @Test
    void getChildrenTest3(){
        System.out.println("+--(CHILDREN TEST 3)--------------------------------------*");
        eightTileState = new BestFirstSearchStateEightTile(
                new int[]{1 ,2, 3, 0, 4, 5, 6, 7, 8}, "ROOT", 0);
        System.out.println(eightTileState);
        List<BestFirstSearchState> childrenList = eightTileState.getChildren();
        for (BestFirstSearchState children : childrenList){
            System.out.println(children.toString());
        }
    }

    @Test
    void aStarTest1(){
        System.out.println("+--(A* TEST with out of order tiles heuristic)--------------------------------------*");
        BestFirstSearchState start = new BestFirstSearchStateEightTile(
                new int[]{0,1,2,3,4,5,6,7,8},"", 0);
        BestFirstSearchState goal = new BestFirstSearchStateEightTile(
                new int[]{1,2,3,4,5,6,7,8,0},"", 0);

        //usiamo la seguente euristica: tessere fuori posto rispetto al goal
        ToDoubleBiFunction<BestFirstSearchState, BestFirstSearchState> h =
                (BestFirstSearchState s, BestFirstSearchState g)
                        ->
                {
                    int[] sBoard = ((BestFirstSearchStateEightTile)s).getBoard();
                    int[] gBoard = ((BestFirstSearchStateEightTile)g).getBoard();
                    int ris = 0;
                    for(int i=0; i<9; i++){
                        if(!(sBoard[i] == gBoard[i])){ris++;}
                    }
                    return ris;
                };

        AstarSearch eightTileSearchProblem = new AstarSearch(goal,h);
        BestFirstSearchState result = eightTileSearchProblem.search(start, goal);
        System.out.printf(result.toString());
        BestFirstSearchState father = result.getFather();
        while(father != null){
            System.out.println("+-----------------------------------------------+");
            System.out.printf("f(n):" + eightTileSearchProblem.EvaluationFunction(father) + "\n");
            System.out.printf(father.toString());
            father = father.getFather();
        }
        System.out.printf("\nNumero di iterazioni: " + eightTileSearchProblem.getIterations());

    }

    @Test
    void manhattanDistanceTest(){
        System.out.println("+--(Manhattan distance TEST)--------------------------------------*");
        BestFirstSearchStateEightTile start = new BestFirstSearchStateEightTile(
                new int[]{5,4,0,6,1,8,7,3,2},"", 0);
        BestFirstSearchStateEightTile goal = new BestFirstSearchStateEightTile(
                new int[]{1,2,3,8,0,4,7,6,5},"", 0);
        int manhattanDistance = BestFirstSearchStateEightTile.manhattanDistance(
                start,
                goal);
        System.out.printf("Manhattan : %d\n", manhattanDistance);
    }

    @Test
    void aStarTest2(){
        System.out.println("+--(A* TEST with manhattan distance heuristic)--------------------------------------*");
        BestFirstSearchState start = new BestFirstSearchStateEightTile(
                new int[]{0,1,2,3,4,5,6,7,8},"", 0);
        BestFirstSearchState goal = new BestFirstSearchStateEightTile(
                new int[]{1,2,3,4,5,6,7,8,0},"", 0);

        //usiamo la seguente euristica: distanza di manhattan
        ToDoubleBiFunction<BestFirstSearchState, BestFirstSearchState> h =
                (BestFirstSearchState s, BestFirstSearchState g)
                        -> BestFirstSearchStateEightTile.manhattanDistance(
                                (BestFirstSearchStateEightTile) s,
                                (BestFirstSearchStateEightTile) g);

        AstarSearch eightTileSearchProblem = new AstarSearch(goal,h);
        BestFirstSearchState result = eightTileSearchProblem.search(start, goal);
        System.out.printf(result.toString());
        BestFirstSearchState father = result.getFather();
        while(father != null){
            System.out.println("+-----------------------------------------------+");
            System.out.printf("f(n):" + eightTileSearchProblem.EvaluationFunction(father) + "\n");
            System.out.printf(father.toString());
            father = father.getFather();
        }
        System.out.printf("\nNumero di iterazioni: " + eightTileSearchProblem.getIterations());

    }



}
