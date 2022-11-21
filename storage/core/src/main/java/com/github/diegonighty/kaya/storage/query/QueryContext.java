package com.github.diegonighty.kaya.storage.query;

public record QueryContext(
        String method,
        ReturnQuantity returnType,
        String filter
) {

    public String bodyIdentifier() {
        return method + returnType.definition();
    }

    public static QueryContext create(String method, ReturnQuantity qt, String filter) {
        return new QueryContext(method, qt, filter);
    }

    public static QueryContext create(String method, String returnType, String filter) {
        return create(method, ReturnQuantity.valueOf(returnType.toUpperCase()), filter);
    }

    public enum ReturnQuantity {

        ONE,
        MANY;

        public String definition() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }

        @Override
        public String toString() {
            return definition();
        }
    }

}
