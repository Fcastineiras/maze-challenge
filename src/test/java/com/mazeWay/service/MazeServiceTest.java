package com.mazeWay.service;

import com.mazeWay.exception.EndOfTheWayException;
import com.mazeWay.exception.GoalException;
import com.mazeWay.model.MatrixData;
import com.mazeWay.model.node.Node;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static com.mazeWay.mocks.Mocks.MATRIX_DATA;
import static com.mazeWay.mocks.Mocks.NODE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

/**
 * Created by Fiamma on 2/5/2021.
 */
public class MazeServiceTest {


    private WayFinderRule wayFinderRule = Mockito.mock(WayFinderRule.class);

    private MazeService mazeService = new MazeService(wayFinderRule);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenInitWaysToWalkReturnMap() {
        Map<Integer, ArrayList<Node>> wayToWalk = mazeService.initWays(NODE);
        assertEquals(wayToWalk.size(), 1);
    }

    @Test
    public void whenFoundNewNodesApplySuccessChangesInExternalLists() {
        Map<Integer, ArrayList<Node>> waysToWalk = this.initWays(MATRIX_DATA.getStarter());
        ArrayList<ArrayList<Node>> waysWithGoal = new ArrayList<>();
        ArrayList<Node> way = new ArrayList<>();
        Stack<Node> nextNodes = new Stack<>();
        nextNodes.add(NODE);
        nextNodes.add(NODE);
        nextNodes.add(NODE);
        int nextIndex = 1;

        Mockito.when(wayFinderRule.findNextNodes(any(MatrixData.class), any())).thenReturn(nextNodes);
        int resultIndex = mazeService.recursiveFinder(MATRIX_DATA, waysToWalk, waysWithGoal, nextIndex);
        assertTrue(nextNodes.isEmpty());
        assertEquals(waysToWalk.size(), 3);
        assertEquals(waysToWalk.get(0).size(), 2);
        assertEquals(nextIndex + 2, resultIndex);
    }

    @Test
    public void whenFoundGoalApplySuccessChangesInExternalLists() {
        Map<Integer, ArrayList<Node>> waysToWalk = this.initWays(MATRIX_DATA.getStarter());
        ArrayList<ArrayList<Node>> waysWithGoal = new ArrayList<>();
        int nextIndex = 1;

        Mockito.when(wayFinderRule.findNextNodes(any(MatrixData.class), any())).thenThrow(new GoalException());
        int resultIndex = mazeService.recursiveFinder(MATRIX_DATA, waysToWalk, waysWithGoal, nextIndex);
        assertEquals(waysWithGoal.size(), 1);
        assertEquals(waysToWalk.size(), 0);
        assertEquals(nextIndex, resultIndex);
    }

    @Test
    public void whenDeleteBranchApplySuccessChangesInExternalLists() {
        Map<Integer, ArrayList<Node>> waysToWalk = this.initWays(MATRIX_DATA.getStarter());
        ArrayList<ArrayList<Node>> waysWithGoal = new ArrayList<>();
        int nextIndex = 1;

        Mockito.when(wayFinderRule.findNextNodes(any(MatrixData.class), any())).thenThrow(new EndOfTheWayException());
        int resultIndex = mazeService.recursiveFinder(MATRIX_DATA, waysToWalk, waysWithGoal, nextIndex);
        assertEquals(waysWithGoal.size(), 0);
        assertEquals(waysToWalk.size(), 0);
        assertEquals(nextIndex, resultIndex);
    }

    @Test
    public void whenReturnGoalAtTheFirstTimeThenReturnOneWay() {
        Mockito.when(wayFinderRule.findNextNodes(any(MatrixData.class), any())).thenThrow(new GoalException());
        List<List<int[]>> result = mazeService.resolve(MATRIX_DATA);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).size(), 1);
    }

    private Map<Integer, ArrayList<Node>> initWays(Node node) {
        final Map<Integer, ArrayList<Node>> ways = new HashMap<>();

        ArrayList<Node> firstWay = Lists.newArrayList(node);
        ways.put(0, firstWay);
        return ways;
    }



}
