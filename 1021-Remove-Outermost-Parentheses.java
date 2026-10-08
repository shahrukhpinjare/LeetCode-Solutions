class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder result = new StringBuilder();
        int balance = 0;

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                // Add '(' only if it is not the outermost one
                if (balance > 0) {
                    result.append(ch);
                }
                balance++;
            } else {
                balance--;

                // Add ')' only if it is not the outermost one
                if (balance > 0) {
                    result.append(ch);
                }
            }
        }

        return result.toString();
    }
}