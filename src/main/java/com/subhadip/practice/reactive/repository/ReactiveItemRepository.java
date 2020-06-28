package com.subhadip.practice.reactive.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.subhadip.practice.reactive.document.Item;

public interface ReactiveItemRepository extends ReactiveMongoRepository<Item, String> {

}
