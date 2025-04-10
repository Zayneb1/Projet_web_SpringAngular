package com.codezayneb.ecom.entity;

import com.codezayneb.ecom.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "users")
@Getter
@Setter

public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;


    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")

    private byte[] img;

}
