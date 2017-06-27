package fr.miage.restfull.dao;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.miage.restfull.entities.Address;
import fr.miage.restfull.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Collection<Address> findAddressByUsername(String username);
}
