package com.mazeWay.util;

import com.mazeWay.exception.InvalidMazeException;
import com.mazeWay.model.MatrixData;
import com.mazeWay.model.node.Direction;
import com.mazeWay.model.node.Node;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

import static com.mazeWay.mocks.Mocks.ACCESS_CHAR;
import static com.mazeWay.mocks.Mocks.GOAL_LOCATION;
import static com.mazeWay.mocks.Mocks.NODE;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(JUnitParamsRunner.class)
public class MazeMapperTest {

    @Test
    public void whenUseDefaultMazeThenReturnMatrixData() {
        char[][] defaultMatrix = MazeConstructor.getDefaultMaze();
        MatrixData matrixData = MazeMapper.getMatrixDataByArray(defaultMatrix, ACCESS_CHAR);
        final Node expectedNode = NODE;
        final int[] expectedGoal = GOAL_LOCATION;
        assertArrayEquals(defaultMatrix, matrixData.getMazeMatrix());
        assertArrayEquals(matrixData.getGoal(), expectedGoal);
        assertArrayEquals(matrixData.getStarter().getLocation(), expectedNode.getLocation());
        assertEquals(matrixData.getStarter().getRepeated(), expectedNode.getRepeated());
        assertEquals(matrixData.getStarter().getFrom(), expectedNode.getFrom());
        assertEquals(matrixData.getStarter().getChar(), expectedNode.getChar());
    }

    @Test
    @Parameters({"LEFT,5,0",
            "RIGHT,5,12",
            "UP,0,5",
            "DOWN,12,5"})
    public void allCasesWhenGetDirectionToStartByCoordinates(String expected, int y, int x) {
        Direction result = MazeMapper.getDirectionToStartByCoordinates(new int[]{y, x}, 12);
        assertEquals(Direction.valueOf(expected), result);
    }

    @Test(expected = InvalidMazeException.class)
    public void whenUseAMazeWithMoreTwoAccessThenFail() {
        char[][] invalidMatrix = new char[][]{
                {'A', ACCESS_CHAR, 'A'},
                {'A', 'C', ACCESS_CHAR},
                {'A', ACCESS_CHAR, 'C'},
        };
        MazeMapper.getMatrixDataByArray(invalidMatrix, ACCESS_CHAR);
    }

    @Test(expected = InvalidMazeException.class)
    public void whenUseAMazeWithoutSameSizeInAllRows() {
        char[][] invalidMatrix = new char[][]{
                {'A', ACCESS_CHAR},
                {'A', 'C', ACCESS_CHAR}
        };
        MazeMapper.getMatrixDataByArray(invalidMatrix, ACCESS_CHAR);
    }

}
