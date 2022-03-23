package com.mazeWay.controller;

import com.mazeWay.model.MatrixData;
import com.mazeWay.service.MazeService;
import com.mazeWay.util.MazeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.mazeWay.util.MazeConstructor.getDefaultMaze;

/**
 * Created by Fiamma on 2/5/2021.
 */
@Controller
public class MazeController {

    @Autowired
    private MazeService mazeService;

    @Value("${access.char}")
    private char accessChar;

    @GetMapping(path = "/maze-resolver")
    public ResponseEntity mazeResolver() {
        final MatrixData matrixData = MazeMapper.getMatrixDataByArray(getDefaultMaze(), accessChar);
        final List<List<int[]>> result = mazeService.resolve(matrixData);
        return ResponseEntity.ok(result);
    }
}