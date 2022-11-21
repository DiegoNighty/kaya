package com.github.diegonighty.person;

import com.mongodb.client.model.Filters;
import org.mongojack.JacksonMongoCollection;

import java.util.ArrayList;
import java.util.List;

public class GeneratedPersonRepositoryImpl implements com.github.diegonighty.person.PersonRepository {

    private final JacksonMongoCollection<com.github.diegonighty.person.Person> collection;

    public GeneratedPersonRepositoryImpl(JacksonMongoCollection<com.github.diegonighty.person.Person> collection) {
        this.collection = collection;
    }

    @Override
    public long save(com.github.diegonighty.person.Person entity) {
        return collection.save(entity).getModifiedCount();
    }

    @Override
    public void saveAll(List<com.github.diegonighty.person.Person> entities) {
        collection.insert(entities);
    }

    @Override
    public com.github.diegonighty.person.Person findById(java.util.UUID s) {
        return collection.findOneById(s);
    }

    @Override
    public boolean existsById(java.util.UUID s) {
        return collection.findOneById(s) != null;
    }

    @Override
    public List<com.github.diegonighty.person.Person> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public long count() {
        return collection.countDocuments();
    }

    @Override
    public long deleteById(java.util.UUID s) {
        return collection.removeById(s).getDeletedCount();
    }

    @Override
    public long delete(com.github.diegonighty.person.Person entity) {
        return collection.removeById(entity.getId()).getDeletedCount();
    }

    @Override
    public long deleteAll(List<? extends com.github.diegonighty.person.Person> entities) {
        return collection.deleteMany(Filters.all("_id", entities.stream().map(com.github.diegonighty.person.Person::getId).toArray())).getDeletedCount();
    }

    @Override
    public long deleteAllByIds(List<java.util.UUID> ids) {
        return collection.deleteMany(Filters.all("_id", ids.toArray())).getDeletedCount();
    }

    @Override
    public void deleteAll() {
        collection.drop();
    }

    @Override
    public java.util.List<com.github.diegonighty.person.Person> findManyByCredentialName(com.github.diegonighty.person.Person.Credential credential) {
        return collection.find(Filters.eq("name", credential.getName())).into(new ArrayList<>());
    }

    @Override
    public java.util.List<com.github.diegonighty.person.Person> findManyByCredentialNameOrId(com.github.diegonighty.person.Person.Credential credential, java.util.UUID id) {
        return collection.find(Filters.or(Filters.eq("name", credential.getName()), Filters.eq("id", id))).into(new ArrayList<>());
    }

    @Override
    public long deleteManyByIdOrCredentialNameAndCredentialFolio(com.github.diegonighty.person.Person.Credential credential, java.util.UUID id) {
        return collection.deleteMany(Filters.or(Filters.eq("id", id), Filters.and(Filters.eq("name", credential.getName()), Filters.eq("folio", credential.getFolio())))).getDeletedCount();
    }

    @Override
    public com.github.diegonighty.person.Person findOneByCredentialName(com.github.diegonighty.person.Person.Credential credential) {
        return collection.find(Filters.eq("name", credential.getName())).first();
    }

}
