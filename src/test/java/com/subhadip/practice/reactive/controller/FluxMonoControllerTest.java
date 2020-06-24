package com.subhadip.practice.reactive.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxMonoControllerTest {

	@Autowired
	WebTestClient webTestClient;

	@Test
	public void fluxControllerTest_approach1() {
		Flux<Integer> intFlux = webTestClient.get().uri("/flux").accept(MediaType.APPLICATION_JSON).exchange()
				.expectStatus().isOk().returnResult(Integer.class).getResponseBody();
		StepVerifier.create(intFlux).expectNext(1, 2, 3, 4).verifyComplete();

	}

	@Test
	public void fluxControllerTest_approach2() {
		webTestClient.get().uri("/flux").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON).expectBodyList(Integer.class).hasSize(4);
	}

	@Test
	public void InfiniteFluxControllerTest_approach1() {
		Flux<Long> infiniteFluxStream = webTestClient.get().uri("/fluxInterval")
				.accept(MediaType.APPLICATION_STREAM_JSON).exchange().expectStatus().isOk().returnResult(Long.class)
				.getResponseBody();
		StepVerifier.create(infiniteFluxStream).expectNext(0l, 1l, 2l, 3l).thenCancel().verify();

	}

	@Test
	public void monoTest() {
		Integer expectedResult = 1;
		webTestClient.get().uri("/mono").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
				.expectBody(Integer.class).consumeWith((val) -> {
					assertEquals(expectedResult, val.getResponseBody());
				});
		
	}

}
