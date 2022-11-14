package {package_name};

import com.github.diegonighty.kaya.storage.mongo.repository.MongoReactiveRepository;
import com.mongodb.client.model.Filters;
import org.mongojack.JacksonMongoCollection;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class {clazz_name} implements {parent} {

    private final JacksonMongoCollection<{entity_type}> collection;

    public {clazz_name}(JacksonMongoCollection<{entity_type}> collection) {
        this.collection = collection;
    }

    @Override
    public Mono<Long> save({entity_type} entity) {
        return Mono.fromSupplier(() -> collection.save(entity).getModifiedCount());
    }

    @Override
    public Mono<Void> saveAll(List<{entity_type}> entities) {
        return Mono.fromRunnable(() -> collection.insert(entities));
    }

    @Override
    public Mono<Void> saveAll(Publisher<List<{entity_type}>> publisher) {
        return Mono.from(publisher)
                .flatMap(this::saveAll);
    }

    @Override
    public Mono<{entity_type}> findById({id_type} s) {
        return Mono.fromSupplier(() -> collection.findOneById(s));
    }

    @Override
    public Mono<{entity_type}> findById(Publisher<{id_type}> id) {
        return Mono.from(id)
                .flatMap(this::findById);
    }

    @Override
    public Mono<Boolean> existsById({id_type} s) {
        return Mono.fromSupplier(() -> collection.findOneById(s) != null);
    }

    @Override
    public Mono<Boolean> existsById(Publisher<{id_type}> id) {
        return Mono.from(id)
                .flatMap(this::existsById);
    }

    @Override
    public Mono<List<{entity_type}>> findAll() {
        return Mono.fromSupplier(() -> collection.find().into(new ArrayList<>()));
    }

    @Override
    public Mono<Long> count() {
        return Mono.fromSupplier(collection::countDocuments);
    }

    @Override
    public Mono<Void> deleteById({id_type} s) {
        return Mono.fromRunnable(() -> collection.removeById(s));
    }

    @Override
    public Mono<Void> deleteById(Publisher<{id_type}> id) {
        return Mono.from(id)
                .flatMap(this::deleteById);
    }

    @Override
    public Mono<Void> delete({entity_type} entity) {
        return Mono.fromRunnable(() -> collection.removeById(entity.getId()));
    }

    @Override
    public Mono<Void> deleteAll(List<? extends {entity_type}> entities) {
        return Mono.fromRunnable(() -> collection.deleteMany(Filters.all("_id", entities.stream().map({entity_type}::getId).toArray())));
    }

    @Override
    public Mono<Void> deleteAllByIds(List<{id_type}> ids) {
        return Mono.fromRunnable(() -> collection.deleteMany(Filters.all("_id", ids.toArray())));
    }

    @Override
    public Mono<Void> deleteAll(Publisher<List<{entity_type}>> entityStream) {
        return Mono.from(entityStream)
                .flatMap(this::deleteAll);
    }

    @Override
    public Mono<Void> deleteAll() {
        return Mono.fromRunnable(collection::drop);
    }
