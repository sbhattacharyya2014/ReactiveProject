package com.subhadip.practice.reactive.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FluxAndMonoController {
	
	@GetMapping("/flux")
	public Flux<Integer> returnFlux(){
		return Flux.range(1, 4).delayElements(Duration.ofSeconds(1)).log();
	}
	
	@GetMapping(value="/fluxStream" , produces=MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Integer> returnFluxStream(){
		return Flux.range(1, 4).delayElements(Duration.ofSeconds(1)).log();
	}
	
	@GetMapping(value="/fluxInterval" , produces=MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Long> returnInfiniteFluxUsingInterval(){
		return Flux.interval(Duration.ofSeconds(1)).log();
	}
	@GetMapping("/mono")
	public Mono<Integer> returnMono(){
		return Mono.just(1).log();
	}

}
