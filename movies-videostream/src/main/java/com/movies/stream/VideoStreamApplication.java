package com.movies.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({ "com" })

public class VideoStreamApplication {	

	public static void main(String[] args) {
		SpringApplication.run(VideoStreamApplication.class, args);
	}	

}
