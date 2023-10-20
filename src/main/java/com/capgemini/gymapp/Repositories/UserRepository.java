package com.capgemini.gymapp.Repositories;

import com.capgemini.gymapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);


    boolean existsByEmail(String email);
    User findByActivationToken(String activationToken);
    @Query(value = """
  select u from User u left join u.tokens t 
  where t.token = :token and (t.expired = false or t.revoked = false)
  """)
    Optional<User> findUserByToken(@Param("token") String token);




}
