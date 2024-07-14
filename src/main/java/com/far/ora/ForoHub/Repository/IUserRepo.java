package com.far.ora.ForoHub.Repository;

import com.far.ora.ForoHub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IUserRepo extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.profile.username = :username")
    UserDetails findByUsername(String username);

    List<User> findAll();
}
