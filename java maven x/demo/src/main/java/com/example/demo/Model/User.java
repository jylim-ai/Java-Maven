package com.example.demo.Model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = true, name = "uuid")
    private UUID uuid = UUID.randomUUID();

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String username;


    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    public enum Role {
        USER,
        CREATOR,
        ADMIN
    }

}
