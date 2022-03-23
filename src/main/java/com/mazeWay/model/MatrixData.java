package com.mazeWay.model;

import com.mazeWay.exception.NoWayException;
import com.mazeWay.model.node.Node;

public class MatrixData {

    public static final int Y = 0;
    public static final int X = 1;
    private final char[][] mazeMatrix;
    private final Node starter;
    private final int[] goal;

    public MatrixData(char[][] mazeMatrix, Node starter, int[] goal) {
        this.mazeMatrix = mazeMatrix;
        this.starter = starter;
        this.goal = goal;
    }

    public char[][] getMazeMatrix() {
        return mazeMatrix;
    }

    public Node getStarter() {
        return starter;
    }

    public int[] getGoal() {
        return goal;
    }

    public char getCharByCoordinates(int[] coordinates) throws NoWayException {
        try {
            return this.mazeMatrix[coordinates[Y]][coordinates[X]];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoWayException();
        }
    }
}
