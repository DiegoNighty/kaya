package com.github.diegonighty.kaya.storage.error;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;

import javax.lang.model.type.TypeMirror;

public class ReturnTypeError extends RuntimeException {

    public ReturnTypeError(RepositoryMethodElement specification, Class<?> clazz) {
        super(specification.name() + " must return a " + clazz.getCanonicalName());
    }

    public ReturnTypeError(RepositoryMethodElement specification, TypeMirror entityClazz) {
        super(specification.name() + " must return a " + entityClazz.toString());
    }
}
