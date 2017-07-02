package fr.mahery.preface.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.mahery.preface.dao.AddressRepository;
import fr.mahery.preface.entities.Address;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	private final AddressRepository addressRepository;
	
	@Autowired
	public AddressController(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}
	
	@RequestMapping("/{addressId}")
	public ResponseEntity<Address> getAddress(@PathVariable long addressId){
		return new ResponseEntity<Address>(this.addressRepository.findOne(addressId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Address> createAddress(@RequestBody Address address) {
		return new ResponseEntity<Address>(addressRepository.save(address), HttpStatus.OK);
	}
	
}
