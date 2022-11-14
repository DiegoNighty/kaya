package com.github.diegonighty.kaya.storage.processor.element;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public record RepositoryElement(
        TypeElement clazz,
        TypeElement repositoryType,
        TypeMirror entityClazz,
        TypeMirror idClazz
) {

    @Override
    public String toString() {
        return "RepositoryElement{" +
                "clazz=" + clazz +
                ", repositoryType=" + repositoryType +
                ", entityClazz=" + entityClazz +
                ", idClazz=" + idClazz +
                '}';
    }
}
