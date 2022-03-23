package com.mazeWay.service;

import com.mazeWay.exception.EndOfTheWayException;
import com.mazeWay.exception.GoalException;
import com.mazeWay.exception.NoWayException;
import com.mazeWay.model.node.Direction;
import com.mazeWay.model.MatrixData;
import com.mazeWay.model.node.Node;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

@Service
public class WayFinderRule {

    private int requiredRepeatedChar;

    @Autowired
    public WayFinderRule(int requiredRepeatedChar) {
        this.requiredRepeatedChar = requiredRepeatedChar;
    }

    public Stack<Node> findNextNodes(MatrixData matrixData, List<Node> actualWay)
            throws EndOfTheWayException, GoalException {

        Node node = Iterables.getLast(actualWay); // get last node in the way

        if (Arrays.equals(node.getLocation(), matrixData.getGoal())) {
            throw new GoalException();
        }

        Stack<Node> nodes = new Stack<>();
        for (Direction direction :
                Direction.values()) {
            if (direction != node.getFrom()) { // go to all different directions than from
                try {
                    Node foundNode = findNextNodeByDirection(matrixData, node, direction, actualWay);
                    nodes.add(foundNode);
                } catch (NoWayException e) {
                    //do nothing
                }
            }
        }

        if (nodes.isEmpty()) {
            throw new EndOfTheWayException();
        }

        return nodes;
    }

    private Node findNextNodeByDirection(MatrixData matrixData, Node node, Direction goToDirection,
                                         List<Node> blockedCoordinates) throws NoWayException {
        int[] newCoordinate = newCoordinatesByDirection(node, goToDirection);
        char newChar = matrixData.getCharByCoordinates(newCoordinate); // validate out of range
        Direction from = goToDirection.getOpposite();
        int repeatCounter = ifIsValidCharGetRepeat(node, newChar); // validate pattern
        if (blockedCoordinates.stream().map(Node::getLocation)
                .anyMatch(n -> Arrays.equals(n, newCoordinate))) { //validate if node was used after
            throw new NoWayException();
        }
        return new Node(newCoordinate, newChar, from, repeatCounter);
    }

    private int[] newCoordinatesByDirection(Node node, Direction direction) {
        int x = node.getX();
        int y = node.getY();
        switch (direction) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }
        return new int[]{y,x};
    }

    int generateNewCountChar(int repeated) { // reset after three times
        repeated++;
        if (repeated == requiredRepeatedChar) {
            return 0;
        }
        return repeated;
    }

    private int ifIsValidCharGetRepeat(Node node, char newElementChar) throws NoWayException {
        if (((node.getRepeated() == 0 && node.getChar() != newElementChar)
                || (node.getChar() == newElementChar && node.getRepeated() != 0))) {
            return generateNewCountChar(node.getRepeated());
        }
        throw new NoWayException();
    }
}
