package com.bdo.pruebaTecnica.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
@Getter
@Setter
public class Incidence {

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum Status {
        OPEN,
        IN_PROGRESS,
        CLOSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "user_id")
    private Long userId;

    @PrePersist
    private void setCreationDate() {
        if (dateCreated == null) {
            dateCreated = LocalDateTime.now();
        }
    }
}