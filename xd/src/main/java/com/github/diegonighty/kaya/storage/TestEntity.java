package com.github.diegonighty.kaya.storage;

public record TestEntity(String id) {

    public String getId() {
        return id;
    }

    public record Nested(NestedNested nested) {

        public NestedNested getNested() {
            return nested;
        }

        public record NestedNested(int value) {
            public int getValue() {
                return value;
            }
        }

    }

}
