# kaya-storage
Auto-Generated entity repository inspired by [spring-data](https://github.com/spring-projects/spring-data-commons)

## Installation
**Kaya requires java 16 or higher to run**

With Gradle: 
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.kaya-framework:kaya-storage-<IMPLEMENTATION>:<VERSION>'
}
```

## Getting Started
Create a class that represents your entity **with getId method** (if you don't add getId method, an exception will be thrown)
the id can be any type, but it must be serializable

```java
public record Person(UUID id) {}
```

Create an interface that extends an implementation of [**repository**](https://github.com/DiegoNighty/kaya/blob/main/storage/core/src/main/java/com/github/diegonighty/kaya/storage/repository/Repository.java)
for example [**MongoRepository**](https://github.com/DiegoNighty/kaya/blob/main/storage/mongo/src/main/java/com/github/diegonighty/kaya/storage/mongo/repository/MongoRepository.java)
and annotate it with [@MongoRepositoryAnnotation](https://github.com/DiegoNighty/kaya/blob/main/storage/mongo/src/main/java/com/github/diegonighty/kaya/storage/mongo/repository/MongoRepositoryAnnotation.java)
```java
@MongoRepositoryAnnotation
public interface PersonRepository extends MongoRepository<Person, UUID> {

}
```
if we leave the interface without any method, the CRUD methods will be created by default
```java
public class PersonRepositoryImpl implements com.github.diegonighty.person.PersonRepository {

    private final JacksonMongoCollection<com.github.diegonighty.person.Person> collection;

    public PersonRepositoryImpl(JacksonMongoCollection<com.github.diegonighty.person.Person> collection) {
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

}
```

but if we want to create our own methods, we can do it
using the valid [**query methods**](https://github.com/DiegoNighty/kaya/tree/main/storage/mongo/readme.md)
and **filters** from [**mongodb-driver**](https://mongodb.github.io/mongo-java-driver/3.6/javadoc/com/mongodb/client/model/Filters.html)
```java
@MongoRepositoryAnnotation
public interface PersonRepository extends MongoRepository<Person, UUID> {
    List<Person> findManyByName(String name);
}
```

be sure to add the name to Person entity

```java
public record Person(UUID id, String name) {}
```

## Accessing to nested objects
if we want to access to nested objects we can do it with the method name
for example, you want to access to the credential name of the person
```java
public class Person {
    private final UUID id;
    private final String name;

    public Person(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Credential {
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

```

in the repository we can do it
```java
@MongoRepositoryAnnotation
public interface PersonRepository extends MongoRepository<Person, UUID> {
    List<Person> findManyByCredentialName(Credential credential);
}
```
the generated query will be
```java
	@Override
	public java.util.List<com.github.diegonighty.person.Person> findManyByCredentialName(com.github.diegonighty.person.Person.Credential credential) {
		return collection.find(Filters.eq("name", credential.getName())).into(new ArrayList<>());
	}
```

## Filters
if we want to use filters from [**mongodb-driver**](https://mongodb.github.io/mongo-java-driver/3.6/javadoc/com/mongodb/client/model/Filters.html)
we can do it
```java
@MongoRepositoryAnnotation
public interface PersonRepository extends MongoRepository<Person, UUID> {
    List<Person> findManyByCredentialNameOrId(Credential credential, UUID id);
}
```
the generated query will be

```java
@Override
public java.util.List<com.github.diegonighty.person.Person> findManyByCredentialNameOrId(com.github.diegonighty.person.Person.Credential credential, java.util.UUID id) {
	return collection.find(Filters.or(Filters.eq("name", credential.getName()), Filters.eq("id", id))).into(new ArrayList<>());
}
```

we can use **and** filters too
```java
@MongoRepositoryAnnotation
public interface PersonRepository extends MongoRepository<Person, UUID> {
	long deleteManyByIdOrCredentialNameAndCredentialFolio(Credential credential, UUID id);
}
```
the generated query will be
```java
@Override
public long deleteManyByIdOrCredentialNameAndCredentialFolio(com.github.diegonighty.person.Person.Credential credential, java.util.UUID id) {
	return collection.deleteMany(Filters.or(Filters.eq("id", id), Filters.and(Filters.eq("name", credential.getName()), Filters.eq("folio", credential.getFolio())))).getDeletedCount();
}
```

if we want to find first entity we can do it
```java
@MongoRepositoryAnnotation
public interface PersonRepository extends MongoRepository<Person, UUID> {
    Person findOneByCredentialName(Credential credential);
}
```
the generated query will be
```java
@Override
public com.github.diegonighty.person.Person findOneByCredentialName(com.github.diegonighty.person.Person.Credential credential) {
	return collection.find(Filters.eq("name", credential.getName())).first();
}
```

full example [**here**](https://github.com/DiegoNighty/kaya/blob/main/test-storage/src/main/java/com/github/diegonighty/person/GeneratedPersonRepositoryImpl.java)



