import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {

        Set<String> result = new HashSet<>();

        int leftRemove = 0;
        int rightRemove = 0;

        // Find minimum removals needed
        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                leftRemove++;
            } else if (ch == ')') {
                if (leftRemove > 0) {
                    leftRemove--;
                } else {
                    rightRemove++;
                }
            }
        }

        backtrack(
            s,
            0,
            0,
            leftRemove,
            rightRemove,
            new StringBuilder(),
            result
        );

        return new ArrayList<>(result);
    }

    private void backtrack(
            String s,
            int index,
            int balance,
            int leftRemove,
            int rightRemove,
            StringBuilder current,
            Set<String> result) {

        // Invalid parentheses
        if (balance < 0) {
            return;
        }

        // End of string
        if (index == s.length()) {

            if (balance == 0 &&
                leftRemove == 0 &&
                rightRemove == 0) {

                result.add(current.toString());
            }

            return;
        }

        char ch = s.charAt(index);

        if (ch == '(') {

            // Remove '('
            if (leftRemove > 0) {
                backtrack(
                    s,
                    index + 1,
                    balance,
                    leftRemove - 1,
                    rightRemove,
                    current,
                    result
                );
            }

            // Keep '('
            current.append('(');

            backtrack(
                s,
                index + 1,
                balance + 1,
                leftRemove,
                rightRemove,
                current,
                result
            );

            current.deleteCharAt(current.length() - 1);

        } else if (ch == ')') {

            // Remove ')'
            if (rightRemove > 0) {
                backtrack(
                    s,
                    index + 1,
                    balance,
                    leftRemove,
                    rightRemove - 1,
                    current,
                    result
                );
            }

            // Keep ')' only when possible
            if (balance > 0) {

                current.append(')');

                backtrack(
                    s,
                    index + 1,
                    balance - 1,
                    leftRemove,
                    rightRemove,
                    current,
                    result
                );

                current.deleteCharAt(current.length() - 1);
            }

        } else {

            // Letter: always keep
            current.append(ch);

            backtrack(
                s,
                index + 1,
                balance,
                leftRemove,
                rightRemove,
                current,
                result
            );

            current.deleteCharAt(current.length() - 1);
        }
    }
}