package com.subhadip.practice.reactive.fluxMonoTest;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoErrorTest {

	@Test
	public void fluxErrorHandlingOnErrorResume() {
		Flux<String> strFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException(" Runtime Exception occured"))).concatWith(Flux.just("D"))
				.onErrorResume((e) -> {
					// Error Handling
					System.out.println("Error " + e);
					return Flux.just("default1", "default2");
				});

//		StepVerifier.create(strFlux)
//		.expectNext("A","B","C")
//		.expectError()
//		.verify();

		StepVerifier.create(strFlux.log()).expectNext("A", "B", "C").expectNext("default1", "default2")
				.verifyComplete();
	}

	@Test
	public void fluxErrorHandlingOnErrorReturn() {
		Flux<String> strFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException(" Runtime Exception occured"))).concatWith(Flux.just("D"))
				.onErrorReturn("default");

		StepVerifier.create(strFlux.log()).expectNext("A", "B", "C").expectNext("default").verifyComplete();
	}

	@Test
	public void fluxErrorHandlingOnErrorMap() {
		Flux<String> strFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException(" Runtime Exception occured"))).concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e));

		StepVerifier.create(strFlux.log()).expectNext("A", "B", "C").expectError(CustomException.class).verify();
	}

	@Test
	public void fluxErrorHandlingOnErrorMapWithRetry() {
		Flux<String> strFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException(" Runtime Exception occured"))).concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e)).retry(2);

		StepVerifier.create(strFlux.log()).expectNext("A", "B", "C").expectNext("A", "B", "C").expectNext("A", "B", "C")
				.expectError(CustomException.class).verify();
	}
	
	@Test
	public void fluxErrorHandlingOnErrorMapWithRetryBackoff() {
		Flux<String> strFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException(" Runtime Exception occured"))).concatWith(Flux.just("D"))
				.onErrorMap((e) -> new CustomException(e)).retryBackoff(2,Duration.ofSeconds(5));

		StepVerifier.create(strFlux.log()).expectNext("A", "B", "C").expectNext("A", "B", "C").expectNext("A", "B", "C")
				.expectError(IllegalStateException.class).verify();
	}
}
