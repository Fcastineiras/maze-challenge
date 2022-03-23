package com.mazeWay.service;

import com.mazeWay.exception.EndOfTheWayException;
import com.mazeWay.exception.GoalException;
import com.mazeWay.model.MatrixData;
import com.mazeWay.model.node.Element;
import com.mazeWay.model.node.Node;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Created by Fiamma on 2/5/2021.
 */
@Service
public class MazeService {

    private WayFinderRule wayFinderRule;

    @Autowired
    public MazeService(WayFinderRule wayFinderRule) {
        this.wayFinderRule = wayFinderRule;
    }

    public List<List<int[]>> resolve(MatrixData matrixData) {
        Map<Integer, ArrayList<Node>> waysToWalk = initWays(matrixData.getStarter());
        ArrayList<ArrayList<Node>> waysWithGoal = new ArrayList<>();
        int nextIndex = 1;
        while (waysToWalk.size() != 0) {
            nextIndex = recursiveFinder(matrixData, waysToWalk, waysWithGoal, nextIndex);
            nextIndex++;
        }

        return waysWithGoal.stream().map(nodes -> nodes.stream()
                .map(Element::getLocation).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    int recursiveFinder(MatrixData matrixData,
                                 Map<Integer, ArrayList<Node>> waysToWalk,
                                 ArrayList<ArrayList<Node>> waysWithGoal,
                                 int nextIndex) {
        ArrayList<Map.Entry<Integer, ArrayList<Node>>> tempWays = Lists.newArrayList(waysToWalk.entrySet());
        for (Map.Entry<Integer, ArrayList<Node>> way : tempWays) {

            try {
                Stack<Node> nextNodes = wayFinderRule.findNextNodes(matrixData, way.getValue());
                nextIndex = insertNewNodes(nextNodes, waysToWalk, waysToWalk.get(way.getKey()), nextIndex);

            } catch (EndOfTheWayException e) {
                waysToWalk.remove(way.getKey());
            } catch (GoalException e) {
                waysWithGoal.add(way.getValue());
                waysToWalk.remove(way.getKey());
            }
        }
        return nextIndex;
    }

     Map<Integer, ArrayList<Node>> initWays(Node node) {
        final Map<Integer, ArrayList<Node>> ways = new HashMap<>();

        ArrayList<Node> firstWay = Lists.newArrayList(node);
        ways.put(0, firstWay);
        return ways;
    }

    int insertNewNodes(Stack<Node> nextNodes,
                               Map<Integer,
                               ArrayList<Node>> waysToWalk,
                               ArrayList<Node> way,
                               int nextIndex) {
        for (int i = 1; i <= nextNodes.size() && nextNodes.size() > 1; i++) {
            ArrayList<Node> newWay = Lists.newArrayList(way);
            newWay.add(nextNodes.pop());
            waysToWalk.put(nextIndex + 1, newWay);
            nextIndex++;
        }
        way.add(nextNodes.pop());
        return nextIndex;
    }
}
