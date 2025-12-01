package io.github.matheusplaza.clean_architecture.infra.persistence;

import io.github.matheusplaza.clean_architecture.core.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String identifier;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime finalDate;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    private String organizer;
    @Column(name = "img_url")
    private String imgUrl;
    @Enumerated(EnumType.STRING)
    private EventType type;
}
