package com.vvs.springreactiveh2;

import java.util.stream.Stream;

import com.vvs.springreactiveh2.model.Todo;
import com.vvs.springreactiveh2.repository.TodoRepository;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringReactiveH2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveH2Application.class, args);
	}

	@Bean
	ApplicationRunner init(TodoRepository repository, DatabaseClient client) {
		return args -> {
			client.sql("create table IF NOT EXISTS TODO (id SERIAL PRIMARY KEY, text varchar(255) not null, completed boolean default false);").fetch().first().subscribe();
			client.sql("DELETE FROM TODO;").fetch().first().subscribe();

			Stream<Todo> stream = Stream.of(
				new Todo(null, "Hi, this is my first todo!", false),
				new Todo(null, "This one I have acomplished!", true),
				new Todo(null, "And this is secret", false)
			);

			repository.saveAll(Flux.fromStream(stream))
				.then()
				.subscribe();
		};
	}

}
