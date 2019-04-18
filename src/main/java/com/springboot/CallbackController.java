package com.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/callback")
public class CallbackController {

	@GetMapping
	public String callback() {
		return "Success";
	}
	
}
