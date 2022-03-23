package com.mazeWay.util;

import com.mazeWay.exception.InvalidMazeException;
import com.mazeWay.model.node.Direction;
import com.mazeWay.model.node.Node;
import com.mazeWay.model.MatrixData;

import java.util.ArrayList;

import static com.mazeWay.model.MatrixData.X;
import static com.mazeWay.model.MatrixData.Y;
import static com.mazeWay.util.MazeValidator.isValidAccessCharCount;
import static com.mazeWay.util.MazeValidator.isValidRowWidth;

/**
 * Created by Fiamma on 1/5/2021.
 */
public class MazeMapper {

    private MazeMapper() {
        //do nothing
    }

    public static MatrixData getMatrixDataByArray(char[][] maze, char accessChar) throws InvalidMazeException {
        final int mazeWidth = maze.length;
        final int yLimit = mazeWidth - 1;
        final int mazeLength = maze[0].length;
        final int xLimit = mazeLength -1;
        ArrayList<int[]> accessCoordinates = new ArrayList<>();
        //used two loops because if maze is rectangular
        for (int y = 0; y < mazeLength; y++) {
            isValidRowWidth(maze, y, mazeWidth);
            addCoordinatesIfIsAMazeAccess(maze, accessCoordinates, y, 0, accessChar);
            addCoordinatesIfIsAMazeAccess(maze, accessCoordinates, y, xLimit, accessChar);
        }
        for (int x = 1; x < mazeWidth - 1; x++) {
            addCoordinatesIfIsAMazeAccess(maze, accessCoordinates, 0, x, accessChar);
            addCoordinatesIfIsAMazeAccess(maze, accessCoordinates, yLimit, x, accessChar);
        }
        isValidAccessCharCount(accessCoordinates);
        return new MatrixData(maze, getStarterNode(accessCoordinates.get(0), xLimit, accessChar), accessCoordinates.get(1));
    }

    private static void addCoordinatesIfIsAMazeAccess(char[][] maze, ArrayList<int[]> accessCoordinates, int y, int x, char accessChar) {
        if (maze[y][x] == accessChar) {
            accessCoordinates.add(new int[]{y, x});
        }
    }

    private static Node getStarterNode(int[] startCoordinates, int xLimit, char accessChar) {
        final Direction from = getDirectionToStartByCoordinates(startCoordinates, xLimit);
        return new Node(startCoordinates, accessChar, from, 0);
    }

    static Direction getDirectionToStartByCoordinates(int[] elementCoordinates, int xLimit) {
        final int y = elementCoordinates[Y];
        final int x = elementCoordinates[X];

        if (x == 0) {
            return Direction.LEFT;
        } else if (x == xLimit) {
            return Direction.RIGHT;
        } else if (y == 0) {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }
}