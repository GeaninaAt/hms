package com.hms.ws;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by gatomulesei on 8/9/2017.
 */
@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class AuthenticationEndpoint {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }
}
