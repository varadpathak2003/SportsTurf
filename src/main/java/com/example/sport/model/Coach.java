package com.example.sport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int experience;
    private String specialization;
    private String phoneNumber;
    private String email;
    private double salary;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

}
