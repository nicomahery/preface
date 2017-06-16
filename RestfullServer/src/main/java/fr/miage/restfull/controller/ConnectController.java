package fr.miage.restfull.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectController {
	
	@RequestMapping("/connect")
	public boolean getUser(@RequestParam(value="id") long id){
		
		return true;
	}
}
