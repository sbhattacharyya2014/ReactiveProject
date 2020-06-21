package com.subhadip.practice.reactive.fluxMonoTest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxMonoFactoryTest {
	List<String> nameList = Arrays.asList("Subhadip", "Rahul", "Karan", "Soumya", "Agniv", "Sayan");

	@Test
	public void fluxUsingIterable() {
		Flux<String> nameFlux = Flux.fromIterable(nameList);
		StepVerifier.create(nameFlux).expectNext("Subhadip", "Rahul", "Karan", "Soumya", "Agniv")
				.expectNextMatches(name -> name.startsWith("Say")).verifyComplete();
	}

	@Test
	public void fluxUsingStream() {
		Flux<String> nameFlux = Flux.fromStream(nameList.stream());
		StepVerifier.create(nameFlux).expectNext("Subhadip", "Rahul", "Karan", "Soumya", "Agniv")
				.expectNextMatches(name -> name.startsWith("Say")).verifyComplete();
	}

	@Test
	public void fluxUsingRange() {
		Flux<Integer> integerFlux = Flux.range(1, 10).filter(no -> no % 2 == 0).map(no -> no - 1);
		StepVerifier.create(integerFlux.log()).expectNext(1, 3, 5, 7, 9).verifyComplete();
	}

	@Test
	public void monoUsingJustOrEmpty() {
		Mono<String> emptyMono = Mono.justOrEmpty(null);
		StepVerifier.create(emptyMono).verifyComplete();
	}

	@Test
	public void monoUsingSupplier() {
		Supplier<String> strSupplier = () -> "Subhadip";
		Mono<String> supplierMono = Mono.fromSupplier(strSupplier);
		StepVerifier.create(supplierMono).expectNext("Subhadip").verifyComplete();
	}

}
