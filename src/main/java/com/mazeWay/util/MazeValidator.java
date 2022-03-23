package com.mazeWay.util;

import com.mazeWay.exception.InvalidMazeException;

import java.util.ArrayList;

/**
 * Created by Fiamma on 1/5/2021.
 */
class MazeValidator {

    private MazeValidator() {
        //do nothing
    }

    static void isValidRowWidth(char[][] maze, int y, int firstWidth) throws InvalidMazeException {
        if(maze[y].length  != firstWidth) { // if all rows have not same size
            throw new InvalidMazeException();
        }
    }

    static void isValidAccessCharCount(ArrayList<int[]> accessCoordinates) throws InvalidMazeException{
        if (accessCoordinates.size() != 2) { // if exists more two access in the maze
            throw new InvalidMazeException();
        }
    }
}
