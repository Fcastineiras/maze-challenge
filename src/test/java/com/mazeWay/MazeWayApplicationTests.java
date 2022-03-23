package com.mazeWay;

import com.mazeWay.exception.RestResponseEntityExceptionHandler;
import com.mazeWay.service.MazeService;
import com.mazeWay.service.WayFinderRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MazeWayApplication.class)
public class MazeWayApplicationTests {

    @Autowired
    private RestResponseEntityExceptionHandler exceptionHandler;

    @Autowired
    private WayFinderRule wayFinderRule;
    @Autowired
    private MazeService mazeService;

    @Test
    public void contextLoads() {
        assertThat(wayFinderRule).isNotNull();
        assertThat(mazeService).isNotNull();
        assertThat(exceptionHandler).isNotNull();
    }

    @Test
    public void InvalidMazeException() {
        ResponseEntity responseEntity = exceptionHandler.handleBadRequest();
        assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

}
