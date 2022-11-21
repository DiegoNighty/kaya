package com.github.diegonighty.kaya.storage.error;

import javax.lang.model.type.TypeMirror;

public class TypeNeedMethodError extends RuntimeException {

    public TypeNeedMethodError(TypeMirror type, String method) {
        super(String.format("The type %s need the method %s", type.toString(), method));
    }

}
