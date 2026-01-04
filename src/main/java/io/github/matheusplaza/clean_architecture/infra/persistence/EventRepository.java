package io.github.matheusplaza.clean_architecture.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    boolean existsByIdentifier(String identifier);

    Optional<EventEntity> findByIdentifier(String identifier);
}
