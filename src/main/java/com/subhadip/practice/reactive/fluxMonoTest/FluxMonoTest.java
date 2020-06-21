package com.subhadip.practice.reactive.fluxMonoTest;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxMonoTest {

	@Test
	public void fluxTest() {
		Flux<String> strFlux=Flux.just("Subhadip", "Rahul","Karan");
		strFlux.subscribe(System.out::println, 
				e->{System.err.println(e);});
	}
	@Test
	public void fluxTestElementsWitoutError() {
		Flux<String> nameFlux=Flux.just("Subhadip", "Rahul","Karan","Soumya","Agniv","Sayan")
				.filter(name->name.length()==5)
				.map(String::toUpperCase).log();
		
		//Verifying Flux stream
		StepVerifier.create(nameFlux)
		.expectNext("RAHUL")
		.expectNextMatches(name->name.startsWith("KAR"))
		.expectNext("AGNIV","SAYAN")
		.expectComplete()
		.verify();
		
		//Finding count
		StepVerifier.create(nameFlux).expectNextCount(4).verifyComplete();
		
	}
	
	@Test
	public void fluxTestElementsWithError() {
		Flux<String> nameFlux=Flux.just("Subhadip", "Rahul","Karan","Soumya","Agniv","Sayan")
				.filter(name->name.length()==5)
				.map(String::toUpperCase).concatWith(Flux.error(new RuntimeException("Runtime Exception"))).log();
		StepVerifier.create(nameFlux)
		.expectNext("RAHUL")
		.expectNextMatches(name->name.startsWith("KAR"))
		.expectNext("AGNIV","SAYAN")
		.expectError(RuntimeException.class)
		.verify();
		
		//Verifying error message
		StepVerifier.create(nameFlux)
		.expectNext("RAHUL")
		.expectNextMatches(name->name.startsWith("KAR"))
		.expectNext("AGNIV","SAYAN")
		.expectErrorMessage("Runtime Exception")
		.verify();
	}
	@Test
	public void monoTest() {
		Mono<String> nameMono=Mono.just("Subhadip").log();
		StepVerifier.create(nameMono)
		.expectNext("Subhadip")
		.verifyComplete();
	}
	@Test
	public void monoTestWitError() {
		Mono<Object> nameMono=Mono.error(new RuntimeException("Runtime Exception came")).log();
		StepVerifier.create(nameMono)
		.expectError(RuntimeException.class)
		.verify();
	}	
	
	
}
