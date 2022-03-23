package com.mazeWay;

import com.mazeWay.service.MazeService;
import com.mazeWay.service.WayFinderRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MazeWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MazeWayApplication.class, args);
	}

	@Value("${required.repeated.char}")
	private int requiredRepeatedChar;

	@Value("${access.char}")
	private char accessChar;

	@Autowired
	private WayFinderRule wayFinderRule;

	@Bean
	public WayFinderRule wayFinderRule(){
		return new WayFinderRule(requiredRepeatedChar);
	}

	@Bean
	public MazeService mazeService(){
		return new MazeService(wayFinderRule);
	}

}
