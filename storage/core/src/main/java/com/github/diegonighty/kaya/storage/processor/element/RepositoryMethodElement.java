package com.github.diegonighty.kaya.storage.processor.element;

import com.github.diegonighty.kaya.storage.processor.RepositoryProcessor;

import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

public record RepositoryMethodElement(
        Name name,
        TypeMirror returnType,
        List<? extends VariableElement> parameters,
        TypeChecker returnTypeChecker
) {

    public interface TypeChecker {
        CheckerResult is(TypeMirror clazz);

        CheckerResult is(Class<?> type);

        CheckerResult is(Class<?> type, Class<?>... wildcards);

        CheckerResult is(Class<?> type, TypeMirror... wildcards);

        record CheckerResult(TypeMirror expected, boolean flat) {
            public static CheckerResult from(boolean result, TypeMirror expected) {
                return new CheckerResult(expected, result);
            }

            public void ifNotThrow(ErrorSupplier exception) {
                if (!flat) {
                    throw exception.get(expected);
                }
            }

            public interface ErrorSupplier {
                RuntimeException get(TypeMirror expected);
            }
        }

        class DefaultChecker implements TypeChecker {
            private final TypeMirror mirror;
            private final RepositoryProcessor<?> processor;

            public DefaultChecker(TypeMirror mirror, RepositoryProcessor<?> processor) {
                this.mirror = mirror;
                this.processor = processor;
            }

            @Override
            public CheckerResult is(TypeMirror clazz) {
                return CheckerResult.from(processor.is(mirror, clazz), clazz);
            }

            @Override
            public CheckerResult is(Class<?> type) {
                return CheckerResult.from(processor.is(mirror, type), processor.fromClass(type));
            }

            @Override
            public CheckerResult is(Class<?> type, Class<?>... wildcards) {
                TypeMirror expected = processor.fromWildcardClass(type, wildcards);

                return CheckerResult.from(processor.isWithSameWildcards(mirror, expected), expected);
            }

            @Override
            public CheckerResult is(Class<?> type, TypeMirror... wildcards) {
                TypeMirror expected = processor.fromWildcardClass(type, wildcards);

                return CheckerResult.from(processor.isWithSameWildcards(mirror, expected), expected);
            }

        }
    }

}
