package fr.miage.restfull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.miage.restfull.dao.AddressRepository;
import fr.miage.restfull.dao.UserRepository;
import fr.miage.restfull.entities.Address;
import fr.miage.restfull.entities.User;
import fr.miage.restfull.exception.UserNotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private final UserRepository userRepository;
	private final AddressRepository addressRepository;
	
	@Autowired
	public UserController(UserRepository userRepository, AddressRepository addressRepository) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username){
		try {
		this.validateUsername(username);
		return new ResponseEntity<User>(this.userRepository.findByUsername(username).get(), HttpStatus.OK);
		}
		catch(UserNotFoundException e){
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Error message", e.getMessage());
			return new ResponseEntity<User>(null, responseHeaders, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{username}/address")
	public ResponseEntity<Address> getUserAddress(@PathVariable String username){
		try {
			this.validateUsername(username);
			return new ResponseEntity<Address>(this.addressRepository.findOne(this.userRepository.findByUsername(username).get().getAddress().getId()), HttpStatus.OK);
		}
		catch (UserNotFoundException e){
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Error message", e.getMessage());
			return new ResponseEntity<Address>(null, responseHeaders, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> CreateUser(@RequestBody User user){
		this.addressRepository.save(user.getAddress());
		User u = this.userRepository.save(user);
		return new ResponseEntity<User>(u, HttpStatus.CREATED);
	}
	
	private void validateUsername(String username){
		this.userRepository.findByUsername(username).orElseThrow(()
				-> new UserNotFoundException(username));
	}
}
