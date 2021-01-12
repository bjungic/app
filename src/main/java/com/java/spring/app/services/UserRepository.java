package com.java.spring.app.services;

import com.java.spring.app.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select * from users where username = ?1", nativeQuery = true)
    User getUserByUsername(String username);
}
