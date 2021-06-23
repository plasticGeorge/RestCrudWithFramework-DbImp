package com.george.repositories;

import com.george.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query(
            value = "SELECT * FROM USER u LEFT JOIN TASK t ON u.USER_ID = t.USER_ID WHERE u.USER_ID = :id",
            nativeQuery = true)
    Optional<User> fullInformation(@Param("id") long id);
}
