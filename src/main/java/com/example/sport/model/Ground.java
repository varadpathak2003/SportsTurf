package com.example.sport.model;

import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ground")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ground {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String address;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private String groundType; // Indoor/Outdoor
    private int playerCapacity;
    private String surfaceType; // Grass, Turf, etc.
    private boolean floodlightsAvailable;
    private boolean changingRooms;
    private LocalTime openingTime; // New field for opening time
    private LocalTime closingTime;

    private String imageFileName;
    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = true)
    private Coach coach ;  // Selected Coach

}