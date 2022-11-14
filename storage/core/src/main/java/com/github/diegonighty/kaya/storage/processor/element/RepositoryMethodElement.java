package com.github.diegonighty.kaya.storage.processor.element;

import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public record RepositoryMethodElement(
        Name name,
        TypeMirror returnType,
        List<? extends VariableElement> parameters
) { }
