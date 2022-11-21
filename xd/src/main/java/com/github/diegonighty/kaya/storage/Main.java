package com.github.diegonighty.kaya.storage;

import org.mongojack.JacksonMongoCollection;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    static void as(JacksonMongoCollection<TestEntity> collection) {
        Flux.create(sink -> collection.find().forEach(sink::next));

        collection.find().into(new ArrayList<>());
    }
}