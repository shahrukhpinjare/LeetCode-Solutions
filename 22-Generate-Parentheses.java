class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        backtrack(result, "", 0, 0, n);

        return result;
    }

    private void backtrack(
        List<String> result,
        String current,
        int open,
        int close,
        int n
    ) {
        // Complete valid combination
        if (current.length() == 2 * n) {
            result.add(current);
            return;
        }

        // We can add '(' if we haven't used all of them
        if (open < n) {
            backtrack(result, current + "(", open + 1, close, n);
        }

        // We can add ')' only if there are unmatched '('
        if (close < open) {
            backtrack(result, current + ")", open, close + 1, n);
        }
    }
}
