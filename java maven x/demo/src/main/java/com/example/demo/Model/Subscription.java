package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"subscriber_id", "creator_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id", nullable = false)
    private User subscriber;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    private Double price = 0.0;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public enum Status {
        ACTIVE, CANCELLED, EXPIRED
    }
}



