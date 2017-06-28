package fr.miage.restfull.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.miage.restfull.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	public List<Address> findByState(String state);
	public Optional<Address> findByStateAndStreetAndCityAndCountryAndZip(String state, String street, String city, String country, String zip);
}
