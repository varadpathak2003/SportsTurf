package com.example.sport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "slot")
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ground_id", nullable = false)
    private Ground ground;  // Reference to Ground entity (fetch ground name)

    private boolean availability; // True if available, False if booked

    private double price; // Price for Monday to Wednesday
    private double weekendPrice; // Price for Saturday & Sunday

    private LocalTime startTime; // Slot start time
    private LocalTime endTime;    // Slot end time
    @Column(nullable = true)
    private String breakTime;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;  // default: not deleted


}