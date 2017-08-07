package com.hms.service;

import com.hms.domain.User;

import java.util.List;

/**
 * Created by gatomulesei on 8/7/2017.
 */
public interface UserService {

    User findById(Long id);

    User findByUsername(String username);

    User saveUser(User user);

    void deleteUser(Long id);

    List<User> findAllUsers();

    boolean isUserExist(User user);
}