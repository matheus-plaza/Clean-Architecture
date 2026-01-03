package io.github.matheusplaza.clean_architecture.infra.config;

import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;
import io.github.matheusplaza.clean_architecture.core.useCases.CreateEventCase;
import io.github.matheusplaza.clean_architecture.core.useCases.CreateEventCaseImpl;
import io.github.matheusplaza.clean_architecture.core.useCases.GetEventByIdCase;
import io.github.matheusplaza.clean_architecture.core.useCases.GetEventByIdCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateEventCase createEventCase(EventGateway eventGateway) {
        return new CreateEventCaseImpl(eventGateway);
    }

    @Bean
    public GetEventByIdCase getEventByIdCase(EventGateway eventGateway) {
        return new GetEventByIdCaseImpl(eventGateway);
    }
}
