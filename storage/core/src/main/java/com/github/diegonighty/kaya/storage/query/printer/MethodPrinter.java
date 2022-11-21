package com.github.diegonighty.kaya.storage.query.printer;

import com.github.diegonighty.kaya.storage.processor.element.RepositoryMethodElement;

public record MethodPrinter(StringBuilder builder) {

    public static MethodPrinter empty() {
        return new MethodPrinter(new StringBuilder());
    }

    static final String END_LINE = System.lineSeparator();
    static final String END_LINE_CODE = ";";
    static final String OVERRIDE = "@Override";
    static final String SPACE = " ";
    static final String QUOTE = "\"";
    static final String RETURN = "return";
    static final String TAB = "\t";
    static final String START_BRACKET = "{";
    static final String END_BRACKET = "}";
    static final String START_PARENTHESIS = "(";
    static final String END_PARENTHESIS = ")";

    public MethodPrinter printStartParenthesis() {
        return print(START_PARENTHESIS);
    }

    public MethodPrinter printEndParenthesis() {
        return print(END_PARENTHESIS);
    }

    public MethodPrinter printEndLine() {
        return print(END_LINE).printTab();
    }

    public MethodPrinter printReturn() {
        return print(RETURN).printSpace();
    }

    public MethodPrinter printStartBracket() {
        return print(START_BRACKET).printEndLine();
    }

    public MethodPrinter printEndBracket() {
        return print(END_BRACKET).printEndLine();
    }

    public MethodPrinter printTab() {
        return print(TAB);
    }

    public MethodPrinter printSpace() {
        return print(SPACE);
    }

    public MethodPrinter printQuote() {
        return print(QUOTE);
    }

    public MethodPrinter printOverride() {
        return print(OVERRIDE);
    }

    public MethodPrinter printEndLineCode() {
        return print(END_LINE_CODE).printEndLine();
    }

    public MethodPrinter print(String str) {
        builder.append(str);
        return this;
    }

    public MethodPrinter print(Object obj) {
        builder.append(obj);
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    public MethodPrinter printMethodSignature(RepositoryMethodElement element) {
        return printOverride()
                .printEndLine()
                .print("public")
                .printSpace()
                .print(element.returnType())
                .printSpace()
                .print(element.name())
                .printStartParenthesis()
                .print(element.parameters().stream()
                        .map(parameter -> parameter.asType() + SPACE + parameter.getSimpleName())
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("")
                )
                .printEndParenthesis()
                .printSpace()
                .printStartBracket();
    }
}
