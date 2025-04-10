package com.codezayneb.ecom.services.auth;


import com.codezayneb.ecom.dto.SignupRequest;
import com.codezayneb.ecom.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);

}
