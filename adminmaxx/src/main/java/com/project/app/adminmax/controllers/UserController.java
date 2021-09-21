package com.project.app.adminmax.controllers;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.project.app.adminmax.domain.User;
import com.project.app.adminmax.domain.CustomLog;
import com.project.app.adminmax.domain.LoginRequest;
import com.project.app.adminmax.services.UserService;
import java.util.Optional;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author suzy
 */
@RestController
@RequestMapping("/api/v2/user")
@Slf4j

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity save(@RequestBody User user) {
        Optional<User> saveUser = userService.registration(user);
        if (saveUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(saveUser);
        }
        return ResponseEntity.status(400).body(saveUser);
    }

    
    @GetMapping(path = "/findAll/registered/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> findAll() {
        return (List<User>) userService.findAll();
    }

    
    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        Optional<?> loginUser = userService.login(request.getEmail(), request.getPassword());
        if (loginUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(loginUser);
        }
        return ResponseEntity.status(400).body(loginUser);
    }

    
    @PostMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout() {
        boolean logoutUser = userService.logout();
        return ResponseEntity.status(HttpStatus.OK).body(logoutUser);
    }

    
    @DeleteMapping("/delete/byId")
    public ResponseEntity delete(@RequestParam String Id) {
        String deleteById = userService.deleteById(Id);
        if (deleteById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Give id");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfullly deleted");
    }

    
    @DeleteMapping("/deleteAll/users")
    public ResponseEntity deleteAll() {
        userService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("All users successfullly have been deleted");
    }

    
    @PutMapping(path = "/change/fields", produces = MediaType.APPLICATION_JSON_VALUE)
    public User put(@RequestBody User user) {
        return userService.update(user);
    }

    
    @GetMapping("/findbyId")
    public ResponseEntity findById(@RequestParam String Id) {
        Optional<?> findById = userService.findById(Id);
        if (findById.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(findById);
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Something goes wrong");
    }

    
    @GetMapping(path = "/findAll/logs/sortByDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomLog> findAllLogs(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam String sortBy) {
        return userService.findAllEventLogs(pageNo, pageSize, sortBy);
    }
    
    
    @GetMapping(path = "admin/see/Top10eventLogs/orderByDateDesc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomLog> findTop10ByOrderByDateDesc() {
        return userService.findTop10ByOrderByDateDesc();
    }

}
