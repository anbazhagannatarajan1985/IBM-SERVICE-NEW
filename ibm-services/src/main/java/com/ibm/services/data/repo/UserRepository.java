package com.ibm.services.data.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.services.data.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
    List<User> findAllByUserType(String type);

}