package com.mazeWay.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

/**
 * Created by Fiamma on 7/5/2021.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenGetResultForDefaultMaze() throws Exception {
        final ArrayList result = this.restTemplate.getForObject("http://localhost:" + port + "/maze-resolver", ArrayList.class);
        assertEquals(24, result.size());
    }


    @Test
    public void whenGetHealth() throws Exception {
        final String result = this.restTemplate.getForObject("http://localhost:" + port + "/ping", String.class);
        assertEquals("pong", result);
    }
}
