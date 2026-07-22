package com.fitness.userservice.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Data
public class User {

   @Id
   @GeneratedValue (strategy = jakarta.persistence.GenerationType.UUID)
   private String id;
    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String FirstName;

    private String LastName;

    @Enumerated(EnumType.STRING)
    private UserRole userRole= UserRole.USER;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
