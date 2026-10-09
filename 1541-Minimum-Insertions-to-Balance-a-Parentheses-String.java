class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int open = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                open++;
            } else {
                // If the next character is also ')',
                // they form a valid closing pair.
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    i++;
                } else {
                    // Insert one ')' to complete the pair.
                    insertions++;
                }

                // Match the closing pair with an opening '('.
                if (open > 0) {
                    open--;
                } else {
                    // Insert a '(' because no opening exists.
                    insertions++;
                }
            }
        }

        // Each unmatched '(' requires two closing ')'.
        insertions += open * 2;

        return insertions;
    }
}