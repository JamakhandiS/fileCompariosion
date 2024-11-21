package com.compair.File.WebController;

import org.springframework.web.bind.annotation.GetMapping;

public class WebController {
  
	@GetMapping("/")
	public String home() {
		return "index" ;
	}
}
