package com.github.diegonighty.kaya.storage;

import com.github.diegonighty.kaya.storage.TestEntity.Nested;
import com.github.diegonighty.kaya.storage.mongo.repository.MongoReactiveRepository;
import com.github.diegonighty.kaya.storage.mongo.repository.MongoRepository;
import reactor.core.publisher.Mono;

@MongoRepository
public interface TestRepository extends MongoReactiveRepository<TestEntity, String> {

    Mono<TestEntity> findOneByAgeOrName(int age, String name);

    Mono<TestEntity> findOneByUuidOrCurpOrNameAndLastname(String uuid, String name, String lastname, String curp);

    Mono<TestEntity> findOneByNestedNestedValue(Nested nested);

}
