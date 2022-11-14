package com.github.diegonighty.kaya.storage;

import com.github.diegonighty.kaya.storage.mongo.repository.MongoReactiveRepository;
import com.github.diegonighty.kaya.storage.mongo.repository.MongoRepository;
import reactor.core.publisher.Mono;

@MongoRepository
public interface TestRepository extends MongoReactiveRepository<TestEntity, String> {

    Mono<TestEntity> find_name(String name);

    Mono<TestEntity> deleteOne_age(int age);

}
