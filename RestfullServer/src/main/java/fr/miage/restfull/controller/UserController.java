package fr.miage.restfull.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.restfull.dao.AddressRepository;
import fr.miage.restfull.dao.UserRepository;
import fr.miage.restfull.entities.Address;
import fr.miage.restfull.entities.User;
import fr.miage.restfull.exception.UserNotFoundException;

@RestController
@RequestMapping("/user/{username}")
public class UserController {
	
	private final UserRepository userRepository;
	private final AddressRepository addressRepository;
	
	@Autowired
	public UserController(UserRepository userRepository, AddressRepository addressRepository) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public User getUser(@PathVariable String username){
		this.validateUsername(username);
		return this.userRepository.findByUsername(username);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/address")
	public Collection<Address> getUserAddress(@PathVariable String username){
		this.validateUsername(username);
		return this.userRepository.findAddressByUsername(username);
	}
	
	private void validateUsername(String username){
		this.userRepository.findByUsername(username);//.orElseThrow(()
				//-> new UserNotFoundException(username));
	}
}
