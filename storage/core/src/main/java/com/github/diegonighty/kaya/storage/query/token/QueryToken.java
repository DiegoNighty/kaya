package com.github.diegonighty.kaya.storage.query.token;

public record QueryToken(Type type, String value) {

    public enum Type {
        METHOD, // find, delete, save
        QUANTITY, // one, many, all (optional, default: one)
        DECORATOR, //By|For
        FIELD, // value
        OPERATOR, // or, and (optional)
    }

}
