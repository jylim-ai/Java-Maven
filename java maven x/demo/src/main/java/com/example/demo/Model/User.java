package com.example.demo.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

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

    @Column(name = "uuid", nullable = false, updatable = false)
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

    @OneToMany(mappedBy = "creator")
    private List<Plan> planList;

    @OneToMany(mappedBy = "subscriber")
    private List<Subscription> subscriptionList;

}
