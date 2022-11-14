package com.github.diegonighty.kaya;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public abstract class CommonProcessor<A extends Annotation> extends AbstractProcessor {

    public static final String GENERATED_PACKAGE = "com.github.diegonighty.kaya.generated";

    protected abstract List<Class<? extends A>> annotations();

    protected abstract void handle(Set<? extends Element> elements, RoundEnvironment environment);

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty() || roundEnv.errorRaised() || roundEnv.processingOver()) {
            return false;
        }

        for (var annotation : annotations()) {
            handle(
                    roundEnv.getElementsAnnotatedWith(annotation),
                    roundEnv
            );
        }

        return true;
    }

    protected void copyFromTemplate(String template, String newName, BiConsumer<BufferedReader, PrintWriter> actions) {
        try (var templateStream = getClass().getClassLoader().getResourceAsStream(template)) {
            if (templateStream == null) {
                throw new RuntimeException("Template not found: " + template);
            }

            try (var reader = new BufferedReader(new InputStreamReader(templateStream))) {
                try (var writer = new PrintWriter(filer().createSourceFile(newName).openWriter())) {
                    actions.accept(reader, writer);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Stream<String> pathReader(BufferedReader reader, List<Tuple<String, String>> var) {
        return reader.lines()
                .map(line -> {
                    for (Tuple<String, String> tuple : var) {
                        line = line.replace(tuple.first(), tuple.second());
                    }

                    return line;
                });
    }

    protected boolean isMethod(Element element) {
        return is(element, ElementKind.METHOD);
    }

    protected boolean isField(Element element) {
        return is(element, ElementKind.FIELD);
    }

    protected boolean is(Element element, ElementKind kind) {
        return element.getKind() == kind;
    }

    protected boolean isChild(TypeMirror interfaceChild, TypeMirror toEqual) {
        var declaredType = (DeclaredType) interfaceChild;
        var element = (TypeElement) declaredType.asElement();

        var toEqualType = (DeclaredType) toEqual;
        var toEqualName = toEqualType.asElement().getSimpleName().toString();

        return element.getInterfaces().stream()
                .map(parentInterface -> (DeclaredType) parentInterface)
                .map(DeclaredType::asElement)
                .map(Element::getSimpleName)
                .map(Name::toString)
                .anyMatch(name -> name.equals(toEqualName));
    }

    protected TypeMirror fromClass(Class<?> clazz) {
        return processingEnv.getElementUtils().getTypeElement(clazz.getCanonicalName()).asType();
    }

    protected Messager messager() {
        return processingEnv.getMessager();
    }

    protected Filer filer() {
        return processingEnv.getFiler();
    }

    protected Elements elementUtils() {
        return processingEnv.getElementUtils();
    }

    protected boolean isAssignable(TypeMirror type, Class<?> clazz) {
        return processingEnv.getTypeUtils().isAssignable(type, fromClass(clazz));
    }

    protected boolean isAssignableWithoutWildcard(TypeMirror type, Class<?> clazz) {
        var declaredType = (DeclaredType) type;
        var typeName = declaredType.asElement().getSimpleName();

        return typeName.toString().equals(clazz.getSimpleName());
    }

    protected boolean hasMethod(TypeMirror type, String methodName) {
        var declaredType = (DeclaredType) type;
        var element = (TypeElement) declaredType.asElement();

        return element.getEnclosedElements().stream()
                .filter(this::isMethod)
                .map(Element::getSimpleName)
                .map(Name::toString)
                .anyMatch(name -> name.equals(methodName));
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return annotations().stream()
                .map(Class::getName)
                .collect(toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_17;
    }
}
