/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.app.adminmax.repositories;

import java.util.Optional;
import com.project.app.adminmax.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author suzy
 */
@Transactional
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findById(String Id);

    User findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);

}
