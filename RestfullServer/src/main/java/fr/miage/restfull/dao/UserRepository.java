package fr.miage.restfull.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.miage.restfull.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
