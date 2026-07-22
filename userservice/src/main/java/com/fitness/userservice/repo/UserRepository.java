package com.fitness.userservice.repo;

import com.fitness.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    // java.util.Optional<com.fitness.userservice.models.User> findByEmail(String email);
    boolean existsByEmail(String email);
}
