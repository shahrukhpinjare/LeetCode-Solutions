import java.util.*;

class Solution {
    private int index;
    private String expression;

    public List<String> braceExpansionII(String expression) {
        this.expression = expression;
        this.index = 0;

        Set<String> result = parseExpression();

        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);

        return ans;
    }

    // Parses an expression involving unions.
    // Example: a,b,c
    private Set<String> parseExpression() {
        Set<String> result = parseTerm();

        while (index < expression.length() && expression.charAt(index) == ',') {
            index++; // skip ','

            Set<String> next = parseTerm();
            result.addAll(next);
        }

        return result;
    }

    // Parses concatenation.
    // Example: ab{c,d}ef
    private Set<String> parseTerm() {
        Set<String> result = new HashSet<>();
        result.add("");

        while (index < expression.length()
                && expression.charAt(index) != ','
                && expression.charAt(index) != '}') {

            Set<String> next;

            char ch = expression.charAt(index);

            if (ch == '{') {
                index++; // skip '{'

                next = parseExpression();

                index++; // skip '}'
            } else {
                // Single lowercase letter
                next = new HashSet<>();
                next.add(String.valueOf(ch));
                index++;
            }

            result = concatenate(result, next);
        }

        return result;
    }

    // Cartesian product + string concatenation
    private Set<String> concatenate(Set<String> a, Set<String> b) {
        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}
