package org.skypro.Star.Bank.dynamic;

import java.util.List;
import java.util.Objects;

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

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    /**
     * Проверяет, является ли данный запрос поддерживаемым типом
     */
    public boolean isValidQueryType() {
        return switch (query) {
            case "USER_OF", "ACTIVE_USER_OF",
                 "TRANSACTION_SUM_COMPARE",
                 "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" -> true;
            default -> false;
        };
    }

    /**
     * Проверяет корректность количества аргументов для текущего типа запроса
     */
    public boolean hasValidArgumentCount() {
        if (query == null || arguments == null) {
            return false;
        }

        return switch (query) {
            case "USER_OF", "ACTIVE_USER_OF" -> arguments.size() == 1;
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" -> arguments.size() == 2;
            case "TRANSACTION_SUM_COMPARE" -> arguments.size() == 4;
            default -> false;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleCondition that = (RuleCondition) o;
        return negate == that.negate &&
                Objects.equals(query, that.query) &&
                Objects.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, arguments, negate);
    }

    @Override
    public String toString() {
        return "RuleCondition{" +
                "query='" + query + '\'' +
                ", arguments=" + arguments +
                ", negate=" + negate +
                '}';
    }
}