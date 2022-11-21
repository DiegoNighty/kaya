package {package_name};

import com.github.diegonighty.kaya.storage.mongo.repository.MongoRepository;
import com.mongodb.client.model.Filters;
import org.mongojack.JacksonMongoCollection;

import java.util.ArrayList;
import java.util.List;

public class {clazz_name} implements {parent} {

    private final JacksonMongoCollection<{entity_type}> collection;

    public {clazz_name}(JacksonMongoCollection<{entity_type}> collection) {
        this.collection = collection;
    }

    @Override
    public long save({entity_type} entity) {
        return collection.save(entity).getModifiedCount();
    }

    @Override
    public void saveAll(List<{entity_type}> entities) {
        collection.insert(entities);
    }

    @Override
    public {entity_type} findById({id_type} s) {
        return collection.findOneById(s);
    }

    @Override
    public boolean existsById({id_type} s) {
        return collection.findOneById(s) != null;
    }

    @Override
    public List<{entity_type}> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public long count() {
        return collection.countDocuments();
    }

    @Override
    public long deleteById({id_type} s) {
        return collection.removeById(s).getDeletedCount();
    }

    @Override
    public long delete({entity_type} entity) {
        return collection.removeById(entity.getId()).getDeletedCount();
    }

    @Override
    public long deleteAll(List<? extends {entity_type}> entities) {
        return collection.deleteMany(Filters.all("_id", entities.stream().map({entity_type}::getId).toArray())).getDeletedCount();
    }

    @Override
    public long deleteAllByIds(List<{id_type}> ids) {
        return collection.deleteMany(Filters.all("_id", ids.toArray())).getDeletedCount();
    }

    @Override
    public void deleteAll() {
        collection.drop();
    }

