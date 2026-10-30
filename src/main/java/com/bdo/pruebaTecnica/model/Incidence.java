package com.bdo.pruebaTecnica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
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

    @Column(columnDefinition = "TEXT")
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
