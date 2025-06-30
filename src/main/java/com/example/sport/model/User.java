package com.example.sport.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @Column(nullable = false)
    private String password;  // Stored as plain text (not encoded)

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String gender;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}