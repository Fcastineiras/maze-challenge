package com.mazeWay.mocks;

import com.mazeWay.model.node.Direction;
import com.mazeWay.model.MatrixData;
import com.mazeWay.model.node.Node;
import com.mazeWay.util.MazeConstructor;

public class Mocks {

    public static char ACCESS_CHAR = 'B';

    public static Node NODE = new Node(new int[]{10,11}, ACCESS_CHAR, Direction.RIGHT, 0);

    public static int[] GOAL_LOCATION = new int[]{0,1};

    public static MatrixData MATRIX_DATA = new MatrixData(MazeConstructor.getDefaultMaze(), NODE, GOAL_LOCATION);
}
