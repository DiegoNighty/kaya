package com.github.diegonighty.kaya.storage.query;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;

import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;

public record PrintContext(RepositoryMethodElement method, StringBuilder builder, String sourceVariable) {

    static final String END_LINE = System.lineSeparator();
    static final String END_LINE_CODE = ";";
    static final String OVERRIDE = "@Override";
    static final String SPACE = " ";
    static final String QUOTE = "\"";
    static final String RETURN = "return";
    static final String TAB = "\t";

    public PrintContext printParameters() {
        List<String> parameters = new ArrayList<>();

        for (VariableElement parameter : method.parameters()) {
            parameters.add(parameter.asType() + " " + parameter.getSimpleName());
        }

        return print("(").print(String.join(", ", parameters)).print(")");
    }

    public PrintContext printTab() {
        return print(TAB);
    }

    public PrintContext startReturnMono() {
        return printReturn().print("Mono.fromRunnable(() -> ");
    }

    public PrintContext endReturn() {
        return print(")").endLineCode();
    }

    public PrintContext printReturn() {
        return print(RETURN).space();
    }

    public PrintContext printParameterName(int index) {
        List<String> parameters = new ArrayList<>();

        for (VariableElement parameter : method.parameters()) {
            parameters.add(parameter.getSimpleName().toString());
        }

        return print(parameters.get(index));
    }

    public PrintContext print(String text) {
        builder.append(text);
        return this;
    }

    public PrintContext quote() {
        return print(QUOTE);
    }

    public PrintContext print(Object obj) {
        builder.append(obj);
        return this;
    }

    public PrintContext printLine(String text) {
        builder.append(text).append(END_LINE);
        return this;
    }

    public PrintContext printOverride() {
        printTab();
        builder.append(OVERRIDE);
        return endLine();
    }

    public PrintContext space() {
        builder.append(SPACE);
        return this;
    }

    public PrintContext endLine() {
        builder.append(END_LINE);
        return printTab();
    }

    public PrintContext endLineCode() {
        builder.append(END_LINE_CODE).append(END_LINE);
        return printTab();
    }

}
