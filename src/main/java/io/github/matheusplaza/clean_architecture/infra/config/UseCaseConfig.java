package io.github.matheusplaza.clean_architecture.infra.config;

import io.github.matheusplaza.clean_architecture.core.gateway.EventGateway;
import io.github.matheusplaza.clean_architecture.core.useCases.CreateEventCase;
import io.github.matheusplaza.clean_architecture.core.useCases.CreateEventCaseImpl;
import io.github.matheusplaza.clean_architecture.core.useCases.FindEventCase;
import io.github.matheusplaza.clean_architecture.core.useCases.FindEventCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateEventCase createEventCase(EventGateway eventGateway) {
        return new CreateEventCaseImpl(eventGateway);
    }

    @Bean
    public FindEventCase findEventCase() {
        return new FindEventCaseImpl();
    }
}
