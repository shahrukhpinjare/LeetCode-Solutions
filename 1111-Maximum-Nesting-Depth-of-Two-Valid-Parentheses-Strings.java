class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int n = seq.length();
        int[] ans = new int[n];

        int depth = 0;

        for (int i = 0; i < n; i++) {
            char ch = seq.charAt(i);

            if (ch == '(') {
                // Assign based on current depth
                ans[i] = depth % 2;
                depth++;
            } else {
                // Closing bracket: reduce depth first
                depth--;
                ans[i] = depth % 2;
            }
        }

        return ans;
    }
}
