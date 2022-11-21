package com.github.diegonighty.kaya.storage.mongo.query;

import com.github.diegonighty.kaya.storage.query.filter.AbstractFilterSequence;

import java.util.List;

public class MongoFilterSequence extends AbstractFilterSequence {

    static final List<String> OPERATORS = List.of(
            "and", "or", "not", "nor"
    );

    private final String nestedValuesPrefix;

    public static MongoFilterSequence create(String[] filters, String nestedValuesPrefix) {
        return new MongoFilterSequence(filters, nestedValuesPrefix);
    }

    private MongoFilterSequence(String[] filters, String nestedValuesPrefix) {
        super(filters);
        this.nestedValuesPrefix = nestedValuesPrefix;
    }

    @Override
    public void sequence(SequenceContext context, TokenFinder finder) {
        var currentToken = context.currentToken();
        var fieldToken = currentToken;

        if (!currentToken.isOperator()) {
            var value = new StringBuilder(currentToken.toString());

            if (context.hasNext()) {
                var nextToken = context.nextToken();

                if (nextToken.isOperator() && isOperatorChange(nextToken)) {
                    printer.append("Filters.")
                            .append(nextToken)
                            .append("(");

                    updateOperator(nextToken);
                } else {
                    skipTo(nextToken.index());

                    while (isSafeIndex(skippedTo())) {
                        var nestedToken = finder.find(skippedTo());

                        if (nestedToken.isOperator()) {
                            if (isOperatorChange(nestedToken)) {
                                printer.append("Filters.")
                                        .append(nestedToken)
                                        .append("(");

                                updateOperator(nestedToken);
                            }

                            break;
                        }

                        value.append(".").append(nestedValuesPrefix);

                        if (!nestedValuesPrefix.isEmpty()) {
                            value.append(nestedToken.term());
                        } else {
                            value.append(nestedToken);
                        }

                        fieldToken = nestedToken;
                        value.append("()");
                        skipOne();
                    }

                    if (isMaxIndex(skippedTo())) {
                        skipComma(true);
                    }
                }
            }

            printer.append("Filters.eq(\"").append(fieldToken).append("\", ").append(value).append(")");

            if (context.hasNext() && !skipComma()) {
                printer.append(", ");
            }
        }

        skipComma(false);
    }

    @Override
    protected boolean isOperator(String term) {
        return term != null && OPERATORS.contains(term.toLowerCase());
    }
}
