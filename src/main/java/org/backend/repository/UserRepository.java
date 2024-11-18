package org.backend.repository;



import org.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

   Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    //TODO updateUserById? (params)
    //Optional<User>putUserById(long userId);

}
