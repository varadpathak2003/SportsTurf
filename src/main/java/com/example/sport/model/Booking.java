package com.example.sport.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long gameId;
    private Long groundId;
    private Long slotId;
    private int paymentStatus; // 0 = unpaid, 1 = paid
    
    @Column(name = "booking_date")
    private LocalDate bookingDate;
    
    @Column(name = "end_time")
    private LocalTime endTime;
    
    @Column(name = "start_time")
    private LocalTime startTime;
    
    
    @Column(name = "price")
    private Double price;
    
    
    @Column(nullable = true)
    private Integer rating; // 1 to 5 stars

    @Column(nullable = true)
    private String review; // Text feedback
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime bookingTimestamp;  // Stores the booking creation time

   
}