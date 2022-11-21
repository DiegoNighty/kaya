package com.github.diegonighty.kaya.storage.mongo.repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@Target(ElementType.TYPE)
public @interface MongoRepositoryAnnotation {
}
