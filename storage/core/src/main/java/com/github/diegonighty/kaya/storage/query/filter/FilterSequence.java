package com.github.diegonighty.kaya.storage.query.filter;

public interface FilterSequence {

    String build();

    void sequence(SequenceContext context, TokenFinder finder);

    interface TokenFinder {
        Token find(int index);
    }

    record SequenceContext(
            Token currentToken,
            Token nextToken
    ) {

        public boolean hasNext() {
            return nextToken != null;
        }

    }

    record Token(String term, int index, boolean isOperator) {

        public static Token from(String token, int index, boolean isOperator) {
            if (token == null) {
                return null;
            }

            return new Token(token, index, isOperator);
        }

        @Override
        public String toString() {
            return term.toLowerCase();
        }
    }

}
