package io.github.matheusplaza.clean_architecture.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    boolean existsByIdentifier(String identifier);
}
