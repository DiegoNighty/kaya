package com.github.diegonighty.person;

import com.github.diegonighty.kaya.storage.mongo.repository.MongoRepository;
import com.github.diegonighty.kaya.storage.mongo.repository.MongoRepositoryAnnotation;
import com.github.diegonighty.person.Person.Credential;

import java.util.List;
import java.util.UUID;

@MongoRepositoryAnnotation
public interface PersonRepository extends MongoRepository<Person, UUID> {

    List<Person> findManyByCredentialName(Credential credential);

    List<Person> findManyByCredentialNameOrId(Credential credential, UUID id);

    long deleteManyByIdOrCredentialNameAndCredentialFolio(Credential credential, UUID id);

    Person findOneByCredentialName(Credential credential);

}
