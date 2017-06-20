package fr.miage.restfull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.restfull.dao.AddressRepository;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	private final AddressRepository addressRepository;
	
	@Autowired
	public AddressController(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}
	
}
