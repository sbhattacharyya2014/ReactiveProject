package com.subhadip.practice.reactive.fluxMonoTest;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoCombineTest {

	@Test
	public void combineFluxUsingMerge() {
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");
		Flux<String> mergedFlux = Flux.merge(flux1, flux2);
		StepVerifier.create(mergedFlux.log()).expectSubscription().expectNext("A", "B", "C", "D", "E", "F")
				.verifyComplete();
	}

	@Test
	public void combineFluxUsingMergeWithDelay() {
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
		Flux<String> mergedFlux = Flux.merge(flux1, flux2);
		StepVerifier.create(mergedFlux.log()).expectSubscription().expectNext("A", "B", "C", "D", "E", "F")
				.verifyComplete();
		// This test case will fail as flux merge will not wait - as it do paralell execution
		// output may be A,D,B,E,C,F or D,A,E,B,F,C so order is not maintained

	}

	@Test
	public void combineFluxUsingConcatWithDelay() {
		Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
		Flux<String> mergedFlux = Flux.concat(flux1, flux2);
		StepVerifier.create(mergedFlux.log()).expectSubscription().expectNext("A", "B", "C", "D", "E", "F")
				.verifyComplete();
		// This test case will pass as flux concat will wait for all element to be
		// completed - as it do not do paralell execution

	}
	@Test
	public void combineFluxUsingZip() {
		Flux<String> flux1 = Flux.just("A", "B", "C");
		Flux<String> flux2 = Flux.just("D", "E", "F");
		Flux<String> mergedFlux = Flux.zip(flux1, flux2, (a1,b1)->{
			return a1.concat(b1);
			}); // Zip will return AD, BE, CF
		StepVerifier.create(mergedFlux.log()).expectSubscription().expectNext("AD", "BE", "CF")
				.verifyComplete();
	}
}
