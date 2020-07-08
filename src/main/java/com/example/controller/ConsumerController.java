package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class ConsumerController {

	@Autowired
	WebClient.Builder webClientBuilder;
	
	@GetMapping("/health")
	public String health() {
		return "Consumer Working!!!";
	}
	
	@PostMapping("/perspost")
	public Person pers(@RequestBody Person per){
		return webClientBuilder.build().post().uri("http://localhost:8081/perspost")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(per)
			.retrieve().bodyToMono(Person.class).block();
			//	.syncBody(per).retrieve().bodyToMono(Person.class).block();
	}
	
	@PostMapping("/persmono")
	public Mono<Person> persmono(@RequestBody Person per){
		
	Person b = webClientBuilder.build().post().uri("http://localhost:8081/hellopost")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(per)
				.retrieve().bodyToMono(Person.class).block();
			//	.syncBody(per).retrieve().bodyToMono(Person.class).block();
	
		return Mono.just(b);
	}
	
}
