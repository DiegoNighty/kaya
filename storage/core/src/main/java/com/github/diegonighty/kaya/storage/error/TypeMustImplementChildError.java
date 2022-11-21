package com.github.diegonighty.kaya.storage.error;

import javax.lang.model.element.Element;

public class TypeMustImplementChildError extends RuntimeException {

    public TypeMustImplementChildError(Element type, Class<?> mustImplement) {
        super(String.format("The type %s must implement %s child (NOT PARENT)", type.getSimpleName(), mustImplement.getName()));
    }

}
