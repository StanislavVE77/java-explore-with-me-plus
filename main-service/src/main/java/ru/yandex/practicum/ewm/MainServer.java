package ru.yandex.practicum.ewm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
@ComponentScan(value = {"ru.yandex.practicum.ewm"})
public class MainServer {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainServer.class, args);


        RestStatClient statClient = context.getBean(RestStatClient.class);
        statClient.hit(new EndpointHitCreateDto("ewm-main-service","/events/11","192.163.0.3", LocalDateTime.parse("2022-09-06T11:00:28")));

        statClient.getStats("2020-05-05 00:00:01", "2035-05-05 00:00:01", Set.of("/events/5","/events/11"), false);
    }
}
