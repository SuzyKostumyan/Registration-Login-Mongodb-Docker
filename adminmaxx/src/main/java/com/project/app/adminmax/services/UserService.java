/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.app.adminmax.services;

import com.project.app.adminmax.repositories.UserRepository;
import java.util.Optional;
import com.project.app.adminmax.domain.User;
import com.project.app.adminmax.domain.CustomLog;
import com.project.app.adminmax.enums.RoleType;
import com.project.app.adminmax.repositories.CustomLogRepository;
import com.project.app.adminmax.utils.PasswordHash;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;


/**
 *
 * @author suzy
 */
@Service
@Slf4j
public class UserService {
    
    @Autowired
    private CustomLogRepository customLogRepository;
    @Autowired
    private UserRepository userRepository;
    
    public boolean isLogin = false;
    private User findUser = new User();

    private User user = new User();
    private CustomLog customLog;
    
    public Optional<User> registration(User user) {
        log.info("Users registration");
        if (!userRepository.findByEmail(user.getEmail()).isPresent()) {

            if (user.getPassword().isEmpty()) {
                return Optional.empty();
            }
            user.setPassword(PasswordHash.hashPassword(user.getPassword()));
            user.setRole(RoleType.REGULAR.getValue());
            User registration = userRepository.save(user);
            customLog = new CustomLog();
            customLog.setMessage("Success Registered at " + customLog.getDate());
            CustomLog save = customLogRepository.save(customLog);

            return Optional.ofNullable(registration);
        }

        log.info("This email already registered");
        return Optional.empty();
    }

    
    public Optional<?> login(String email, String password) {
        log.info("Users login");

        if (email == null || password == null) {
            log.info("Pls give email or password");
            return Optional.empty();
        }
        if (isLogin == true) {
            log.info("You are already logged in");
            return Optional.empty();
        }
        findUser = userRepository.findByEmailAndPassword(email, PasswordHash.hashPassword(password));

        if (findUser == null) {
            log.info("Try again or sign up");
            return Optional.empty();
        }

        if (findUser.getRole().equals("admin")) {
            log.info("I am admin");
            isLogin = true;
            customLog = new CustomLog();
            customLog.setMessage("Admin logged in");
            CustomLog save = customLogRepository.save(customLog);

            return Optional.ofNullable(findUser);
        }

        isLogin = true;
        log.info("User logged in the system");
        customLog = new CustomLog();
        customLog.setMessage("Success logged in at " + customLog.getDate());
        CustomLog save = customLogRepository.save(customLog);
        return Optional.ofNullable(findUser);

    }

    public boolean logout() {

        if (isLogin == false) {
            log.info("You are already logged out from system or not even logged in");
            return false;
        }
        log.info("logout");
        isLogin = false;
        customLog = new CustomLog();
        customLog.setMessage("Success logout at " + customLog.getDate());
        CustomLog save = customLogRepository.save(customLog);
        return true;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public String deleteById(String Id) {
        log.info("delete registered user by email");
        Optional<User> deleteById = userRepository.findById(Id);

        if (!deleteById.isPresent()) {
            return "User with this email is not registered";
        }
        if (deleteById.isPresent()) {
            userRepository.deleteById(Id);
            return Id;
        }
        return null;
    }

    public User update(User user) {
        log.info("for changing fields");

        user.setRole(RoleType.REGULAR.getValue());
        if (user.getId() != null) {
            return userRepository.save(user);
        } else {
            log.error("For update please provide Id");
            return new User();
        }
    }

    public Optional<?> findById(String Id) {
        log.info("find user by id");
        Optional<User> findById = userRepository.findById(Id);

        if (!findById.isPresent()) {
            return Optional.empty();
        }
        return findById;
    }

    public List<CustomLog> findAllEventLogs(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<CustomLog> pagedResult = customLogRepository.findAll(paging);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<CustomLog>();
        }
    }

    public List<CustomLog> findTop10ByOrderByDateDesc() {
        if (findUser.getRole().equals("admin")) {
            log.info("You are admin and can see latest event logs");
            return customLogRepository.findTop10ByOrderByDateDesc();
        }
        log.info("You aren't admin for seeing latest event logs");
        return Collections.emptyList();
    }

    public RoleType[] getRoleTypes() {
        return RoleType.values();
    }
}
