package com.github.diegonighty.kaya.storage;

import com.github.diegonighty.kaya.storage.mongo.repository.MongoRepository;
import com.github.diegonighty.kaya.storage.mongo.repository.MongoRepositoryAnnotation;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.UUID;

@MongoRepositoryAnnotation
public interface TestRepository extends MongoRepository<TestEntity, String> {

    TestEntity findOneByName(String name);

    List<TestEntity> findManyByName(String name);

    TestEntity findOneByNestedNestedValue(TestEntity.Nested nested);

    TestEntity findOneByUserNameOrUuidAndAge(UserPrincipal user, UUID uuid, int age);

    TestEntity findOneByUuidOrCurpOrNameAndLastname(String uuid, String name, String lastname, String curp);

}
