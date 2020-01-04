package com.movies.stream.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.movies.stream.handler.HttpRequestHandler;
import com.movies.stream.security.JwtTokenProvider;

@Controller
public class StreamController {
	
    private static final Logger logger = LoggerFactory.getLogger(StreamController.class);
	
	@Autowired
	private HttpRequestHandler handler;
	
	 @Value("${app.video.url}")
	 private String url;
	 
	private File MP4_FILE;

	// supports byte-range requests
	@GetMapping("/index")
	public String home(String year, String id, boolean active) {
		MP4_FILE=new File(url+year+"/"+id+".mp4");
		if (active) {
		return "index";
		}
		return "no_access";
	}

	// 
	//@PreAuthorize("hasRole('USER')")
	@GetMapping("/byterange")
	public void byterange( HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		logger.info("Movie starts:"+MP4_FILE);
		
		request.setAttribute(HttpRequestHandler.ATTR_FILE, MP4_FILE);
		handler.handleRequest(request, response);
		
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}


}
