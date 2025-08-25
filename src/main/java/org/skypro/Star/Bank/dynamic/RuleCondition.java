package org.skypro.Star.Bank.dynamic;


import java.util.List;

public class RuleCondition {
    private String query;
    private List<String> arguments;
    private boolean negate;

    public RuleCondition(String query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public String getQuery() {
        return query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }
}
