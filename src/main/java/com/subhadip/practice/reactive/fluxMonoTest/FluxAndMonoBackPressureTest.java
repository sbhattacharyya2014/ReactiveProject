package com.subhadip.practice.reactive.fluxMonoTest;

import org.junit.Test;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackPressureTest {
	Flux<Integer> intFlux = Flux.range(1, 10).log();

	@Test
	public void fluxBackPressureTest() {

		StepVerifier.create(intFlux).expectSubscription().thenRequest(1).expectNext(1).thenRequest(3)
				.expectNext(2, 3, 4).thenRequest(2).expectNext(5, 6).thenCancel().verify();
	}

	@Test
	public void fluxBackPressure() {
		intFlux.subscribe((element) -> System.out.println("Element is " + element),
				(e) -> System.err.println("Error is " + e.getMessage()), () -> System.out.println("Done"),
				subsscription -> subsscription.request(2));
	}

	@Test
	public void fluxBackPressureCancel() {
		intFlux.subscribe((element) -> System.out.println("Element is " + element),
				(e) -> System.err.println("Error is " + e.getMessage()), () -> System.out.println("Done"),
				subsscription -> subsscription.cancel());
	}

	@Test
	public void customizedBackPressure() {
		intFlux.subscribe(new BaseSubscriber<Integer>() {
			@Override
			public void hookOnNext(Integer value) {
				request(1);
				System.out.println("value received " + value);
				if (value == 4)
					cancel();
			}
		});
	}
}
