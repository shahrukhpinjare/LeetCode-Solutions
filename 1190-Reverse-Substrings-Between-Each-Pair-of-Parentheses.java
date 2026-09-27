import java.util.*;

class Solution {
    public String reverseParentheses(String s) {
        Stack<StringBuilder> stack = new Stack<>();
        StringBuilder current = new StringBuilder();

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                // Save the string built so far
                stack.push(current);
                current = new StringBuilder();

            } else if (ch == ')') {
                // Reverse the innermost substring
                current.reverse();

                // Restore the previous level
                StringBuilder previous = stack.pop();
                previous.append(current);

                current = previous;

            } else {
                current.append(ch);
            }
        }

        return current.toString();
    }
}
