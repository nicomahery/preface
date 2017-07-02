package fr.mahery.preface.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.mahery.preface.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Optional<Long> findAddressByUsername(String username);
}
