package com.mazeWay.service;

import com.mazeWay.exception.EndOfTheWayException;
import com.mazeWay.exception.GoalException;
import com.mazeWay.model.node.Direction;
import com.mazeWay.model.node.Node;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static com.mazeWay.mocks.Mocks.MATRIX_DATA;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class WayFinderRuleTest {

    private WayFinderRule service = new WayFinderRule(3);

    @Test
    @Parameters({"0,1",
            "1,2",
            "2,0"})
    public void whenInitWaysToWalkReturnMap(int insert, int expected) {
        int result = service.generateNewCountChar(insert);
        assertEquals(expected, result);
    }

    @Test
    public void whenSearchNextNodeThenReturnThese() {

        List<Node> wayWalked = new LinkedList<>();
        wayWalked.add(getNode(10,11));
        wayWalked.add(getNode(9,11));
        wayWalked.add(getNode(8,11));
        wayWalked.add(new Node(new int[]{7,11}, 'A', Direction.DOWN, 0));

        Stack<Node> result = service.findNextNodes(MATRIX_DATA, wayWalked);
        assertEquals(1, result.size());
        assertEquals('D', result.peek().getChar());
        assertArrayEquals(new int[]{7,10}, result.peek().getLocation());

    }

    @Test(expected = GoalException.class)
    public void whenSearchNextNodeWithExitCellThenReturnThese() {
        List<Node> wayWalked = new LinkedList<>();
        wayWalked.add(new Node(new int[]{0,1}, 'C', Direction.UP, 0));

        service.findNextNodes(MATRIX_DATA, wayWalked);
    }


    @Test(expected = EndOfTheWayException.class)
    public void whenSearchNextNodeWithoutPatternThenReturnThese() {
        List<Node> wayWalked = new LinkedList<>();
        wayWalked.add(new Node(new int[]{10,11}, 'B', Direction.UP, 1));

        service.findNextNodes(MATRIX_DATA, wayWalked);
    }

    private Node getNode(int y, int x) {
        return new Node(new int[]{y,x}, '-', null, 0);
    }






}
