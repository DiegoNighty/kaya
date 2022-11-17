package com.github.diegonighty.kaya.storage.processor;

import com.github.diegonighty.kaya.CommonProcessor;
import com.github.diegonighty.kaya.Tuple;
import com.github.diegonighty.kaya.storage.processor.element.CompletedRepositoryElement;
import com.github.diegonighty.kaya.storage.processor.element.RepositoryElement;
import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;
import com.github.diegonighty.kaya.storage.query.io.PrintContext;
import com.github.diegonighty.kaya.storage.query.io.PrintableQuery;
import com.github.diegonighty.kaya.storage.query.QueryFactory;
import com.github.diegonighty.kaya.storage.repository.ReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public abstract class RepositoryProcessor<T extends Annotation> extends CommonProcessor<T> {

    private void handle0(CompletedRepositoryElement element) {
        var queryFactory = getFactory();
        var repositoryElement = element.repository();

        var entityClazz = repositoryElement.entityClazz();

        if (!hasMethod(entityClazz, "getId")) {
            throw new RuntimeException(entityClazz.toString() + " must have an id getter called getId");
        }

        var parentElement = repositoryElement.clazz();
        var newClazzName = parentElement.getSimpleName().toString() + "Impl";

        List<Tuple<String, String>> variables = List.of(
                Tuple.of("{parent}", parentElement.getQualifiedName().toString()),
                Tuple.of("{clazz_name}", newClazzName),
                Tuple.of("{package_name}", GENERATED_PACKAGE),
                Tuple.of("{entity_type}", entityClazz.toString()),
                Tuple.of("{id_type}", element.repository().idClazz().toString())
        );

        copyFromTemplate(
                getTemplatePath(),
                newClazzName,
                (reader, writer) -> {
                    pathReader(
                            reader, variables
                    ).forEach(writer::println);

                    for (RepositoryMethodElement elementMethod : element.methods()) {
                        StringBuilder builder = new StringBuilder();

                        PrintableQuery printableQuery = queryFactory.create(elementMethod);
                        printableQuery.print(new PrintContext(elementMethod, builder, "collection"));

                        writer.println(builder);
                    }

                    writer.println("}");
                });
    }

    @Override
    protected void handle(Set<? extends Element> elements, RoundEnvironment environment) {
        for (Element element : elements) {
            handle0(completed(createRepository(element)));
        }
    }

    private CompletedRepositoryElement completed(RepositoryElement element) {
        var methodElements = element.clazz().getEnclosedElements()
                .stream()
                .filter(this::isMethod)
                .map(ExecutableElement.class::cast)
                .map(this::createRepositoryMethod)
                .toList();

        return new CompletedRepositoryElement(element, methodElements);
    }

    private RepositoryMethodElement createRepositoryMethod(ExecutableElement element) {
        var returnType = element.getReturnType();

        if (!isAssignableWithoutWildcard(returnType, Mono.class) && !isAssignableWithoutWildcard(returnType, Flux.class)) {
            throw new IllegalArgumentException(element + " must return a Mono or Flux");
        }

        return new RepositoryMethodElement(
                element.getSimpleName(),
                element.getReturnType(),
                element.getParameters()
        );
    }

    private RepositoryElement createRepository(Element annonymousElement) {
        var repositoryImplementationElement = (TypeElement) annonymousElement;
        var repositoryTypeElement = repositoryImplementationElement.getInterfaces()
                .stream()
                .filter(this::isReactiveRepository)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Repository must implement ReactiveRepository"));

        var declaredType = (DeclaredType) repositoryTypeElement;

        return new RepositoryElement(
                repositoryImplementationElement,
                (TypeElement) declaredType.asElement(),
                declaredType.getTypeArguments().get(0),
                declaredType.getTypeArguments().get(1)
        );
    }

    private boolean isReactiveRepository(TypeMirror typeMirror) {
        return isChild(typeMirror, fromClass(ReactiveRepository.class));
    }

    protected abstract QueryFactory getFactory();

    protected abstract String getTemplatePath();

}
