package fr.mahery.preface.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.mahery.preface.dao.AddressRepository;
import fr.mahery.preface.dao.UserRepository;
import fr.mahery.preface.entities.Address;
import fr.mahery.preface.entities.User;
import fr.mahery.preface.utilities.AuthObj;
import fr.mahery.preface.exception.UserNotFoundException;
import fr.mahery.preface.exception.UserPasswordIncorrectException;

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
	public ResponseEntity<User> createUser(@RequestBody User user){
		Address add = this.validateOrSaveAddress(user.getAddress());
		user.setAddress(add);
		User u = this.userRepository.save(user);
		return new ResponseEntity<User>(u, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable String username){
		try {
			this.validateUsername(username);
			this.userRepository.delete(this.userRepository.findByUsername(username).get());
			return new ResponseEntity<String>("Deletion complete" , HttpStatus.ACCEPTED);
		}
		catch (UserNotFoundException e){
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Error message", e.getMessage());
			return new ResponseEntity<String>("Deletion aborded", responseHeaders, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/{username}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String username){
		try {
			this.validateUsername(username);
			User u = this.userRepository.findByUsername(username).get();
			user.setId(u.getId());
			Address add = user.getAddress();
			user.setAddress(this.validateOrSaveAddress(add));
			this.userRepository.save(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		catch (UserNotFoundException e){
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Error message", e.getMessage());
			return new ResponseEntity<User>(null, responseHeaders, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value="/connect")
	public ResponseEntity<User> connectUser(@RequestBody AuthObj authObj){
		try {
			this.validateUsername(authObj.getUsername());
			User u = this.userRepository.findByUsername(authObj.getUsername()).get();
			this.validatePassword(u, authObj);
				return new ResponseEntity<User>(u, HttpStatus.OK);
				
		}
		catch (UserNotFoundException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Error message", e.getMessage());
			return new ResponseEntity<User>(null, responseHeaders, HttpStatus.NOT_FOUND);
		}
		catch (UserPasswordIncorrectException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Error message", e.getMessage());
			return new ResponseEntity<User>(null, responseHeaders, HttpStatus.NOT_FOUND);
		}
	}
	
	private void validateUsername(String username){
		this.userRepository.findByUsername(username).orElseThrow(()
				-> new UserNotFoundException(username));
	}
	
	private Address validateOrSaveAddress(Address address){
		Address add = address;
		Optional<Address> result = this.addressRepository.findByStateAndStreetAndCityAndCountryAndZip(add.getState(), add.getStreet(), add.getCity(), add.getCountry(), add.getZip());
		if (!result.isPresent())
			add = this.addressRepository.save(address);
		else
			add = result.get();
		return add;
	}
	
	public void validatePassword(User u, AuthObj auth){
		if (! u.getPassword().equals(auth.getPassword())){
			throw new UserPasswordIncorrectException();
		}
	}
}
