package com.github.diegonighty.kaya.storage.mongo;

public record TestEntity(String id, String name) {

    public String getId() {
        return id;
    }

}
