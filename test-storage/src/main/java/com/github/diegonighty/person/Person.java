package com.github.diegonighty.person;

import java.util.UUID;

public class Person {
    private final UUID id;
    private final String name;

    public Person(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Credential {
        private String name;
        private int folio;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getFolio() {
            return folio;
        }

        public void setFolio(int folio) {
            this.folio = folio;
        }
    }
}
