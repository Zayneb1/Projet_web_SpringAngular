package com.codezayneb.ecom.dto;

import com.codezayneb.ecom.enums.UserRole;
import lombok.Data;

@Data
public class SignupRequest {

    private String email;

    private String password;

    private String name;


    //private UserRole role;
   // private byte[] img;

}
