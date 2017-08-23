package com.hms.ws;

import com.hms.domain.User;
import com.hms.repository.UserRepository;
import com.hms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by gatomulesei on 8/7/2017.
 */
@RestController
@RequestMapping(value = "/users")
@CrossOrigin(origins = "http://localhost:8000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void postInit(){
        if(userRepository.findByUsername("admin") == null) {
            final User defaultUser = new User();
            defaultUser.setUsername("admin");
            defaultUser.setPassword("admin");
            defaultUser.setFirstName("admin");
            defaultUser.setLastName("admin");
            defaultUser.setEmail("admin@admin.com");
            defaultUser.setRole("ROLE_ADMIN");
            defaultUser.setEnabled(true);
            userRepository.save(defaultUser);
            System.out.println("Default admin added.");
        } else {
            System.out.println("Default admin exists. Skipping creation");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers(){
        List<User> users = userService.findAllUsers();
        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        System.out.println("Fetching user with id : " + id);
        User user = userService.findById(id);
        if(user == null) {
            System.out.println("User with id " + id + "not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating user : " + user.getUsername());

        if(userService.isUserExist(user)) {
            System.out.println("User with username " + user.getUsername() + " already exists!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        System.out.println("Updating user " + id);

        User currentUser = userService.findById(id);

        if(currentUser == null) {
            System.out.println("User with id " + id + " was not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(user.getUsername() != null) {
            currentUser.setUsername(user.getUsername());
        }
        if(user.getPassword() != null) {
            currentUser.setPassword(user.getPassword());
        }

        userService.saveUser(currentUser);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        System.out.println("Fetching and deleting user with id " + id);

        User user = userService.findById(id);
        if(user == null) {
            System.out.println("Unable to delete. User with id " + id + " was not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
