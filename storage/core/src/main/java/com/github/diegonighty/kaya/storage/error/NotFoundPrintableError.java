package com.github.diegonighty.kaya.storage.error;

public class NotFoundPrintableError extends RuntimeException {

    public NotFoundPrintableError(String bodyIdentifier, String moduleName) {
        super(String.format("Not found printable for body %s in module %s", bodyIdentifier, moduleName));
    }

}
