package com.github.diegonighty.kaya.storage.query.filter;

public abstract class AbstractFilterSequence implements FilterSequence {

    static final int NEXT_INDEX = 1;

    protected final StringBuilder printer = new StringBuilder();

    private int operatorChanges = 0;
    private int skippedTo = -1;
    private boolean skipComma = false;
    private String previousOperator = "";

    private final String[] filters;

    public AbstractFilterSequence(String[] filters) {
        this.filters = filters;
    }

    @Override
    public String build() {
        TokenFinder finder = index -> {
            var term = filters[index];

            return Token.from(term, index, isOperator(term));
        };

        for (int index = 0; index < filters.length; index++) {
            if (index < skippedTo) {
                continue;
            }

            var currentTerm = filters[index];
            var currentToken = Token.from(currentTerm, index, isOperator(currentTerm));

            var nextIndex = index + NEXT_INDEX;
            var nextTerm = nextIndex < filters.length ? filters[nextIndex] : null;

            var nextToken = Token.from(nextTerm, nextIndex, isOperator(nextTerm));

            sequence(new SequenceContext(currentToken, nextToken), finder);
        }

        return printer.append(")".repeat(operatorChanges)).toString();
    }

    protected boolean isSafeIndex(int index) {
        return index >= 0 && index < filters.length;
    }

    protected boolean isMaxIndex(int index) {
        return index == filters.length;
    }

    protected void updateOperator(Token previousOperator) {
        operatorChanges++;
        this.previousOperator = previousOperator.term();
    }

    protected boolean isOperatorChange(Token operator) {
        return !previousOperator.equals(operator.term());
    }

    protected void skipComma(boolean skipComma) {
        this.skipComma = skipComma;
    }

    protected boolean skipComma() {
        return skipComma;
    }

    protected int skippedTo() {
        return skippedTo;
    }

    protected void skipTo(int index) {
        skippedTo = index;
    }

    protected void skipOne() {
        skippedTo++;
    }

    protected abstract boolean isOperator(String term);
}
