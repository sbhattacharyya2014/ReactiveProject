package com.subhadip.practice.reactive.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.subhadip.practice.reactive.document.Item;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ReactiveItemRepositoryTest {

	List<Item> itemList = Arrays.asList(new Item(null, "iPhone 7", 35000.0), new Item(null, "Iphone 8", 42000.50),
			new Item(null, "iPhone X", 64999.99));

	@Autowired
	ReactiveItemRepository reactiveItemRepository;

	@Before
	public void setup() {
		reactiveItemRepository.deleteAll().thenMany(Flux.fromIterable(itemList)).flatMap(reactiveItemRepository::save)
				.doOnNext(item -> {
					System.out.println("Item added---" + item);
				}).blockLast();
	}

	@Test
	public void findAllItems() {

		StepVerifier.create(reactiveItemRepository.findAll()).expectSubscription().expectNextCount(3).verifyComplete();
	}

}
