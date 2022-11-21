package com.github.diegonighty.kaya.processor.error;

public class TemplateNotFoundError extends RuntimeException {

    public TemplateNotFoundError(String template) {
        super("Template not found: " + template);
    }

}
